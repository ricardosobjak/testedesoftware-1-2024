package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import br.edu.utfpr.bankapi.validations.AvailableBalanceValidation;

@SpringBootTest
public class WithdrawTest {

    @Autowired
    private TransactionService service;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AvailableAccountValidation availableAccountValidation;

    @MockBean
    private AvailableBalanceValidation availableBalanceValidation;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    /**
     * Teste para verificar a operação de saque
     * 
     * @throws NotFoundException
     */
    @Test
    void deveriaSacar() throws NotFoundException {
        // ### ARRANGE ###
        double initialBalance = 1200.50;

        long sourceAccountNumber = 67890;

        long amount = 200;

        WithdrawDTO withdrawDTO = new WithdrawDTO(sourceAccountNumber, 200);

        Account sourceAccount = new Account("Jane Doe", 67890, initialBalance, 0);

        Transaction transaction = new Transaction(sourceAccount, sourceAccount, amount,
                TransactionType.WITHDRAW);

        // Comportamento da validação da conta disponível
        BDDMockito.given(availableAccountValidation.validate(withdrawDTO.sourceAccountNumber()))
                .willReturn(sourceAccount);

        // Comportamento da validação do saldo disponível (Não deve dar uma excpetion)
        BDDMockito.willDoNothing().given(availableBalanceValidation).validate(transaction);

        // ### ACT ###
        service.withdraw(withdrawDTO);

        // ### ASSERT ###
        // Verificar se a transação foi salva
        BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();

        // A conta de origem deveria ser a mesma na transação
        Assertions.assertEquals(sourceAccount, savedTransaction.getSourceAccount());
        // O valor do saque deveria ser o mesmo na transação
        Assertions.assertEquals(withdrawDTO.amount(), savedTransaction.getAmount());
        // O tipo de operação deveria ser WITHDRAW na transação
        Assertions.assertEquals(TransactionType.WITHDRAW,
                savedTransaction.getType());
        // O saldo na conta de origem deveria ser reduzido pelo valor da transação
        Assertions.assertEquals(initialBalance - withdrawDTO.amount(),
                savedTransaction.getSourceAccount().getBalance());
    }
}