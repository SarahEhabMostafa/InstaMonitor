package com.sarahehabm.instamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sarahehabm.instamonitorlibrary.database.InstaMonitorDatabaseInterface;
import com.sarahehabm.instamonitorlibrary.view.InstaMonitorActivity;

public class MainActivity extends InstaMonitorActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onViewStatsClick(View view) {
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }

    public void onClearDataClick(View view) {
        int deletedRows = InstaMonitorDatabaseInterface.clearAllData(this);
        Toast.makeText(this, "All data (" + deletedRows + " rows) successfully cleared",
                Toast.LENGTH_SHORT).show();
    }
}
