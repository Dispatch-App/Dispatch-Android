package com.dispatch.shared.apiclient;

/**
 * Created by Daniel on 8/8/2015.
 */
public final class Config {

    public static final class APIConfig {
        public static final String ENDPOINT_GET_CRIMES = "http://192.168.1.182:3000/getCrimes";
        public static final String ENDPOINT_ADD_CRIME = "http://192.168.1.182:3000/addCrime";
    }

    public static final class ParseConfig {
        public static final String APPLICATION_ID = "P5bTIKqraePh3EgDKvOTaXbW3dmuvjKQBpN6vYWj";
        public static final String CLIENT_KEY = "XRn5bFk5KNhxWYy5vcV94f3iYTmtd6754xq1X6gk";
        public static final String CRIME_CHANNEL = "crime";
    }

    public static final class JSONConfig {
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_LONGITUDE = "longitude";
        public static final String KEY_TYPE = "type";
        public static final String KEY_RANGE = "range";
        public static final String KEY_CODE = "code";
        public static final String KEY_SUCCESS = "success";
        public static final String KEY_CRIMES = "crimes";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME_STAMP = "timestamp";
        public static final String KEY_LOCATION = "location";
    }

}
