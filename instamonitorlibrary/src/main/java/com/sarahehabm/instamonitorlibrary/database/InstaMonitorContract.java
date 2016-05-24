package com.sarahehabm.instamonitorlibrary.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sarah E. Mostafa on 23-May-16.
 */
public class InstaMonitorContract {
    public static final String CONTENT_AUTHORITY = "com.sarahehabm.instamonitorlibrary";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ACTIVITY = "activity";
    public static final String PATH_FRAGMENT = "fragment";

    public static final class ActivityEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "activity";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IS_IGNORED = "isIgnored";
        public static final String COLUMN_TOTAL_TIME = "totalTime";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIVITY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTIVITY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTIVITY;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_IS_IGNORED + " INTEGER, "
                + COLUMN_TOTAL_TIME + " INTEGER "
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }

    public static final class FragmentEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "fragment";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TOTAL_TIME = "totalTime";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FRAGMENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRAGMENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRAGMENT;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_TOTAL_TIME + " INTEGER "
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }

}
