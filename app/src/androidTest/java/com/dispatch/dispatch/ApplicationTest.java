package com.dispatch.dispatch;

import android.app.Application;
import android.location.Location;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.dispatch.dispatch.activities.MainActivity;
import com.dispatch.dispatch.models.Crime;
import com.dispatch.dispatch.utils.APIClient;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "";

    public ApplicationTest() {
        super(Application.class);

        // TODO: Setup the tests properly
    }

    public void testAddCrime() {
        Crime crime = new Crime();
        crime.setLongitude(77);
        crime.setLatitude(55);
        crime.setType(Crime.Type.ROBBERY);

        new APIClient().addCrime(crime, new APIClient.APICallback() {
            @Override
            public void success(Object o) {
                Log.d(TAG, "success");
            }

            @Override
            public void error(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        });
    }

    public void testGetCrimes() {
        Location location = new Location("");
        location.setLatitude(15);
        location.setLatitude(5);

        new APIClient().getCrimes(location, 25000, new APIClient.APICallback() {
            @Override
            public void success(Object o) {
                Log.d(TAG, "success");
            }

            @Override
            public void error(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        });
    }
}