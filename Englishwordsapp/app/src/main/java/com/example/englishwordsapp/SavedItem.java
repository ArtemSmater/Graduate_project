package com.example.englishwordsapp;

public class SavedItem {

    private final String time;
    private final String name;
    private final String points;

    public SavedItem(String name, String time, String points) {
        this.name = name;
        this.points = points;
        this.time = time;
    }

    public String getPoints() {
        return points;
    }

    public String getTime() {
        return time;
    }
    public String getName() {
        return name;
    }
}
