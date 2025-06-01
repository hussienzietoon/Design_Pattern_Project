package com.notetaking.patterns.strategy;

import com.jfoenix.controls.JFXButton;
import com.notetaking.models.Note;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GridViewStrategy implements NoteViewStrategy {
    
    @Override
    public Node createNoteNode(Note note, EventHandler<ActionEvent> onDelete) {
        VBox noteCard = new VBox(10);
        noteCard.getStyleClass().add("note-card");
        noteCard.setPrefWidth(200);
        
        Label title = new Label(note.getTitle());
        title.getStyleClass().add("note-title");
        
        Text content = new Text(note.getContent());
        content.getStyleClass().add("note-content");
        content.setWrappingWidth(180);
        
        Label date = new Label(formatDate(note.getCreatedAt()));
        date.getStyleClass().add("note-date");
        
        JFXButton deleteButton = new JFXButton("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(onDelete);
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        
        noteCard.getChildren().addAll(title, content, date, deleteButton);
        
        return noteCard;
    }
    
    private String formatDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        );
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
} 