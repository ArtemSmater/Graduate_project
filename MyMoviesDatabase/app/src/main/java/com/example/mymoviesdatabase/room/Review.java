package com.example.mymoviesdatabase.room;

public class Review {

    private String author;
    private String content;
    private String updated;

    public Review(String author, String content, String updated) {
        this.author = author;
        this.content = content;
        this.updated = updated;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
