package com.dispatch.dispatch;

/**
 * Created by Daniel on 8/8/2015.
 */
public final class Config {

    public static final class APIConfig {
        public static final String ENDPOINT_GET_CRIMES = "http://192.168.1.182:3000/getCrimes";
        public static final String ENDPOINT_ADD_CRIME = "http://192.168.1.182:3000/addCrime";
    }

    public static final class JSONConfig {
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_LONGITUDE = "longitude";
        public static final String KEY_TYPE = "type";
        public static final String KEY_RANGE = "range";
        public static final String KEY_CODE = "code";
        public static final String KEY_SUCCESS = "success";
        public static final String KEY_CRIMES = "crimes";
        public static final String KEY_DATE = "createdAt";
    }

}
