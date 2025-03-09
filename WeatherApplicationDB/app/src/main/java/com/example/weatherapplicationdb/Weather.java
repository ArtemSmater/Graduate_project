package com.example.weatherapplicationdb;

public class Weather {

    private String title;
    private String info;
    private String imgURL;
    private int id;

    public Weather(String title, String info, String imgURL, int id) {
        this.title = title;
        this.info = info;
        this.imgURL = imgURL;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getImgURL() {
        return imgURL;
    }

    public int getId() {
        return id;
    }
}
