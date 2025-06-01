package com.notetaking.patterns.prototype;

import com.notetaking.models.Note;

public class NoteTemplate implements Cloneable {
    private String title;
    private String content;
    private String category;
    private String template;

    public NoteTemplate(String template, String title, String content, String category) {
        this.template = template;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    @Override
    public NoteTemplate clone() {
        try {
            return (NoteTemplate) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Note createNote() {
        return new Note.NoteBuilder()
                .setTitle(title)
                .setContent(content)
                .setCategory(category)
                .setCreatedAt(System.currentTimeMillis())
                .build();
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getTemplate() { return template; }
} 