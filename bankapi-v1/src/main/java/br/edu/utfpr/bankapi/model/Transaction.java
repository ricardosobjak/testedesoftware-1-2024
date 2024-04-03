package br.edu.utfpr.bankapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;

    @ManyToOne
    private Account sourceAccount;

    @ManyToOne
    private Account receiverAccount;

    private LocalDateTime localDateTime;

    public Transaction() {
        localDateTime = LocalDateTime.now();
    }
}