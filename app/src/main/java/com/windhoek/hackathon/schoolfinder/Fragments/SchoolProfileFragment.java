package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class SchoolProfileFragment extends Fragment {
    private View fragmentView;
    private TextView schoolNameTextView;
    private static final String TAG = "SchoolProfileFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_school_profile, container, false);
        this.schoolNameTextView = (TextView) this.fragmentView.findViewById(R.id.school_name_text);
        fetchExtrasFromBundle();
        return this.fragmentView;
    }

    private void fetchExtrasFromBundle() {
        Bundle bundle = getArguments();  // getMainActivity().getIntent().getExtras();
        if (bundle != null) {
            this.schoolNameTextView.setText(bundle.getString("name"));
        }

    }
}
