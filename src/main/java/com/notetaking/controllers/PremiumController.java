package com.notetaking.controllers;

import com.notetaking.patterns.singleton.UserSession;
import com.notetaking.patterns.strategy.CreditCardPayment;
import com.notetaking.patterns.strategy.PayPalPayment;
import com.notetaking.patterns.strategy.PaymentStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

public class PremiumController {

    private static final double PREMIUM_PRICE = 9.99;

    @FXML
    private void handleCreditCardPayment(ActionEvent event) {
        TextInputDialog cardDialog = new TextInputDialog();
        cardDialog.setTitle("Credit Card Payment");
        cardDialog.setHeaderText("Enter Credit Card Details");
        cardDialog.setContentText("Card Number:");

        Optional<String> cardResult = cardDialog.showAndWait();
        if (cardResult.isPresent()) {
            TextInputDialog cvvDialog = new TextInputDialog();
            cvvDialog.setTitle("Credit Card Payment");
            cvvDialog.setHeaderText("Enter CVV");
            cvvDialog.setContentText("CVV:");

            Optional<String> cvvResult = cvvDialog.showAndWait();
            if (cvvResult.isPresent()) {
                PaymentStrategy paymentStrategy = new CreditCardPayment(
                    cardResult.get(), cvvResult.get(), "12/25");
                processPayment(paymentStrategy, event);
            }
        }
    }

    @FXML
    private void handlePayPalPayment(ActionEvent event) {
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("PayPal Payment");
        emailDialog.setHeaderText("Enter PayPal Details");
        emailDialog.setContentText("Email:");

        Optional<String> emailResult = emailDialog.showAndWait();
        if (emailResult.isPresent()) {
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("PayPal Payment");
            passwordDialog.setHeaderText("Enter Password");
            passwordDialog.setContentText("Password:");

            Optional<String> passwordResult = passwordDialog.showAndWait();
            if (passwordResult.isPresent()) {
                PaymentStrategy paymentStrategy = new PayPalPayment(
                    emailResult.get(), passwordResult.get());
                processPayment(paymentStrategy, event);
            }
        }
    }

    private void processPayment(PaymentStrategy paymentStrategy, ActionEvent event) {
        if (paymentStrategy.processPayment(PREMIUM_PRICE)) {
            UserSession.getInstance().setPremium(true);
            showSuccess();
            navigateToDashboard(event);
        } else {
            showError();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateToDashboard(event);
    }

    private void navigateToDashboard(ActionEvent event) {
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Scene scene = new Scene(dashboard);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Premium Subscription Activated");
        alert.setContentText("Thank you for upgrading to premium!");
        alert.showAndWait();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Payment Failed");
        alert.setContentText("There was an error processing your payment. Please try again.");
        alert.showAndWait();
    }
} 