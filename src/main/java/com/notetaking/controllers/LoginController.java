package com.notetaking.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import com.notetaking.patterns.singleton.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            UserSession.getInstance().setUsername(username);
            try {
                Parent dashboard = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
                Scene scene = new Scene(dashboard);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                showError("Error loading dashboard");
            }
        } else {
            showError("Invalid username or password");
        }
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            Parent registration = FXMLLoader.load(getClass().getResource("/fxml/Registration.fxml"));
            Scene scene = new Scene(registration);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showError("Error loading registration page");
        }
    }

    private boolean validateLogin(String username, String password) {
        // Clear any previous error messages
        errorLabel.setVisible(false);

        // Username validation
        if (username == null || username.trim().isEmpty()) {
            showError("Username is required");
            return false;
        }

        if (username.length() < 3) {
            showError("Username must be at least 3 characters long");
            return false;
        }

        if (username.length() > 50) {
            showError("Username must not exceed 50 characters");
            return false;
        }

        // Password validation
        if (password == null || password.trim().isEmpty()) {
            showError("Password is required");
            return false;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters long");
            return false;
        }

        if (password.length() > 100) {
            showError("Password must not exceed 100 characters");
            return false;
        }

        // If all validations pass
        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}