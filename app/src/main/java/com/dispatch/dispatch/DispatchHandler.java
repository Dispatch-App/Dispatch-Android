package com.dispatch.dispatch;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import com.dispatch.dispatch.activities.MainActivity;
import com.dispatch.dispatch.models.Crime;
import com.dispatch.dispatch.utils.APIClient;

/**
 * Created by Daniel on 8/10/2015.
 */
public class DispatchHandler {
    public static final String TAG = "";
    private Context context;

    public DispatchHandler(Context context) {
        this.context = context;
    }

    /**
     * Sends a new crime off to the server and notifies authorities
     * @param type Type of crime that happened
     */
    public void dispatch(Crime.Type type) {
        // Build a crime object
        Crime crime = new Crime();
        crime.setType(type);
        crime.setTimestamp(System.currentTimeMillis());
        crime.setLatitude(15);
        crime.setLongitude(22);

        // Notify the API
        new APIClient().addCrime(crime, new APIClient.APICallback() {
            @Override
            public void success(Object o) {
                Log.d(TAG, "success");
                Toast.makeText(context, "Help is on the way.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        });
    }
}
