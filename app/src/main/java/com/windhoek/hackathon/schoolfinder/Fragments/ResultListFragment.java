package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultListFragment extends Fragment {
    private static final String TAG = "ResultListFragment";
    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);

        return this.fragmentView;
    }
}
