package com.juan.deardiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juan on 6/8/16.
 */
public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "deardiary"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    DiaryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT);");

        insertNote(db, "first note", "Hello user, this is a sample note");
        insertNote(db, "second note", "Hello user, this is another sample note");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static void insertNote(SQLiteDatabase db, String name, String description) {
        ContentValues noteValues = new ContentValues();
        noteValues.put("NAME", name);
        noteValues.put("DESCRIPTION", description);
        db.insert("NOTES", null, noteValues);
    }



}
