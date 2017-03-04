package com.windhoek.hackathon.schoolfinder.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ResultListFragment";
    private View fragmentView;
    private ListView resultListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);

        this.resultListView = (ListView) this.fragmentView.findViewById(R.id.resultListView);

        setupListView();
        return this.fragmentView;
    }

    private void setupListView() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getMainActivity(), R.layout.fragment_result_list_item);
        this.resultListView.setAdapter(arrayAdapter);
        this.resultListView.setOnItemClickListener(this);
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "onItemClick: item was clicked" );
    }


}
