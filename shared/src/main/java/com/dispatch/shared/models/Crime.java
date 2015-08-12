package com.dispatch.shared.models;

import java.util.Date;

/**
 * Created by Daniel on 8/8/2015.
 */
public class Crime {
    private Type type;
    private long timestamp;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Timestamp: " + timestamp + " Latitude: " + latitude + " Longitude: " + longitude;
    }

    public static enum Type {
        SHOOTING,
        ROBBERY
    }
}
