package com.sarahehabm.instamonitor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sarahehabm.instamonitorlibrary.model.InstaMonitorActivity;

public class Activity2 extends InstaMonitorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        setIgnored(true);
    }
}
