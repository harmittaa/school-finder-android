package com.windhoek.hackathon.schoolfinder;

/**
 * Created by Asus on 05/03/2017.
 */

public class ContextHolder {
    private MainActivity mainActivity;

    public ContextHolder(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
