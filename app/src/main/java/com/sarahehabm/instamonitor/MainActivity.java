package com.sarahehabm.instamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;
import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivity;

public class MainActivity extends InstaMonitorActivity {
    private TextView textView;

//    private InstaMonitorDatabaseInterface databaseInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setName(MainActivity.class.getName());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textViewData);
    }

    public void onInsertActivityClick(View view) {
//        Uri uri = InstaMonitorDatabaseInterface.insertActivity(this);
//        textView.setText(uri.getPath());
    }

    public void onInsertFragmentClick(View view) {
//        Uri uri = InstaMonitorDatabaseInterface.insertFragment(this);
//        textView.setText(uri.getPath());
    }

    public void onGetActivitiesClick(View view) {
        String activities = InstaMonitorDatabaseInterface.getAllActivities(this);
        textView.setText(activities);
    }

    public void onGetFragmentsClick(View view) {
        String fragments = InstaMonitorDatabaseInterface.getAllFragments(this);
        textView.setText(fragments);
    }

    public void onClearDataClick(View view) {
        int deletedRows = InstaMonitorDatabaseInterface.clearAllData(this);
        textView.setText(String.valueOf(deletedRows));
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

    public void onGetApplicationTimeClick(View view) {
        int totalTime = InstaMonitorDatabaseInterface.getApplicationTime(this);
        textView.setText("Total time spent in application = " + totalTime + " ms");
    }
}
