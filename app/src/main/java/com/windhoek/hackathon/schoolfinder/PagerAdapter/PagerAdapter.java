package com.windhoek.hackathon.schoolfinder.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.windhoek.hackathon.schoolfinder.Fragments.MapFragment;
import com.windhoek.hackathon.schoolfinder.Fragments.ResultListFragment;
import com.windhoek.hackathon.schoolfinder.Fragments.ResultMapFragment;
import com.windhoek.hackathon.schoolfinder.Fragments.SearchFragment;

/**
 * Created by Asus on 04/03/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs;


    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new SearchFragment();
            case 1:
                return new ResultListFragment();
            case 2:
                return new MapFragment();
            default:
                return null;
        }
    }



    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}
