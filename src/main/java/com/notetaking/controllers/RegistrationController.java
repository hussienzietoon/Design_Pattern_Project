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

public class RegistrationController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegistration(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (validateRegistration(username, password, confirmPassword)) {
            // In a real application, this would save to a database
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
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(login);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showError("Error loading login page");
        }
    }

    private boolean validateRegistration(String username, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            showError("Username is required");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            showError("Password is required");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
} 