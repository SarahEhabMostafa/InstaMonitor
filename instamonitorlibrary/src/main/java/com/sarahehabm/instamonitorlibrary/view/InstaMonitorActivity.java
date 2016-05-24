package com.sarahehabm.instamonitorlibrary.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivityModel;

/**
 Created by Sarah E. Mostafa on 23-May-16.
 <p/>
 Activities should extend this class to be able to monitor the duration
 */
public class InstaMonitorActivity extends AppCompatActivity {
    private InstaMonitorActivityModel model;

    private long startTimeStamp, endTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = InstaMonitorDatabaseInterface.getActivity(this, getClass().getName());
        if (model == null) {
            InstaMonitorDatabaseInterface.insertActivity(this, getClass().getName(), false);
            model = InstaMonitorDatabaseInterface.getActivity(this, getClass().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startTimeStamp = System.currentTimeMillis();
        Log.v(model.getName(), "START= " + startTimeStamp);
    }

    @Override
    protected void onStop() {
        super.onStop();

        endTimeStamp = System.currentTimeMillis();
        Log.v(model.getName(), "END= " + endTimeStamp);

        InstaMonitorDatabaseInterface.updateActivity(this, model.getName(), endTimeStamp - startTimeStamp);
    }

    /**
     Returns whether the current activity is ignored or not. When an activity is ignored, the time
     spent in it is not included in the total time of the application

     @return true if it is ignored else if it is not ignored
     */
    public boolean isIgnored() {
        return model.isIgnored();
    }

    /**
     Sets whether the current activity is ignored or not. When an activity is ignored, the time spent
     in it is not included in the total time of the application

     @param ignored
     true if it is ignored else if it is not ignored
     */
    public void setIgnored(boolean ignored) {
        model.setIgnored(isIgnored());
        InstaMonitorDatabaseInterface.updateActivity(this, model.getName(), ignored);
    }
}
