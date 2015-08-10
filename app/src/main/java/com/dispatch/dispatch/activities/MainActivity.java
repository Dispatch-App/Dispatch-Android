package com.dispatch.dispatch.activities;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dispatch.dispatch.CrimeTypeDialog;
import com.dispatch.dispatch.DispatchHandler;
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

        Button helpMe = (Button) findViewById(R.id.btn_helpMe);
        Button seeCrime = (Button) findViewById(R.id.btn_seeCrime);

        helpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show dialog asking what kind of crime occurred
                new CrimeTypeDialog(MainActivity.this, new CrimeTypeDialog.CrimeTypeDialogListener() {
                    @Override
                    public void sendHelp(Crime.Type type) {
                        // The user selected a crime
                        new DispatchHandler(MainActivity.this).dispatch(type);
                    }
                });
            }
        });
    }
}
