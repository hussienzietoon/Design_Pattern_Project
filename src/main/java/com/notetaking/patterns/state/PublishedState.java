package com.notetaking.patterns.state;

import com.notetaking.models.Note;

public class PublishedState implements NoteState {
    @Override
    public void draft(Note note) {
        note.setState(new DraftState());
    }

    @Override
    public void publish(Note note) {
        // Already in published state
    }

    @Override
    public void archive(Note note) {
        note.setState(new ArchivedState());
    }

    @Override
    public String getStatusName() {
        return "Published";
    }
} 