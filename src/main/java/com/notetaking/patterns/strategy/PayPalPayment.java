package com.notetaking.patterns.strategy;

public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean processPayment(double amount) {
        // In a real application, this would integrate with PayPal's API
        System.out.println("Processing PayPal payment for $" + amount);
        System.out.println("PayPal Account: " + email);
        return true;
    }
} 