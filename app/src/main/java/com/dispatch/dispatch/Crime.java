package com.dispatch.dispatch;

import java.util.Date;

/**
 * Created by Daniel on 8/8/2015.
 */
public class Crime {
    private Type type;
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Date: " + date + " Latitude: " + latitude + " Longitude: " + longitude;
    }

    public static enum Type {
        SHOOTING,
        ROBBERY
    }
}
