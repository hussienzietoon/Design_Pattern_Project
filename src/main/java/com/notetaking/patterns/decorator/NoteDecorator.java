package com.notetaking.patterns.decorator;

import com.notetaking.models.Note;

public abstract class NoteDecorator {
    protected Note note;

    public NoteDecorator(Note note) {
        this.note = note;
    }

    public abstract String getFormattedContent();
    public abstract String getFormattedTitle();
} 