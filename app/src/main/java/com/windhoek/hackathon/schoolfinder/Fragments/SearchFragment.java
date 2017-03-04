package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class SearchFragment extends Fragment {
    private View fragmentView;
    private static final String TAG = "SearchFragment";
    private Spinner citiesSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        Log.e(TAG, "onCreateView: fragment switch completed");

        this.citiesSpinner = (Spinner) this.fragmentView.findViewById(R.id.city_spinner);
        
        populateSpinner();
        
        return this.fragmentView;
    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.citiesSpinner.setAdapter(adapter);
    }
}
