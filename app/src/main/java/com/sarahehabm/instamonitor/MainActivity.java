package com.sarahehabm.instamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivity;

public class MainActivity extends InstaMonitorActivity {
    private static final String KEY_TEXT = "text";
//    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = (TextView) findViewById(R.id.textViewData);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        if(textView!=null)
//            outState.putString(KEY_TEXT, textView.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        if(textView!=null)
//            textView.setText(savedInstanceState.getString(KEY_TEXT));
    }

    /*public void onGetActivitiesClick(View view) {
        String activities = InstaMonitorDatabaseInterface.getAllActivities(this);
//        textView.setText(activities);
    }

    public void onGetFragmentsClick(View view) {
        String fragments = InstaMonitorDatabaseInterface.getAllFragments(this);
//        textView.setText(fragments);
    }*/

    public void onClearDataClick(View view) {
        int deletedRows = InstaMonitorDatabaseInterface.clearAllData(this);
//        textView.setText(String.valueOf(deletedRows));
    }

    public void onStartActivity1Click(View view) {
        Intent intent = new Intent(this, Activity1.class);
        startActivity(intent);
    }

    public void onStartActivity2Click(View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void onStartActivity3Click(View view) {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    /*public void onGetApplicationTimeClick(View view) {
        int totalTime = InstaMonitorDatabaseInterface.getApplicationTime(this);
//        textView.setText("Total time spent in application = " + totalTime + " ms");
    }*/

    public void onViewStatsClick(View view) {
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }
}
