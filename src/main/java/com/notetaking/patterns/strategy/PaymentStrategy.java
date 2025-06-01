package com.notetaking.patterns.strategy;

public interface PaymentStrategy {
    boolean processPayment(double amount);
} 