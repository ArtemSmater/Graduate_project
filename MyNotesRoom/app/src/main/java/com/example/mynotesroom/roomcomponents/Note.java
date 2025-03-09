package com.example.mynotesroom.roomcomponents;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    private String title;
    private String description;
    private int weekDay;
    private int priority;
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    public Note(String title, String description, int weekDay, int priority) {
        this.title = title;
        this.description = description;
        this.weekDay = weekDay;
        this.priority = priority;
    }

    public Note(String title, String description, int weekDay, int priority, int id) {
        this.title = title;
        this.description = description;
        this.weekDay = weekDay;
        this.priority = priority;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeekDayByPosition(int position){
        String day;
        switch (position + 1){
            case 1 :
                day = "monday";
                break;
            case 2 :
                day = "tuesday";
                break;
            case 3 :
                day = "wednesday";
                break;
            case 4 :
                day = "thursday";
                break;
            case 5 :
                day = "friday";
                break;
            case 6 :
                day = "saturday";
                break;
            default:
                day = "sunday";
                break;
        }
        return day;
    }
}
