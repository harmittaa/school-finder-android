package com.windhoek.hackathon.schoolfinder.Model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Asus on 04/03/2017.
 */

public class SchoolObject {
    private static final String TAG = "SchoolObject";
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isPublic;
    private String city;
    private String phoneNumber;
    private String region;
    private LatLng latLng;
    private boolean openPositions;
    private boolean lowerGrades;
    private boolean higherGrades;

    public SchoolObject() {

    }

    public SchoolObject(String name, String address, String latitude, String longitude, String isPublic, String city, String phoneNumber,
                        String region, boolean lowerGrades, boolean higherGrades, boolean hasSpace) {
        this.name = name;
        this.address = address;

        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
        if (isPublic.equals("Yes")) {
            this.isPublic = true;
        }
        try {
            this.latLng = new LatLng(this.latitude, this.longitude);
        } catch (Exception e) {
            Log.e(TAG, "SchoolObject: ", e);
        }

        this.city = city;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.openPositions = hasSpace;
        this.lowerGrades = lowerGrades;
        this.higherGrades = higherGrades;


    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegion() {
        return region;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public boolean hasOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(boolean openPositions) {
        this.openPositions = openPositions;
    }

    public boolean isLowerGrades() {
        return lowerGrades;
    }

    public void setLowerGrades(boolean lowerGrades) {
        this.lowerGrades = lowerGrades;
    }

    public boolean isHigherGrades() {
        return higherGrades;
    }

    public void setHigherGrades(boolean higherGrades) {
        this.higherGrades = higherGrades;
    }
}

