package com.notetaking.patterns.state;

import com.notetaking.models.Note;

public class ArchivedState implements NoteState {
    @Override
    public void draft(Note note) {
        note.setState(new DraftState());
    }

    @Override
    public void publish(Note note) {
        note.setState(new PublishedState());
    }

    @Override
    public void archive(Note note) {
        // Already in archived state
    }

    @Override
    public String getStateName() {
        return "Archived";
    }
} 