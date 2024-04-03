package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterestCalculatorTest {

    @Test
    public void shouldReturnCorrectInterestMonth() {
        // Arrange
        double value = 1000;
        float mensalTax = 1.5f;
        int months = 6;

        InterestCalculator interestCalculator = new InterestCalculator();

        // Act
        double interestValue = interestCalculator.calculateInterest(value, mensalTax, months);

        // Assert
        assertEquals(93.44, interestValue);
    }

    @Test
    public void shouldReturnCorrectInterestDay() {
        // Arrange
        double value = 1000;
        float taxDay = 0.05f;
        int days = 30;

        InterestCalculator interestCalculator = new InterestCalculator();

        // Act
        double interestValueDay = interestCalculator.calculateInterest(value, taxDay, days);

        // Assert
        assertEquals(15.11, interestValueDay);
    }
}
