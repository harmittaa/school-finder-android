package com.windhoek.hackathon.schoolfinder;

import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;

import java.util.ArrayList;

/**
 * Created by Asus on 05/03/2017.
 */

public class DataHandlerSingleton {
    private static DataHandlerSingleton dataHandlerSingleton = new DataHandlerSingleton();
    private ArrayList<SchoolObject> schoolObjects;

    private DataHandlerSingleton() {
        schoolObjects = new ArrayList<>();
    };

    public static DataHandlerSingleton getDataHandlerSingleton() {
        return dataHandlerSingleton;
    }


    public ArrayList<SchoolObject> getSchoolObjects() {
        return schoolObjects;
    }


    public void setSchoolObjects(ArrayList<SchoolObject> schoolObjects) {
        this.schoolObjects = schoolObjects;
    }
}
