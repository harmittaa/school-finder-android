package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultHandlerFragment extends Fragment {
    private static final String TAG = "ResultHandlerFragment";
    private View fragmentView;
    private TabHost tabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_result_handler, container, false);
        Log.e(TAG, "onCreateView: fragment switch completed");
        this.tabHost = (TabHost) this.fragmentView.findViewById(R.id.tab_host);

        tabHost.setup();

        TabHost.TabSpec listTab = tabHost.newTabSpec("List");
        listTab.setContent(R.id.list_tab);
        listTab.setIndicator("List tab");
        tabHost.addTab(listTab);

        TabHost.TabSpec mapTab = tabHost.newTabSpec("List");
        mapTab.setContent(R.id.map_tab);
        mapTab.setIndicator("Map tab");
        tabHost.addTab(mapTab);

        return this.fragmentView;

    }
}
