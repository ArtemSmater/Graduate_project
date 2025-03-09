package com.example.mynotes.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mynotes.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static final String DB_NAME = "notes3.db";
    private static final Object LOCK = new Object();
    private static NotesDatabase database;

    public static NotesDatabase getInstance(Context context){
        synchronized (LOCK){
            if(database == null){
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract NotesDao notesDao();
}
