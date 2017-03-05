package com.windhoek.hackathon.schoolfinder;

import android.util.Log;

import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;

import java.util.ArrayList;

/**
 * Created by Asus on 05/03/2017.
 */

public class DataHandlerSingleton {
    private static final String TAG = "DataHandlerSingleton";
    private static DataHandlerSingleton dataHandlerSingleton = new DataHandlerSingleton();
    private ArrayList<SchoolObject> schoolObjects;
    private ArrayList<SchoolObject> originalSchoolObjects;
    private ContextHolder contextHolder;
    private boolean dataLoaded;

    private DataHandlerSingleton() {
        schoolObjects = new ArrayList<>();
        originalSchoolObjects = new ArrayList<>();
    }

    public void createContextHolder(MainActivity mainActivity) {
        Log.e(TAG, "createContextHolder: CONTEXT HOLDER CREATED");
        this.contextHolder = new ContextHolder(mainActivity);
    }

    public ContextHolder getContextHolder() {
        return this.contextHolder;
    }

    public static DataHandlerSingleton getDataHandlerSingleton() {
        return dataHandlerSingleton;
    }

    public ArrayList<SchoolObject> getSchoolObjects() {
        return schoolObjects;
    }


    public void setSchoolObjects(ArrayList<SchoolObject> schoolObjects) {
        Log.e(TAG, "setSchoolObjects: DATA IS SET SIZE " + schoolObjects.size());
        this.schoolObjects = schoolObjects;
        Log.e(TAG, "setSchoolObjects: NEW SIZE " + this.schoolObjects.size() );
    }

    public ArrayList<SchoolObject> getOriginalSchoolObjects() {
        return originalSchoolObjects;
    }

    public void setOriginalSchoolObjects(ArrayList<SchoolObject> originalSchoolObjects) {
        this.originalSchoolObjects = originalSchoolObjects;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }
}
