package br.edu.utfpr.bankapi.service;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DepositTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    public void shouldReceiverAccountExists() throws NotFoundException {
        // Arrange
        long receiverAccountNumber = 12345;
        double amount = 100.0;
        double initialBalance = 150.0;
        var depositDTO = new DepositDTO(receiverAccountNumber, amount);
        var receiveAccount = new Account("Teste da Silva", receiverAccountNumber, initialBalance, 0);

        BDDMockito.given(accountRepository.getByNumber(depositDTO.receiverAccountNumber()))
                .willReturn(Optional.of(receiveAccount));

        // Act
        transactionService.deposit(depositDTO);

        // Assert
        BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction transaction = transactionCaptor.getValue();

        Assertions.assertEquals(receiveAccount, transaction.getReceiverAccount());
        Assertions.assertEquals(amount, transaction.getAmount());
        Assertions.assertEquals(TransactionType.DEPOSIT, transaction.getType());
        Assertions.assertEquals(initialBalance + amount, transaction.getReceiverAccount().getBalance());
    }

}
