package br.edu.utfpr.bankapi.contracts;

public record CreateAccountRequest(
        String name,
        long number,
        double balance,
        double specialLimit
) {}
