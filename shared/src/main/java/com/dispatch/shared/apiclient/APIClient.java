package com.dispatch.shared.apiclient;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.dispatch.dispatch.models.Crime;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

/**
 * Created by Daniel on 8/8/2015.
 */
public class APIClient {
    private static final String TAG = "utils";
    private Exception exception;

    public void addCrime(final Crime crime, final APICallback callback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                JSONObject reqBody = new JSONObject();

                try {
                    // Build a json object from the crime
                    reqBody.put(Config.JSONConfig.KEY_LATITUDE, crime.getLatitude());
                    reqBody.put(Config.JSONConfig.KEY_LONGITUDE, crime.getLongitude());
                    reqBody.put(Config.JSONConfig.KEY_TYPE, crime.getType());

                    // Fire the post request
                    JSONObject response = firePostRequest(Config.APIConfig.ENDPOINT_ADD_CRIME, reqBody);

                    if(response.getBoolean(Config.JSONConfig.KEY_SUCCESS)) {
                        // Successful in adding the crime
                        return null;
                    }

                } catch(Exception e) {
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if(exception == null) {
                    // Success
                    callback.success(null);
                }
                else {
                    // Failure
                    callback.error(exception);
                }
            }

        }.execute();
    }

    public void getCrimes(final Location location, final float range, final APICallback callback) {
        final List<Crime> crimes = new ArrayList<Crime>();

        new AsyncTask<Void, Void, List<Crime>>() {

            @Override
            protected List<Crime> doInBackground(Void... params) {
                try {
                    // Params for the request
                    Map<String, Object> urlParams = new LinkedHashMap<String, Object>();
                    urlParams.put(Config.JSONConfig.KEY_LATITUDE, location.getLatitude());
                    urlParams.put(Config.JSONConfig.KEY_LONGITUDE, location.getLongitude());
                    urlParams.put(Config.JSONConfig.KEY_RANGE, range);

                    // Fire the GET request and grab the response
                    JSONObject response = fireGetRequest(Config.APIConfig.ENDPOINT_GET_CRIMES, urlParams);

                    if(response.getBoolean(Config.JSONConfig.KEY_SUCCESS)) {
                        // Success... Grab the array of crimes
                        JSONArray crimeArray = response.getJSONArray(Config.JSONConfig.KEY_CRIMES);

                        int length = crimeArray.length();
                        for(int i = 0; i < length; i++) {
                            // Grab every crime
                            JSONObject crimeJSON = crimeArray.getJSONObject(i);

                            // Geo point (parse datatype) of the crime
                            JSONObject geoPoint = crimeJSON.getJSONObject(Config.JSONConfig.KEY_LOCATION);

                            // Convert the crime json into a crime object
                            Crime crime = new Crime();

                            // Extract the latitude/longitude from the geopoint
                            crime.setLatitude(geoPoint.getDouble(Config.JSONConfig.KEY_LATITUDE));
                            crime.setLongitude(geoPoint.getDouble(Config.JSONConfig.KEY_LONGITUDE));

                            // Date of the crime (timestamp of when parse object was created)
                            crime.setTimestamp(crimeJSON.getLong(Config.JSONConfig.KEY_TIME_STAMP));

                            // Set the type of the crime in regards to the enum
                            crime.setType(Crime.Type.valueOf(crimeJSON.getString(Config.JSONConfig.KEY_TYPE)));

                            crimes.add(crime);
                        }
                    }

                } catch(Exception e) {
                    exception = e;
                }

                return crimes;
            }

            @Override
            protected void onPostExecute(List<Crime> crimes) {
                super.onPostExecute(crimes);

                // Notify the callback of success or failure
                if(exception == null) {
                    callback.success(crimes);
                }
                else {
                    // Error happened
                    callback.error(exception);
                }
            }

        }.execute();
    }

    private JSONObject getResponse(HttpURLConnection connection) throws JSONException, IOException {
        return new JSONObject(Utils.readFullySync(connection.getInputStream()));
    }

    private JSONObject firePostRequest(String url, JSONObject jsonObject) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonObject.toString().getBytes());
        outputStream.flush();
        outputStream.close();

        Log.d(TAG, jsonObject.toString());

        return getResponse(connection);
    }

    private JSONObject fireGetRequest(String url, Map<String, Object> params) throws Exception {
        StringBuilder encoded = new StringBuilder();

        // Should the & be appended?
        boolean useSeperator = false;

        for(Map.Entry<String, Object> entry : params.entrySet()) {

            // Append the & if needed
            if(useSeperator) {
                encoded.append("&");
            }

            // Append key and value encoded
            encoded.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));

            useSeperator = true;
        }

        // Put the query string on the back of the URL
        url += "?" + encoded.toString();

        // Now make the request with the built URL
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        // Get the response
        return getResponse(connection);
    }

    private void useBasicAuth(HttpURLConnection connection) {
        //connection.setRequestProperty("Authentication", "Basic " );
    }

    private HttpURLConnection connect(String url) throws Exception {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    /**
     * Used as a listener and is notified under conditions of success or error
     */
    public static interface APICallback {
        void success(Object o);
        void error(Exception e);
    }
}
