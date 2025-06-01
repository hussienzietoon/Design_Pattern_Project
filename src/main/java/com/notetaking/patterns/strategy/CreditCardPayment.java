package com.notetaking.patterns.strategy;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean processPayment(double amount) {
        // In a real application, this would integrate with a payment gateway
        System.out.println("Processing credit card payment for $" + amount);
        System.out.println("Card Number: " + maskCardNumber(cardNumber));
        return true;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return cardNumber;
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
} 