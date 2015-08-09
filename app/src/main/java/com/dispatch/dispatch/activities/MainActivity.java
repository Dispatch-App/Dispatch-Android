package com.dispatch.dispatch.activities;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.dispatch.dispatch.R;
import com.dispatch.dispatch.models.Crime;
import com.dispatch.dispatch.utils.APIClient;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = "dispatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
