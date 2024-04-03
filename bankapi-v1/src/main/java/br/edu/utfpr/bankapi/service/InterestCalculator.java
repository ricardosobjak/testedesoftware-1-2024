package br.edu.utfpr.bankapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InterestCalculator {
    public double calculateInterest(double value, float tax,
                                    int term) {
        float decimalTax = tax / 100;
        double juros = value
                * Math.pow(1 + decimalTax, term) - value;
        return BigDecimal.valueOf(juros).setScale(2,
                RoundingMode.HALF_EVEN).doubleValue();
    }
}
