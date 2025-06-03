package com.example.fragmenthomework;

public class Car {

    private final String title;
    private final String description;
    private final int imgRes;

    public Car(String title, String description, int imgRes) {
        this.title = title;
        this.description = description;
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImgRes() {
        return imgRes;
    }
}
