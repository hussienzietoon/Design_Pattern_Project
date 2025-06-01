package com.notetaking.patterns.strategy;

import com.jfoenix.controls.JFXButton;
import com.notetaking.models.Note;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ListViewStrategy implements NoteViewStrategy {
    
    @Override
    public Node createNoteNode(Note note, EventHandler<ActionEvent> onDelete) {
        HBox noteRow = new HBox(15);
        noteRow.getStyleClass().add("note-row");
        noteRow.setPrefWidth(Double.MAX_VALUE);
        
        VBox contentBox = new VBox(5);
        HBox.setHgrow(contentBox, Priority.ALWAYS);
        
        Label title = new Label(note.getTitle());
        title.getStyleClass().add("note-title");
        
        Text content = new Text(note.getContent());
        content.getStyleClass().add("note-content");
        content.setWrappingWidth(500);
        
        contentBox.getChildren().addAll(title, content);
        
        VBox rightBox = new VBox(5);
        rightBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        
        Label date = new Label(formatDate(note.getCreatedAt()));
        date.getStyleClass().add("note-date");
        
        JFXButton deleteButton = new JFXButton("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(onDelete);
        
        rightBox.getChildren().addAll(date, deleteButton);
        
        noteRow.getChildren().addAll(contentBox, rightBox);
        
        return noteRow;
    }
    
    private String formatDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        );
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
} 