package com.sarahehabm.instamonitorlibrary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorContract.ActivityEntry;
import com.sarahehabm.instamonitorlibrary.database.InstaMonitorContract.FragmentEntry;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivityModel;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorFragmentModel;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 23-May-16.
 <p/>
 InstaMonitorDatabaseInterface is a class exposing the methods that the user might need to get,
 update, insert and delete activities, fragments and their data and times
 */
public class InstaMonitorDatabaseInterface {
    private static final String TAG = InstaMonitorDatabaseInterface.class.getSimpleName();

    /**
     Inserts a new activity in the database

     @param context
     @param activityName
     @param isIgnored

     @return insertion uri
     */
    public static Uri insertActivity(Context context, String activityName, boolean isIgnored) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_NAME, activityName);
        contentValues.put(ActivityEntry.COLUMN_IS_IGNORED, isIgnored);
        contentValues.put(ActivityEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(ActivityEntry.CONTENT_URI, contentValues);
    }

    /**
     Updates an activity's 'ignored' value in the database. Activity is identified by its name

     @param context
     @param activityName
     @param isIgnored

     @return number of the updated rows
     */
    public static int updateActivity(Context context, String activityName, boolean isIgnored) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_IS_IGNORED, isIgnored);

        int numUpdated =
                context.getContentResolver().update(ActivityEntry.CONTENT_URI, contentValues,
                        ActivityEntry.COLUMN_NAME + " = ? ", new String[]{activityName});
        return numUpdated;
    }

    /**
     Updates an activity's 'totalTime' value in the database. Activity is identified by its name

     @param context
     @param activityName
     @param totalTime

     @return number of the updated rows
     */
    public static int updateActivity(Context context, String activityName, long totalTime) {
        int oldTime = getActivityTime(context, activityName);
        Log.v(TAG, "oldTime= " + oldTime);
        long combinedTime = totalTime + oldTime;
        Log.v(TAG, "combinedTime= " + combinedTime);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_TOTAL_TIME, combinedTime);

        int numUpdated =
                context.getContentResolver().update(ActivityEntry.CONTENT_URI, contentValues,
                        ActivityEntry.COLUMN_NAME + " = ? ", new String[]{activityName});
        Log.v(TAG, "numUpdated= " + numUpdated);
        return numUpdated;
    }

    /**
     Gets an activity from the database by its name

     @param context
     @param activityName

     @return InstaMonitorActivityModel or null if the specified name was not found in the database
     */
    public static InstaMonitorActivityModel getActivity(Context context, String activityName) {
        Cursor cursor = context.getContentResolver().query(ActivityEntry.CONTENT_URI,
                null, ActivityEntry.COLUMN_NAME + " = ?", new String[]{activityName}, null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(ActivityEntry.COLUMN_NAME));
            int isIgnoredInt = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_IS_IGNORED));
            boolean isIgnored = isIgnoredInt == 0 ? false : true;
            int totalTime = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_TOTAL_TIME));

            InstaMonitorActivityModel model =
                    new InstaMonitorActivityModel(id, name, isIgnored, totalTime);

            cursor.close();
            return model;
        }

        cursor.close();
        return null;
    }

    /**
     Gets an activity's 'totalTime' from the database by its name

     @param context
     @param activityName

     @return Activity's totalTime in milliseconds
     */
    public static int getActivityTime(Context context, String activityName) {
        InstaMonitorActivityModel activityModel = getActivity(context, activityName);
        if (activityModel == null)
            return 0;

        return activityModel.getTotalTime();
    }

    /**
     Gets all the activities from the database

     @param context

     @return ArrayList<InstaMonitorActivityModel>
     */
    public static ArrayList<InstaMonitorActivityModel> getActivities(Context context) {
        ArrayList<InstaMonitorActivityModel> activities = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(ActivityEntry.CONTENT_URI, null, null, null, null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getActivities count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int activityId = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_ID));
            String activityName = cursor.getString(cursor.getColumnIndex(ActivityEntry.COLUMN_NAME));
            int ignoredInt = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_IS_IGNORED));
            boolean isIgnored = ignoredInt == 1;
            int totalTime = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_TOTAL_TIME));

            InstaMonitorActivityModel activityModel = new InstaMonitorActivityModel(activityId,
                    activityName, isIgnored, totalTime);
            activities.add(activityModel);
        }

        cursor.close();
        return activities;
    }

    /**
     Inserts a new fragment in the database

     @param context
     @param fragmentName

     @return insertion uri
     */
    public static Uri insertFragment(Context context, String fragmentName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FragmentEntry.COLUMN_NAME, fragmentName);
        contentValues.put(FragmentEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(FragmentEntry.CONTENT_URI, contentValues);
    }

    /**
     Updates a fragment's 'totalTime' value in the database. Fragment is identified by its name

     @param context
     @param fragmentName
     @param totalTime

     @return number of the updated rows
     */
    public static int updateFragment(Context context, String fragmentName, long totalTime) {
        int oldTime = getFragmentTime(context, fragmentName);
        Log.v(TAG + "_FRAG", "oldTime= " + oldTime);
        long combinedTime = totalTime + oldTime;
        Log.v(TAG + "_FRAG", "combinedTime= " + combinedTime);

        ContentValues contentValues = new ContentValues();
        contentValues.put(FragmentEntry.COLUMN_TOTAL_TIME, combinedTime);

        int numUpdated =
                context.getContentResolver().update(FragmentEntry.CONTENT_URI, contentValues,
                        FragmentEntry.COLUMN_NAME + " = ? ", new String[]{fragmentName});
        Log.v(TAG + "_FRAG", "numUpdated= " + numUpdated);
        return numUpdated;
    }

    /**
     Gets a fragment from the database by its name

     @param context
     @param fragmentName

     @return InstaMonitorFragmentModel or null if the specified name was not found in the database
     */
    public static InstaMonitorFragmentModel getFragment(Context context, String fragmentName) {
        Cursor cursor = context.getContentResolver().query(FragmentEntry.CONTENT_URI,
                null, FragmentEntry.COLUMN_NAME + " = ? ", new String[]{fragmentName}, null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(FragmentEntry.COLUMN_NAME));
            int totalTime = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_TOTAL_TIME));

            InstaMonitorFragmentModel model =
                    new InstaMonitorFragmentModel(id, name, totalTime);
            cursor.close();
            return model;
        }

        cursor.close();
        return null;
    }

    /**
     Gets a fragment's 'totalTime' from the database by its name

     @param context
     @param fragmentName

     @return Fragment's totalTime in milliseconds
     */
    public static int getFragmentTime(Context context, String fragmentName) {
        InstaMonitorFragmentModel fragmentModel = getFragment(context, fragmentName);
        if (fragmentModel == null)
            return 0;

        return fragmentModel.getTotalTime();
    }

    /**
     Gets all the fragments from the database

     @param context

     @return ArrayList<InstaMonitorFragmentModel>
     */
    public static ArrayList<InstaMonitorFragmentModel> getFragments(Context context) {
        ArrayList<InstaMonitorFragmentModel> fragments = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(FragmentEntry.CONTENT_URI, null, null, null, null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getFragments count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int fragmentId = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_ID));
            String fragmentName = cursor.getString(cursor.getColumnIndex(FragmentEntry.COLUMN_NAME));
            int totalTime = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_TOTAL_TIME));

            InstaMonitorFragmentModel fragmentModel = new InstaMonitorFragmentModel(fragmentId,
                    fragmentName, totalTime);
            fragments.add(fragmentModel);
        }

        cursor.close();
        return fragments;
    }

    /**
     Deletes all the data from both the activity and fragment tables

     @param context

     @return number of rows deleted
     */
    public static int clearAllData(Context context) {
        int numActivitiesDeleted =
                context.getContentResolver().delete(ActivityEntry.CONTENT_URI, null, null);
        int numFragmentsDeleted =
                context.getContentResolver().delete(FragmentEntry.CONTENT_URI, null, null);

        return numActivitiesDeleted + numFragmentsDeleted;
    }

    /**
     Gets the total time of the application. The total time is calculated by adding the totalTimes of
     all the activities that are not ignored

     @param context

     @return Total time of the application in milliseconds
     */
    public static int getApplicationTime(Context context) {
        int totalTime = 0;

        Cursor cursor = context.getContentResolver().
                query(ActivityEntry.CONTENT_URI, new String[]{ActivityEntry.COLUMN_TOTAL_TIME},
                        ActivityEntry.COLUMN_IS_IGNORED + " = ? ", new String[]{"0"}, null);

        if (cursor == null || cursor.getCount() <= 0)
            return 0;

        Log.v(TAG, "getActivities count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int time = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_TOTAL_TIME));

            totalTime += time;
        }

        cursor.close();
        return totalTime;
    }
}
