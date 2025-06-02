package com.notetaking.controllers;

import com.jfoenix.controls.*;
import com.notetaking.patterns.singleton.UserSession;
import com.notetaking.patterns.strategy.ListViewStrategy;
import com.notetaking.patterns.strategy.NoteViewStrategy;
import com.notetaking.models.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DashboardController {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private Text welcomeText;

    @FXML
    private VBox notesContainer;

    @FXML
    private JFXDialog newNoteDialog;

    @FXML
    private JFXTextField noteTitleField;

    @FXML
    private JFXTextArea noteContentArea;

    @FXML
    private JFXButton premiumButton;

    @FXML
    private JFXButton unsubscribeButton;

    private NoteViewStrategy viewStrategy;
    private ObservableList<Note> notes;

    @FXML
    public void initialize() {
        String username = UserSession.getInstance().getUsername();
        boolean isPremium = UserSession.getInstance().isPremium();

        // Update welcome text based on premium status
        welcomeText.setText("Welcome, " + username + (isPremium ? " (PRO)" : ""));

        // Show/hide premium and unsubscribe buttons based on premium status
        premiumButton.setVisible(!isPremium);
        unsubscribeButton.setVisible(isPremium);

        notes = FXCollections.observableArrayList();
        viewStrategy = new ListViewStrategy(); // Use list view by default

        // Set the dialog container
        newNoteDialog.setDialogContainer(rootStackPane);

        refreshNotesView();
    }

    @FXML
    private void handleNewNote() {
        noteTitleField.clear();
        noteContentArea.clear();
        newNoteDialog.show();
    }

    @FXML
    private void handleSaveNote() {
        String title = noteTitleField.getText();
        String content = noteContentArea.getText();

        if (!title.trim().isEmpty() && !content.trim().isEmpty()) {
            Note newNote = new Note.NoteBuilder()
                    .setTitle(title)
                    .setContent(content)
                    .setCreatedAt(System.currentTimeMillis())
                    .build();

            notes.add(newNote);
            refreshNotesView();
            newNoteDialog.close();
        }
    }

    @FXML
    private void handleCancelNote() {
        newNoteDialog.close();
    }

    private void handleDeleteNote(Note note) {
        notes.remove(note);
        refreshNotesView();
    }

    @FXML
    private void handlePremium(ActionEvent event) {
        try {
            Parent premium = FXMLLoader.load(getClass().getResource("/fxml/Premium.fxml"));
            Scene scene = new Scene(premium);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUnsubscribe(ActionEvent event) {
        Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
        confirmDialog.setTitle("Unsubscribe Confirmation");
        confirmDialog.setHeaderText("Unsubscribe from Premium");
        confirmDialog.setContentText("Are you sure you want to unsubscribe from premium features?");

        if (confirmDialog.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            UserSession.getInstance().setPremium(false);
            // Refresh the dashboard to update the UI
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
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        UserSession.getInstance().clearSession();
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(login);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshNotesView() {
        notesContainer.getChildren().clear();
        for (Note note : notes) {
            Node noteNode = viewStrategy.createNoteNode(note, e -> handleDeleteNote(note));
            notesContainer.getChildren().add(noteNode);
        }
    }
}