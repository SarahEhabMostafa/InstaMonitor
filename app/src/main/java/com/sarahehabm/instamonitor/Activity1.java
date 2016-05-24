package com.sarahehabm.instamonitor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sarahehabm.instamonitorlibrary.view.InstaMonitorActivity;

public class Activity1 extends InstaMonitorActivity
        implements CompoundButton.OnCheckedChangeListener {
    private Switch ignore_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        ignore_switch = (Switch) findViewById(R.id.switch1);
        if(isIgnored()) {
            ignore_switch.setChecked(true);
            ignore_switch.setTextOn(getString(R.string.ignore_activity_on));
        } else {
            ignore_switch.setChecked(false);
            ignore_switch.setTextOff(getString(R.string.ignore_activity_off));
        }
        ignore_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            setIgnored(true);
        else
            setIgnored(false);
    }
}
