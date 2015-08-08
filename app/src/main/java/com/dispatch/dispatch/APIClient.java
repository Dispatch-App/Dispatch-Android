package com.dispatch.dispatch;

import android.location.Location;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.*;


/**
 * Created by Daniel on 8/8/2015.
 */
public class APIClient {
    private Exception exception;

    public void addCrime(Crime crime, APICallback callback) {

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

                            // Convert the crime json into a crime object
                            Crime crime = new Crime();

                            crime.setDate(crimeJSON.getString(Config.JSONConfig.KEY_DATE));

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

    private JSONObject getResponse(HttpURLConnection connection) throws  Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String temp;

        while((temp = bufferedReader.readLine()) != null) {
            response.append(temp);
        }

        return new JSONObject(response.toString());
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

    private HttpURLConnection connect(String url) throws Exception {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    public static interface APICallback {
        void success(Object o);
        void error(Exception e);
    }
}
