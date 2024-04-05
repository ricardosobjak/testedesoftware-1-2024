package br.edu.utfpr.bankapi.service;

import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Operação de depósito em uma conta
     */
    @Transactional
    public Transaction deposit(DepositDTO dto) throws NotFoundException {
        if (dto.amount() <= 0)
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");

        var account = accountRepository.getByNumber(dto.receiverAccountNumber());
        if (account.isEmpty())
            throw new NotFoundException();

        var transaction = new Transaction(null, account.get(), dto.amount(), TransactionType.DEPOSIT);
        transaction.getReceiverAccount().setBalance(transaction.getReceiverAccount().getBalance() + dto.amount());
        return transactionRepository.save(transaction);
    }

    /**
     * Operação de SAQUE de uma conta
     */
    @Transactional
    public Transaction withdraw(WithdrawDTO dto) {
        return null;
    }

    /**
     * Operação de transferência entre contas.
     */
    @Transactional
    public Transaction transfer(TransferDTO dto) {

        return null;
    }
}
