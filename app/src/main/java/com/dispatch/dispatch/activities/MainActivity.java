package com.dispatch.dispatch.activities;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dispatch.dispatch.R;
import com.dispatch.dispatch.utils.APIClient;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = "dispatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location location = new Location("");
        location.setLatitude(15);
        location.setLatitude(5);

        new APIClient().getCrimes(location, 25000, new APIClient.APICallback() {
            @Override
            public void success(Object o) {
                Log.d(TAG, "success");
                Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        });
    }
}
