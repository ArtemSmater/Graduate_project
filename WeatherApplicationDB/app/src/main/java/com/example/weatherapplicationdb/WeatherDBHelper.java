package com.example.weatherapplicationdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WeatherDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "weather.db";

    public static final int DN_VERSION = 6;
    public WeatherDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DN_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WeatherContract.ItemEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(WeatherContract.ItemEntry.DROP_COMMAND);
        onCreate(db);
    }
}
