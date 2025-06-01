package com.notetaking.patterns.decorator;

import com.notetaking.models.Note;

public class MarkdownDecorator extends NoteDecorator {
    
    public MarkdownDecorator(Note note) {
        super(note);
    }

    @Override
    public String getFormattedContent() {
        String content = note.getContent();
        // Apply basic Markdown formatting
        content = content.replaceAll("\\*\\*(.+?)\\*\\*", "<b>$1</b>"); // Bold
        content = content.replaceAll("\\*(.+?)\\*", "<i>$1</i>"); // Italic
        content = content.replaceAll("\\`(.+?)\\`", "<code>$1</code>"); // Code
        return content;
    }

    @Override
    public String getFormattedTitle() {
        return "# " + note.getTitle();
    }
} 