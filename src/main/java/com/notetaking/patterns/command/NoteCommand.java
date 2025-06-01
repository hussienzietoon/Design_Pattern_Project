package com.notetaking.patterns.command;

public interface NoteCommand {
    void execute();
    void undo();
} 