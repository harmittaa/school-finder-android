package com.windhoek.hackathon.schoolfinder.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultMapFragment extends Fragment {
    private static final String TAG = "ResultMapFragment";
    private View fragmentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

        return this.fragmentView;
    }
}
