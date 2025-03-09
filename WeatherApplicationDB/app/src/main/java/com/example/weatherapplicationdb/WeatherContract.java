package com.example.weatherapplicationdb;

import android.provider.BaseColumns;

public class WeatherContract {

    public static final class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URLS = "resURL";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + _ID +
                " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " " + TYPE_TEXT + ", " +
                COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_URLS + " " + TYPE_TEXT + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
