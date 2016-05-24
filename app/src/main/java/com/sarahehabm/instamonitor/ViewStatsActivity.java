package com.sarahehabm.instamonitor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivity;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivityModel;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorFragmentModel;

import java.util.ArrayList;

public class ViewStatsActivity extends InstaMonitorActivity {
    private TextView textViewTotalTime, textViewActivities, textViewFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        textViewTotalTime = (TextView) findViewById(R.id.textView_time);
        textViewActivities = (TextView) findViewById(R.id.textView_activities);
        textViewFragments = (TextView) findViewById(R.id.textView_fragments);

        int totalTime = InstaMonitorDatabaseInterface.getApplicationTime(this);
        textViewTotalTime.setText(getString(R.string.total_time, totalTime));

        ArrayList<InstaMonitorActivityModel> activityModels =
                InstaMonitorDatabaseInterface.getActivities(this);
        String activitiesSummary = "";
        if (activityModels != null) {
            for (InstaMonitorActivityModel activityModel : activityModels) {
                activitiesSummary += activityModel.getName() + "\n"
                        + activityModel.getTotalTime() + " ms\n\n";
            }
        } else
            activitiesSummary = getString(R.string.no_data);
        textViewActivities.setText(activitiesSummary);

        ArrayList<InstaMonitorFragmentModel> fragmentModels =
                InstaMonitorDatabaseInterface.getFragments(this);
        String fragmentsSummary = "";
        if (fragmentModels != null) {
            for (InstaMonitorFragmentModel fragmentModel : fragmentModels) {
                fragmentsSummary += fragmentModel.getName() + "\n"
                        + fragmentModel.getTotalTime() + " ms\n\n";
            }
        } else
            fragmentsSummary = getString(R.string.no_data);
        textViewFragments.setText(fragmentsSummary);

    }
}
