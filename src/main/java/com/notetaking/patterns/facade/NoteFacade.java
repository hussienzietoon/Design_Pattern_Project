package com.notetaking.patterns.facade;

import com.notetaking.models.Note;
import com.notetaking.patterns.decorator.MarkdownDecorator;
import com.notetaking.patterns.prototype.NoteTemplate;
import com.notetaking.patterns.proxy.NoteProxy;
import java.util.List;
import java.util.ArrayList;

public class NoteFacade {
    private List<Note> notes;
    private List<NoteTemplate> templates;

    public NoteFacade() {
        this.notes = new ArrayList<>();
        this.templates = new ArrayList<>();
        initializeTemplates();
    }

    private void initializeTemplates() {
        templates.add(new NoteTemplate("Meeting", "Meeting Notes", "## Agenda\n## Participants\n## Action Items", "Work"));
        templates.add(new NoteTemplate("Todo", "Todo List", "- [ ] Task 1\n- [ ] Task 2", "Personal"));
    }

    public Note createNote(String title, String content, String category) {
        Note note = new Note.NoteBuilder()
                .setTitle(title)
                .setContent(content)
                .setCategory(category)
                .setCreatedAt(System.currentTimeMillis())
                .build();
        notes.add(note);
        return note;
    }

    public Note createNoteFromTemplate(String templateName) {
        for (NoteTemplate template : templates) {
            if (template.getTemplate().equals(templateName)) {
                Note note = template.createNote();
                notes.add(note);
                return note;
            }
        }
        return null;
    }

    public String getFormattedNote(Note note) {
        MarkdownDecorator decorator = new MarkdownDecorator(note);
        return decorator.getFormattedTitle() + "\n\n" + decorator.getFormattedContent();
    }

    public void deleteNote(Note note) {
        notes.remove(note);
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    public List<NoteTemplate> getAvailableTemplates() {
        return new ArrayList<>(templates);
    }
} 