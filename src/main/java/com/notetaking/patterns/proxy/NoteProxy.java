package com.notetaking.patterns.proxy;

import com.notetaking.models.Note;

public class NoteProxy {
    private Note realNote;
    private final long noteId;
    private final String title;
    private final long createdAt;

    public NoteProxy(long noteId, String title, long createdAt) {
        this.noteId = noteId;
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Note getFullNote() {
        if (realNote == null) {
            // In a real application, this would load from database
            realNote = loadNoteFromStorage();
        }
        return realNote;
    }

    private Note loadNoteFromStorage() {
        // Simulate database loading
        return new Note.NoteBuilder()
                .setTitle(title)
                .setContent("Loading complete...")
                .setCreatedAt(createdAt)
                .build();
    }
} 