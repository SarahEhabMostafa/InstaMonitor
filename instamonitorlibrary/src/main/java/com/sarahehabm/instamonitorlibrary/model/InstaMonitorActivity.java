package com.sarahehabm.instamonitorlibrary.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;

/**
 * Created by Sarah E. Mostafa on 23-May-16.
 */
public class InstaMonitorActivity extends AppCompatActivity {
    private InstaMonitorActivityModel model;

    private long startTimeStamp, endTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = InstaMonitorDatabaseInterface.getActivity(this, getClass().getName());
        if(model == null) {
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

        InstaMonitorDatabaseInterface.updateActivity(this, model.getName(), endTimeStamp-startTimeStamp);
    }

    public boolean isIgnored() {
        return model.isIgnored();
    }

    public void setIgnored(boolean ignored) {
        model.setIgnored(isIgnored());
        InstaMonitorDatabaseInterface.updateActivity(this, model.getName(), ignored);
    }
}
