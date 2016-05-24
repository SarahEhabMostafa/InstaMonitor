package com.sarahehabm.instamonitorlibrary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorContract.*;

/**
 Created by Sarah E. Mostafa on 23-May-16.
 <p/>
 DatabaseHelper class
 */
public class InstaMonitorDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "InstaMonitor.db";

    public InstaMonitorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ActivityEntry.createTable(db);
        FragmentEntry.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ActivityEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FragmentEntry.TABLE_NAME);

        onCreate(db);
    }
}
