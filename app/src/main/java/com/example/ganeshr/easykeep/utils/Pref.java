package com.example.ganeshr.easykeep.utils;

import java.io.Serializable;

/**
 * Created by root on 22/9/17.
 */

public class Pref implements Serializable {
    private static Pref prefInstance = null; // the only instance of the class
    private String noteTilte="";
    private String noteText="";
    private String date="";

    public static Pref getInstance()
    {
        if (prefInstance == null)
        {
            prefInstance = new Pref();
        }
        return prefInstance;
    }

    public String getNoteTilte() {
        return noteTilte;
    }

    public void setNoteTilte(String noteTilte) {
        this.noteTilte = noteTilte;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
