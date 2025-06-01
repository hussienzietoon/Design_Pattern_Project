package com.notetaking.patterns.state;

import com.notetaking.models.Note;

public class DraftState implements NoteState {
    @Override
    public void draft(Note note) {
        // Already in draft state
    }

    @Override
    public void publish(Note note) {
        note.setState(new PublishedState());
    }

    @Override
    public void archive(Note note) {
        note.setState(new ArchivedState());
    }

    @Override
    public String getStatusName() {
        return "Draft";
    }
} 