package com.notetaking.patterns.builder;

import java.time.LocalDateTime;

public class Note {
    private final String title;
    private final String content;
    private final String category;
    private final LocalDateTime createdAt;
    private final boolean isPinned;
    private final String tags;

    private Note(NoteBuilder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.category = builder.category;
        this.createdAt = builder.createdAt;
        this.isPinned = builder.isPinned;
        this.tags = builder.tags;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isPinned() { return isPinned; }
    public String getTags() { return tags; }

    public static class NoteBuilder {
        private String title;
        private String content;
        private String category;
        private LocalDateTime createdAt;
        private boolean isPinned;
        private String tags;

        public NoteBuilder() {
            this.createdAt = LocalDateTime.now();
        }

        public NoteBuilder title(String title) {
            this.title = title;
            return this;
        }

        public NoteBuilder content(String content) {
            this.content = content;
            return this;
        }

        public NoteBuilder category(String category) {
            this.category = category;
            return this;
        }

        public NoteBuilder isPinned(boolean isPinned) {
            this.isPinned = isPinned;
            return this;
        }

        public NoteBuilder tags(String tags) {
            this.tags = tags;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }
} 