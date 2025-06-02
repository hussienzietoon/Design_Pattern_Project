package com.notetaking.patterns.state;

import com.notetaking.models.Note;

public interface NoteState {
    void draft(Note note);
    void publish(Note note);
    void archive(Note note);
    String getStateName();
} 