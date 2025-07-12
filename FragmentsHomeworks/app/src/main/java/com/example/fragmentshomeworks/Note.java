package com.example.fragmentshomeworks;

import androidx.annotation.NonNull;

public class Note {
    private final String title;
    private final String description;
    private final String date;
    private final String time;
    private final int priority;

    public Note(String title, String description, String date, String time, int priority) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPriority() {
        return priority;
    }
}
