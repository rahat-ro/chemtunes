package com.programmersjail.chemtunesorganicchemistry.notes;

public class NoteModel {

    private String noteTitle,noteURL;

    public NoteModel(String noteTitle, String noteURL) {
        this.noteTitle = noteTitle;
        this.noteURL = noteURL;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteURL() {
        return noteURL;
    }
}
