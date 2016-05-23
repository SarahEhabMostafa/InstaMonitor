package com.sarahehabm.instamonitorlibrary.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;

/**
 * Created by Sarah E. Mostafa on 24-May-16.
 */
public class InstaMonitorFragment extends Fragment {
    private InstaMonitorFragmentModel model;

    private long startTimeStamp, endTimeStamp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = InstaMonitorDatabaseInterface.getFragment(getContext(), getClass().getName());
        if (model == null) {
            InstaMonitorDatabaseInterface.insertFragment(getContext(), getClass().getName());
            model = InstaMonitorDatabaseInterface.getFragment(getContext(), getClass().getName());
        }

        startTimeStamp = -1;
        endTimeStamp = -1;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(model!=null) {
            if (isVisibleToUser) {
                startTimeStamp = System.currentTimeMillis();
                Log.v(model.getName(), "START= " + startTimeStamp);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        endTimeStamp = System.currentTimeMillis();
        Log.v(model.getName(), "END= " + endTimeStamp);

        if (startTimeStamp != -1 && endTimeStamp != -1)
            InstaMonitorDatabaseInterface.updateFragment(getContext(), model.getName(),
                    endTimeStamp - startTimeStamp);
    }
}
