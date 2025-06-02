package com.notetaking.controllers;

import com.jfoenix.controls.*;
import com.notetaking.patterns.singleton.UserSession;
import com.notetaking.patterns.state.*;
import com.notetaking.models.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class DashboardController {
    @FXML private Text welcomeText;
    @FXML private VBox notesContainer;
    @FXML private JFXDialog newNoteDialog;
    @FXML private JFXTextField noteTitleField;
    @FXML private JFXTextArea noteContentArea;
    @FXML private RadioButton draftRadio;
    @FXML private RadioButton publishedRadio;
    @FXML private RadioButton archivedRadio;
    @FXML private JFXButton premiumButton;
    @FXML private JFXButton unsubscribeButton;
    @FXML private StackPane rootStackPane;

    private List<Note> notes = new ArrayList<>();
    private ToggleGroup noteStateGroup;

    @FXML
    public void initialize() {
        welcomeText.setText("Welcome, " + UserSession.getInstance().getUsername() + "!");
        updatePremiumButtonVisibility();

        // Initialize the new note dialog
        newNoteDialog.setDialogContainer(rootStackPane);
        newNoteDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

        // Initialize note state group
        noteStateGroup = new ToggleGroup();
        draftRadio.setToggleGroup(noteStateGroup);
        publishedRadio.setToggleGroup(noteStateGroup);
        archivedRadio.setToggleGroup(noteStateGroup);
        draftRadio.setSelected(true);

        // Create dialog layout
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("New Note"));

        VBox body = new VBox(15);
        body.getChildren().addAll(
                noteTitleField,
                noteContentArea,
                createStateSelectionBox()
        );
        layout.setBody(body);

        HBox actions = new HBox(10);
        actions.getChildren().addAll(
                new JFXButton("Cancel") {{
                    setOnAction(e -> newNoteDialog.close());
                }},
                new JFXButton("Save") {{
                    setOnAction(e -> handleSaveNote());
                }}
        );
        layout.setActions(actions);

        newNoteDialog.setContent(layout);
    }

    private HBox createStateSelectionBox() {
        HBox stateBox = new HBox(10);
        stateBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        stateBox.getChildren().addAll(
                new Label("Status:"),
                draftRadio,
                publishedRadio,
                archivedRadio
        );
        return stateBox;
    }

    private void updatePremiumButtonVisibility() {
        boolean isPremium = UserSession.getInstance().isPremium();
        premiumButton.setVisible(!isPremium);
        unsubscribeButton.setVisible(isPremium);
    }

    @FXML
    private void handleNewNote(ActionEvent event) {
        noteTitleField.clear();
        noteContentArea.clear();
        draftRadio.setSelected(true);
        newNoteDialog.show();
    }

    @FXML
    private void handleSaveNote() {
        String title = noteTitleField.getText();
        String content = noteContentArea.getText();

        if (title.isEmpty() || content.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }

        NoteState initialState;
        if (draftRadio.isSelected()) {
            initialState = new DraftState();
        } else if (publishedRadio.isSelected()) {
            initialState = new PublishedState();
        } else {
            initialState = new ArchivedState();
        }

        Note note = new Note.NoteBuilder()
                .setTitle(title)
                .setContent(content)
                .setCreatedAt(System.currentTimeMillis())
                .build();
        note.setState(initialState);

        notes.add(note);
        displayNote(note);
        newNoteDialog.close();
    }

    private void displayNote(Note note) {
        HBox noteBox = new HBox(10);
        noteBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5;");
        noteBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(noteBox, Priority.ALWAYS);

        VBox contentBox = new VBox(5);
        contentBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(contentBox, Priority.ALWAYS);

        Text titleText = new Text(note.getTitle());
        titleText.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleText.setFill(Color.BLACK);

        Text contentText = new Text(note.getContent());
        contentText.setFill(Color.GRAY);
        contentText.setWrappingWidth(400);

        Label stateLabel = new Label(note.getState().getStateName());
        stateLabel.setStyle("-fx-background-color: " + getStateColor(note.getState()) + "; -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 3;");

        contentBox.getChildren().addAll(titleText, contentText, stateLabel);
        noteBox.getChildren().add(contentBox);

        // Add state transition buttons and delete button
        HBox stateButtons = new HBox(5);
        if (note.getState() instanceof DraftState) {
            stateButtons.getChildren().add(createStateButton("Publish", note, new PublishedState()));
        } else if (note.getState() instanceof PublishedState) {
            stateButtons.getChildren().add(createStateButton("Archive", note, new ArchivedState()));
        } else if (note.getState() instanceof ArchivedState) {
            stateButtons.getChildren().add(createStateButton("Restore", note, new PublishedState()));
        }

        // Add delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            notes.remove(note);
            notesContainer.getChildren().clear();
            for (Note n : notes) {
                displayNote(n);
            }
        });
        stateButtons.getChildren().add(deleteButton);

        noteBox.getChildren().add(stateButtons);
        notesContainer.getChildren().add(noteBox);
    }

    private String getStateColor(NoteState state) {
        if (state instanceof DraftState) return "#808080"; // Gray
        if (state instanceof PublishedState) return "#4CAF50"; // Green
        if (state instanceof ArchivedState) return "#FFA500"; // Orange
        return "#808080";
    }

    private Button createStateButton(String text, Note note, NoteState newState) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        button.setOnAction(e -> {
            note.setState(newState);
            notesContainer.getChildren().clear();
            for (Note n : notes) {
                displayNote(n);
            }
        });
        return button;
    }

    @FXML
    private void handleCancelNote(ActionEvent event) {
        newNoteDialog.close();
    }

    @FXML
    private void handlePremium(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Premium.fxml"));
            Parent premium = loader.load();
            PremiumController controller = loader.getController();

            Scene scene = new Scene(premium);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading premium page: " + e.getMessage());
        }
    }

    @FXML
    private void handleUnsubscribe(ActionEvent event) {
        UserSession.getInstance().setPremium(false);
        updatePremiumButtonVisibility();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        UserSession.getInstance().logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent login = loader.load();
            LoginController controller = loader.getController();

            Scene scene = new Scene(login);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading login page: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}