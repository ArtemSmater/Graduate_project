package com.example.loginproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // database info
    private static final String DB_NAME = "clients.db";
    private static final int DB_VERSION = 1;

    // tables info
    private static final String TABLE_NAME = "person";
    private static final String ID = "_id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String NUMBER = "number";

    // database commands
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EMAIL + " TEXT, " + NAME + " TEXT, " + PASSWORD + " TEXT, " + NUMBER + " TEXT, UNIQUE(" + EMAIL + "))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SEARCH_VALUE = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean addItem(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, user.getEmail());
        cv.put(NAME, user.getName());
        cv.put(PASSWORD, user.getPassword());
        cv.put(NUMBER, user.getNumber());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }

    @SuppressLint("Range")
    public boolean checkExisted(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SEARCH_VALUE, new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public boolean checkCorrectPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery(SEARCH_VALUE, new String[]{email});
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(PASSWORD));
        }
        boolean isEquals = password.equals(result);
        cursor.close();
        db.close();
        return isEquals;
    }

    @SuppressLint("Range")
    public User getInfo(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User result = null;
        Cursor cursor = db.rawQuery(SEARCH_VALUE, new String[]{email});
        if (cursor.moveToFirst()) {
            result = new User(cursor.getString(cursor.getColumnIndex(EMAIL)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(NUMBER)));
        }
        cursor.close();
        db.close();
        return result;
    }
}
