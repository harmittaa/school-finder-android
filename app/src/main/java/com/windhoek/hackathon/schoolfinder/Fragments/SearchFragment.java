package com.windhoek.hackathon.schoolfinder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.windhoek.hackathon.schoolfinder.DataFetchedEvent;
import com.windhoek.hackathon.schoolfinder.DataHandlerSingleton;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.MessageEvent;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private View fragmentView;
    private static final String TAG = "SearchFragment";
    private Spinner citiesSpinner;
    private Button searchButton;
    private Switch showPublic;
    private Switch showPrivate;
    private EditText schoolName;
    private Switch hasOpenSpacesSwitch;
    private DataHandlerSingleton dataHandlerSingleton;
    private ArrayList<SchoolObject> schoolObjects;
    private ArrayList<SchoolObject> originalObjects;
    private ArrayList<SchoolObject> newObjects;
    private ProgressBar progressBar;
    private boolean dataLoaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        Log.e(TAG, "onCreateView: fragment switch completed");
        dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        this.progressBar = (ProgressBar) this.fragmentView.findViewById(R.id.progress_bar);
        if (dataHandlerSingleton.isDataLoaded()) {
            this.progressBar.setVisibility(View.GONE);
        }
        this.schoolName = (EditText) this.fragmentView.findViewById(R.id.school_name);
        this.citiesSpinner = (Spinner) this.fragmentView.findViewById(R.id.city_spinner);
        this.searchButton = (Button) this.fragmentView.findViewById(R.id.search_button);
        this.showPublic = (Switch) this.fragmentView.findViewById(R.id.public_only_switch);
        this.showPrivate = (Switch) this.fragmentView.findViewById(R.id.private_only_switch);
        this.hasOpenSpacesSwitch = (Switch) this.fragmentView.findViewById(R.id.has_open_spaces_switch);

        this.showPublic.setChecked(true);
        this.showPrivate.setChecked(true);

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
     *
     * @param view The clicked view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                Log.e(TAG, "onCreateView: search button clicked");
                checkFilters();

        }
    }

    @Subscribe
    public void onMessageEvent(DataFetchedEvent event) {
        Log.e(TAG, "onMessageEvent: onMEssageEvent RECEIVED");
        String amount = "Filter " + Integer.toString(dataHandlerSingleton.getSchoolObjects().size());
        searchButton.setText(amount);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (dataHandlerSingleton.isDataLoaded()) {
            this.progressBar.setVisibility(View.GONE);
        } else {
            this.progressBar.setVisibility(View.GONE);
        }

        dataHandlerSingleton.setDataLoaded(true);

        Log.e(TAG, "onMessageEvent: onMEssageEvent RECEIVED");
        String amount = "Filter " + Integer.toString(dataHandlerSingleton.getOriginalSchoolObjects().size());
        searchButton.setText(amount);
    }


    private void checkFilters() {
        Log.e(TAG, "checkFilters: " + this.showPublic.isChecked());
        schoolObjects = dataHandlerSingleton.getOriginalSchoolObjects();
        newObjects = new ArrayList<>();
        String userInput = schoolName.getText().toString();

        ArrayList<SchoolObject> tempArrayList = new ArrayList<>();


        if (schoolObjects.size() > 0) {

            if (!userInput.isEmpty()) {
                for (SchoolObject so : schoolObjects) {
                    Log.e(TAG, "checkFilters: " + so.getName());
                    if (so.getName().toLowerCase().contains(userInput.toLowerCase())) {
                        tempArrayList.add(so);
                    }
                }
                schoolObjects = tempArrayList;
            }


            if (!this.showPublic.isChecked()) {
                for (SchoolObject so : schoolObjects) {
                    if (!so.isPublic()) {
                        newObjects.add(so);
                    }
                }
            } else if (!this.showPrivate.isChecked()) {
                for (SchoolObject so : schoolObjects) {
                    if (so.isPublic()) {
                        newObjects.add(so);
                    }
                }
            } else if (this.showPrivate.isChecked() && this.showPublic.isChecked()) {
                newObjects = schoolObjects;
            }
            ArrayList<SchoolObject> veryTempArray = new ArrayList<>();
            if (hasOpenSpacesSwitch.isChecked()) {
                for (SchoolObject so : newObjects) {
                    if (so.hasOpenPositions()) {
                        veryTempArray.add(so);
                    }
                }
                newObjects = veryTempArray;
            }


            String cityName = citiesSpinner.getSelectedItem().toString();
            ArrayList<SchoolObject> tempArray = new ArrayList<>();
            if (!cityName.equals("None")) {
                for (SchoolObject o : newObjects) {
                    String schoolsCityName = o.getCity();
                    Log.e(TAG, "checkFilters: school's city nae " + o.getCity());
                    if (schoolsCityName.equals(cityName)) {
                        tempArray.add(o);
                        Log.e(TAG, "checkFilters: REMOVED SCHOOL " + o.getName() + " from city " + o.getCity());
                    }
                }
                newObjects = tempArray;
            }
        }

        dataHandlerSingleton.setSchoolObjects(newObjects);
        String amount = "Filter " + Integer.toString(newObjects.size());
        EventBus.getDefault().post(new DataFetchedEvent("update"));
        EventBus.getDefault().post(new MessageEvent(this.newObjects));
        getMainActivity().notifyObservers();
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
