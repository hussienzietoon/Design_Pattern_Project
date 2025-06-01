package com.notetaking.models;

import com.notetaking.patterns.state.NoteState;
import com.notetaking.patterns.state.DraftState;

public class Note {
    private String title;
    private String content;
    private long createdAt;
    private boolean isPinned;
    private String category;
    private NoteState state;

    private Note(NoteBuilder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.createdAt = builder.createdAt;
        this.isPinned = builder.isPinned;
        this.category = builder.category;
        this.state = new DraftState(); // Default state
    }

    // State pattern methods
    public void setState(NoteState state) {
        this.state = state;
    }

    public void draft() {
        state.draft(this);
    }

    public void publish() {
        state.publish(this);
    }

    public void archive() {
        state.archive(this);
    }

    public String getStatus() {
        return state.getStatusName();
    }

    // Getters
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public long getCreatedAt() { return createdAt; }
    public boolean isPinned() { return isPinned; }
    public String getCategory() { return category; }

    // Builder class
    public static class NoteBuilder {
        private String title;
        private String content;
        private long createdAt;
        private boolean isPinned;
        private String category;

        public NoteBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public NoteBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public NoteBuilder setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NoteBuilder setPinned(boolean isPinned) {
            this.isPinned = isPinned;
            return this;
        }

        public NoteBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }
} 