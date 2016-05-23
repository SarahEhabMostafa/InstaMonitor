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

/**
 * Created by Sarah E. Mostafa on 23-May-16.
 */
public class InstaMonitorDatabaseInterface {
    private static final String TAG = InstaMonitorDatabaseInterface.class.getSimpleName();

    /*public static Uri insertActivity(Context context) {
        Random random = new Random();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_ID, random.nextInt(5000));
        contentValues.put(ActivityEntry.COLUMN_NAME, "ActivityName" + random.nextInt(50));
        contentValues.put(ActivityEntry.COLUMN_IS_IGNORED, random.nextBoolean());
        contentValues.put(ActivityEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(ActivityEntry.CONTENT_URI, contentValues);
    }*/

    public static Uri insertActivity(Context context, String activityName, boolean isIgnored) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_NAME, activityName);
        contentValues.put(ActivityEntry.COLUMN_IS_IGNORED, isIgnored);
        contentValues.put(ActivityEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(ActivityEntry.CONTENT_URI, contentValues);
    }

    public static int updateActivity(Context context, String activityName, boolean isIgnored) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityEntry.COLUMN_IS_IGNORED, isIgnored);

        int numUpdated =
                context.getContentResolver().update(ActivityEntry.CONTENT_URI, contentValues,
                        ActivityEntry.COLUMN_NAME + " = ? ", new String[]{activityName});
        return numUpdated;
    }

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
            return model;
        }

        return null;
    }

    public static int getActivityTime(Context context, String activityName) {
        /*Cursor cursor = context.getContentResolver().query(ActivityEntry.CONTENT_URI,
                new String[]{ActivityEntry.COLUMN_TOTAL_TIME}, ActivityEntry.COLUMN_NAME + " = ?",
                new String[]{activityName}, null);

        if (cursor == null || cursor.getCount() <= 0)
            return 0;

        if (cursor.moveToFirst()) {
            int time = cursor.getInt(cursor.getColumnIndex(ActivityEntry.COLUMN_TOTAL_TIME));
            return time;
        } else
            return 0;*/
        InstaMonitorActivityModel activityModel = getActivity(context, activityName);
        if(activityModel == null)
            return 0;

        return activityModel.getTotalTime();
    }

    public static String getAllActivities(Context context) {
        String result = "";
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

            result += "ActivityId: " + activityId + "\n"
                    + "ActivityName: " + activityName + "\n"
                    + "ActivityIsIgnored: " + isIgnored + "\n"
                    + "ActivityTotalTime: " + totalTime + "\n\n";
        }

        cursor.close();
        return result;
    }

    /*public static Uri insertFragment(Context context) {
        Random random = new Random();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FragmentEntry.COLUMN_ID, random.nextInt(5000));
        contentValues.put(FragmentEntry.COLUMN_NAME, "FragmentName" + random.nextInt(50));
        contentValues.put(FragmentEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(FragmentEntry.CONTENT_URI, contentValues);
    }*/
    public static Uri insertFragment(Context context, String fragmentName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FragmentEntry.COLUMN_NAME, fragmentName);
        contentValues.put(FragmentEntry.COLUMN_TOTAL_TIME, 0);

        return context.getContentResolver().insert(FragmentEntry.CONTENT_URI, contentValues);
    }

    public static int updateFragment(Context context, String fragmentName, long totalTime) {
        int oldTime = getFragmentTime(context, fragmentName);
        Log.v(TAG+"_FRAG", "oldTime= " + oldTime);
        long combinedTime = totalTime + oldTime;
        Log.v(TAG+"_FRAG", "combinedTime= " + combinedTime);

        ContentValues contentValues = new ContentValues();
        contentValues.put(FragmentEntry.COLUMN_TOTAL_TIME, combinedTime);

        int numUpdated =
                context.getContentResolver().update(FragmentEntry.CONTENT_URI, contentValues,
                        FragmentEntry.COLUMN_NAME + " = ? ", new String[]{fragmentName});
        Log.v(TAG+"_FRAG", "numUpdated= " + numUpdated);
        return numUpdated;
    }

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
            return model;
        }

        return null;
    }

    public static int getFragmentTime(Context context, String fragmentName) {
        InstaMonitorFragmentModel fragmentModel = getFragment(context, fragmentName);
        if(fragmentModel == null)
            return 0;

        return fragmentModel.getTotalTime();
    }

    public static String getAllFragments(Context context) {
        String result = "";
        Cursor cursor = context.getContentResolver().
                query(FragmentEntry.CONTENT_URI, null, null, null, null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getFragments count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int fragmentId = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_ID));
            String fragmentName = cursor.getString(cursor.getColumnIndex(FragmentEntry.COLUMN_NAME));
            int totalTime = cursor.getInt(cursor.getColumnIndex(FragmentEntry.COLUMN_TOTAL_TIME));

            result += "FragmentId: " + fragmentId + "\n"
                    + "FragmentName: " + fragmentName + "\n"
                    + "FragmentTotalTime: " + totalTime + "\n\n";
        }

        cursor.close();
        return result;
    }

    public static int clearAllData(Context context) {
        int numActivitiesDeleted =
                context.getContentResolver().delete(ActivityEntry.CONTENT_URI, null, null);
        int numFragmentsDeleted =
                context.getContentResolver().delete(FragmentEntry.CONTENT_URI, null, null);

        return numActivitiesDeleted + numFragmentsDeleted;
    }

    public static int getApplicationTime(Context context) {
        int totalTime = 0;

        Cursor cursor = context.getContentResolver().
                query(ActivityEntry.CONTENT_URI, new String[]{ActivityEntry.COLUMN_TOTAL_TIME},
                        null, null, null);

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
