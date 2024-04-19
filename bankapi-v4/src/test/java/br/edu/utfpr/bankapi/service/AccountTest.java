package br.edu.utfpr.bankapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.edu.utfpr.bankapi.dto.AccountDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    void deveriaRetornarContaPeloNumero() {
        // ARRANGE
        long accountNumber = 123456789L;
        Account account = new Account("Joao da Silva Sauro", accountNumber, 2000.0, 500.0);
        given(accountRepository.getByNumber(accountNumber)).willReturn(Optional.of(account));

        // ACT
        Optional<Account> result = accountService.getByNumber(accountNumber);

        // ASSERT
        assertTrue(result.isPresent()); // Verifica se o resultado é presente
        assertEquals(account.getNumber(), result.get().getNumber()); // Verifica se o número da conta é igual ao
                                                                     // esperado

        BDDMockito.then(accountRepository).should().getByNumber(accountNumber); // Confirma que getByNumber foi chamado
                                                                                // com o número correto
    }

    @Test
    void DeveriaRetornarTodasContas() {
        // ARRANGE
        Account account = new Account("Maria das Dores Amada", 123456789L, 2000.0, 500.0);
        BDDMockito.given(accountRepository.findAll()).willReturn(Arrays.asList(account));

        // ACT
        List<Account> results = accountService.getAll();

        // ASSERT
        assertFalse(results.isEmpty()); // Verifica se a lista de resultados não está vazia
        assertEquals(1, results.size()); // Verifica se o tamanho da lista é 1
        BDDMockito.then(accountRepository).should().findAll(); // Confirma que findAll foi chamado
    }

    @Test
    void shouldSaveAccountWithInitialBalance() {
        // ARRANGE
        AccountDTO accountDTO = new AccountDTO("Pedro Titanossauro Rex", 123456789L, 0, 500.0);
        BDDMockito.given(accountRepository.save(any(Account.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Account savedAccount = accountService.save(accountDTO);

        // ASSERT
        assertEquals(0, savedAccount.getBalance()); // Verifica se o saldo inicial é 0
        BDDMockito.then(accountRepository).should().save(accountCaptor.capture()); // Confirma que save foi chamado
        Account captured = accountCaptor.getValue();
        assertEquals(accountDTO.name(), captured.getName()); // Verifica se o nome está correto
        assertEquals(500.0, captured.getSpecialLimit()); // Verifica se o limite especial está correto
    }

    @Test
    void deveriaAtualizarConta() throws NotFoundException {
        // ARRANGE
        long accountId = 1L;
        Account existingAccount = new Account("Janaina Amada", 123456789L, 1000.0, 300.0);
        AccountDTO updateDTO = new AccountDTO("Conta atualizada", 123456789L, 1500.0, 700.0);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(existingAccount));
        // Configura o mock para atualizar os dados do account com os valores fornecidos
        // por updateDTO
        given(accountRepository.save(any(Account.class))).willAnswer(invocation -> {
            Account a = invocation.getArgument(0);
            a.setName(updateDTO.name());
            a.setNumber(updateDTO.number());
            a.setBalance(updateDTO.balance());
            a.setSpecialLimit(updateDTO.specialLimit());
            return a;
        });

        // ACT
        Account updatedAccount = accountService.update(accountId, updateDTO);

        // ASSERT
        then(accountRepository).should().save(accountCaptor.capture()); // Confirma que save foi chamado
        Account captured = accountCaptor.getValue();
        assertEquals("Conta atualizada", captured.getName()); // Verifica se o nome foi atualizado corretamente
        assertEquals(1500.0, captured.getBalance()); // Verifica se o saldo foi atualizado corretamente
        assertEquals(700.0, captured.getSpecialLimit()); // Verifica se o limite especial foi atualizado corretamente
    }

    @Test
    void deveriaLancarExcecaoQuandoContaNaoEncontradaForAtualizada() {
        // ARRANGE
        long accountId = 1L;
        given(accountRepository.findById(accountId)).willReturn(Optional.empty());

        // ACT / ASSERT
        assertThrows(NotFoundException.class,
                () -> accountService.update(accountId, new AccountDTO("João Humberto", 123456789L, 1000.0, 500.0))); // Espera que uma NotFoundException seja lançada
        then(accountRepository).should(never()).save(any(Account.class)); // Confirma que save nunca foi chamado
    }
}