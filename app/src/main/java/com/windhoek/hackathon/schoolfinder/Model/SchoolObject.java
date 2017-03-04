package com.windhoek.hackathon.schoolfinder.Model;

/**
 * Created by Asus on 04/03/2017.
 */

public class SchoolObject {
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public SchoolObject(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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
}

