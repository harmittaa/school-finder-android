package com.windhoek.hackathon.schoolfinder;

import com.windhoek.hackathon.schoolfinder.Fragments.ResultListFragment;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;

import java.util.ArrayList;

/**
 * Created by Asus on 05/03/2017.
 */

public class MessageEvent {
    public ArrayList<SchoolObject> schoolObjects;

    public MessageEvent(ArrayList<SchoolObject> schoolObjects) {
        this.schoolObjects = schoolObjects;
    }

    public ArrayList<SchoolObject> getSchoolObjects() {
        return this.schoolObjects;
    }
}
