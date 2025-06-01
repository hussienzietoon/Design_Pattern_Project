package com.notetaking.patterns.strategy;

import com.notetaking.models.Note;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface NoteViewStrategy {
    Node createNoteNode(Note note, EventHandler<ActionEvent> onDelete);
} 