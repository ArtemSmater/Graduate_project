package com.example.filemanager;

public class Option {

    private final String title;
    private final int icon;

    public Option(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
