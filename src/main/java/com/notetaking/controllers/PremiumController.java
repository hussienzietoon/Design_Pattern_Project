package com.notetaking.controllers;

import com.notetaking.patterns.singleton.UserSession;
import com.notetaking.patterns.strategy.CreditCardPayment;
import com.notetaking.patterns.strategy.PayPalPayment;
import com.notetaking.patterns.strategy.PaymentStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.Optional;
import java.util.regex.Pattern;

public class PremiumController {

    private static final double PREMIUM_PRICE = 9.99;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");
    private static final Pattern EXPIRY_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/([0-9]{2})$");

    @FXML
    private void handleCreditCardPayment(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Credit Card Payment");
        dialog.setHeaderText("Enter Credit Card Details");
        dialog.setWidth(500);
        dialog.setHeight(400);

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number (16 digits)");
        TextField cardHolderField = new TextField();
        cardHolderField.setPromptText("Card Holder Name");
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV (3-4 digits)");
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        grid.add(new Label("Card Number:"), 0, 0);
        grid.add(cardNumberField, 1, 0);
        grid.add(new Label("Card Holder:"), 0, 1);
        grid.add(cardHolderField, 1, 1);
        grid.add(new Label("Expiry Date:"), 0, 2);
        grid.add(expiryField, 1, 2);
        grid.add(new Label("CVV:"), 0, 3);
        grid.add(cvvField, 1, 3);
        grid.add(errorLabel, 1, 4);

        dialog.getDialogPane().setContent(grid);

        ButtonType payButtonType = new ButtonType("Pay", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(payButtonType, cancelButtonType);

        // Enable/disable Pay button based on validation
        dialog.getDialogPane().lookupButton(payButtonType).setDisable(true);

        // Add validation listeners
        cardNumberField.textProperty().addListener((obs, oldVal, newVal) -> validateCreditCardFields(
                cardNumberField, cardHolderField, expiryField, cvvField, errorLabel, dialog, payButtonType));
        cardHolderField.textProperty().addListener((obs, oldVal, newVal) -> validateCreditCardFields(
                cardNumberField, cardHolderField, expiryField, cvvField, errorLabel, dialog, payButtonType));
        expiryField.textProperty().addListener((obs, oldVal, newVal) -> validateCreditCardFields(
                cardNumberField, cardHolderField, expiryField, cvvField, errorLabel, dialog, payButtonType));
        cvvField.textProperty().addListener((obs, oldVal, newVal) -> validateCreditCardFields(
                cardNumberField, cardHolderField, expiryField, cvvField, errorLabel, dialog, payButtonType));

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == payButtonType) {
            PaymentStrategy paymentStrategy = new CreditCardPayment(
                    cardNumberField.getText(),
                    cvvField.getText(),
                    expiryField.getText()
            );
            processPayment(paymentStrategy, event);
        }
    }

    private void validateCreditCardFields(TextField cardNumber, TextField cardHolder,
                                          TextField expiry, TextField cvv,
                                          Label errorLabel, Dialog<ButtonType> dialog,
                                          ButtonType payButton) {
        String errorMessage = "";

        if (!CARD_NUMBER_PATTERN.matcher(cardNumber.getText()).matches()) {
            errorMessage = "Invalid card number (must be 16 digits)";
        } else if (cardHolder.getText().trim().isEmpty()) {
            errorMessage = "Card holder name is required";
        } else if (!EXPIRY_PATTERN.matcher(expiry.getText()).matches()) {
            errorMessage = "Invalid expiry date (use MM/YY format)";
        } else if (!CVV_PATTERN.matcher(cvv.getText()).matches()) {
            errorMessage = "Invalid CVV (must be 3-4 digits)";
        }

        errorLabel.setText(errorMessage);
        dialog.getDialogPane().lookupButton(payButton).setDisable(!errorMessage.isEmpty());
    }

    @FXML
    private void handlePayPalPayment(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("PayPal Payment");
        dialog.setHeaderText("Enter PayPal Details");
        dialog.setWidth(500);
        dialog.setHeight(300);

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField emailField = new TextField();
        emailField.setPromptText("PayPal Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("PayPal Password");
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(errorLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType payButtonType = new ButtonType("Pay", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(payButtonType, cancelButtonType);

        // Enable/disable Pay button based on validation
        dialog.getDialogPane().lookupButton(payButtonType).setDisable(true);

        // Add validation listeners
        emailField.textProperty().addListener((obs, oldVal, newVal) -> validatePayPalFields(
                emailField, passwordField, errorLabel, dialog, payButtonType));
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validatePayPalFields(
                emailField, passwordField, errorLabel, dialog, payButtonType));

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == payButtonType) {
            PaymentStrategy paymentStrategy = new PayPalPayment(
                    emailField.getText(),
                    passwordField.getText()
            );
            processPayment(paymentStrategy, event);
        }
    }

    private void validatePayPalFields(TextField email, PasswordField password,
                                      Label errorLabel, Dialog<ButtonType> dialog,
                                      ButtonType payButton) {
        String errorMessage = "";

        if (!EMAIL_PATTERN.matcher(email.getText()).matches()) {
            errorMessage = "Invalid email format";
        } else if (password.getText().length() < 6) {
            errorMessage = "Password must be at least 6 characters";
        }

        errorLabel.setText(errorMessage);
        dialog.getDialogPane().lookupButton(payButton).setDisable(!errorMessage.isEmpty());
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