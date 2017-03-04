package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.windhoek.hackathon.schoolfinder.Constants;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private View fragmentView;
    private static final String TAG = "SearchFragment";
    private Spinner citiesSpinner;
    private Button searchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        Log.e(TAG, "onCreateView: fragment switch completed");

        this.citiesSpinner = (Spinner) this.fragmentView.findViewById(R.id.city_spinner);
        this.searchButton = (Button) this.fragmentView.findViewById(R.id.search_button);
        this.searchButton.setOnClickListener(this);

        populateSpinner();

        return this.fragmentView;
    }

    /**
     * Populates the city spinner with the values from the cities_array
     */
    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.citiesSpinner.setAdapter(adapter);
    }

    /**
     * Handles the onClick events of the views
     * @param view The clicked view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                Log.e(TAG, "onCreateView: search button clicked");
                getMainActivity().fragmentSwitcher(Constants.fragmentTypes.FRAGMENT_RESULT_HANDLER, null);

        }
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
