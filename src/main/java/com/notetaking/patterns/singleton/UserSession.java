package com.notetaking.patterns.singleton;

public class UserSession {
    private static UserSession instance;
    private String username;
    private boolean isPremium;
    private String subscriptionType;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void clearSession() {
        username = null;
        isPremium = false;
        subscriptionType = null;
    }
} 