package com.notetaking.patterns.command;

import com.notetaking.models.Note;
import com.notetaking.patterns.facade.NoteFacade;

public class CreateNoteCommand implements NoteCommand {
    private final NoteFacade noteFacade;
    private final String title;
    private final String content;
    private final String category;
    private Note createdNote;

    public CreateNoteCommand(NoteFacade noteFacade, String title, String content, String category) {
        this.noteFacade = noteFacade;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    @Override
    public void execute() {
        createdNote = noteFacade.createNote(title, content, category);
    }

    @Override
    public void undo() {
        if (createdNote != null) {
            noteFacade.deleteNote(createdNote);
            createdNote = null;
        }
    }
} 