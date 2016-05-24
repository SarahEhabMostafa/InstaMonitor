package com.sarahehabm.instamonitorlibrary.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorContract.*;

/**
 Created by Sarah E. Mostafa on 23-May-16.
 */
public class InstaMonitorContentProvider extends ContentProvider {
    private static final String TAG = InstaMonitorContentProvider.class.getSimpleName();
    private InstaMonitorDatabaseHelper databaseHelper;
    private UriMatcher uriMatcher = buildUriMatcher();

    static final int ACTIVITY = 100;
    static final int ACTIVITY_BY_ID = 101;
    static final int FRAGMENT = 200;
    static final int FRAGMENT_BY_ID = 201;

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = InstaMonitorContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, InstaMonitorContract.PATH_ACTIVITY, ACTIVITY);
        uriMatcher.addURI(authority, InstaMonitorContract.PATH_ACTIVITY + "/#", ACTIVITY_BY_ID);
        uriMatcher.addURI(authority, InstaMonitorContract.PATH_FRAGMENT, FRAGMENT);
        uriMatcher.addURI(authority, InstaMonitorContract.PATH_FRAGMENT + "/#", FRAGMENT_BY_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new InstaMonitorDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final int match = uriMatcher.match(uri);
        Cursor cursor;
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        switch (match) {
            case ACTIVITY:
                cursor = readableDatabase.query(ActivityEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case ACTIVITY_BY_ID:
                String activityId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(ActivityEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, ActivityEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{activityId}),
                        null, null, sortOrder);
                break;

            case FRAGMENT:
                cursor = readableDatabase.query(FragmentEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case FRAGMENT_BY_ID:
                String fragmentId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(FragmentEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, FragmentEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{fragmentId}),
                        null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ACTIVITY:
                return ActivityEntry.CONTENT_TYPE;

            case ACTIVITY_BY_ID:
                return ActivityEntry.CONTENT_ITEM_TYPE;

            case FRAGMENT:
                return FragmentEntry.CONTENT_TYPE;

            case FRAGMENT_BY_ID:
                return FragmentEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Uri returnUri;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case ACTIVITY: {
                long id = database.insert(ActivityEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = ActivityEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case FRAGMENT: {
                long id = database.insert(FragmentEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = FragmentEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(returnUri, null);
        Log.v(TAG, "Inserted: " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = 0;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case ACTIVITY:
                rowsDeleted = database.delete(ActivityEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case FRAGMENT:
                rowsDeleted = database.delete(FragmentEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = 0;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case ACTIVITY:
                rowsUpdated =
                        database.update(ActivityEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case FRAGMENT:
                rowsUpdated =
                        database.update(FragmentEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    private String concatenateSelection(String selection, String newSelection) {
        if (selection == null)
            return newSelection;

        return selection + " AND " + newSelection;
    }

    private String[] concatenateSelectionArgs(String[] selectionArgs, String[] newSelectionArgs) {
        if (selectionArgs == null)
            return newSelectionArgs;

        int size = selectionArgs.length + newSelectionArgs.length;
        String[] concatenatedSelectionArgs = new String[size];
        int i = 0;
        for (; i < size; i++) {
            concatenatedSelectionArgs[i] = selectionArgs[i];
        }

        for (int j = 0; j < newSelectionArgs.length; i++, j++) {
            concatenatedSelectionArgs[i] = newSelectionArgs[j];
        }

        return concatenatedSelectionArgs;
    }
}