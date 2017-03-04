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
import android.widget.TextView;

import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.R;

import java.util.ArrayList;

import static com.windhoek.hackathon.schoolfinder.Constants.fragmentTypes.FRAGMENT_SCHOOL_PROFILE;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ResultListFragment";
    private View fragmentView;
    private ListView resultListView;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragmentView == null) {
            // Inflate the layout for this fragment
            fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);
            // Find and setup subviews
            this.resultListView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
            setupListView();
            setRetainInstance(true);

        } else {

            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // in onDestroyView() (it will be added back).
        }
        this.resultListView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
        setupListView();
        setRetainInstance(true);
        Log.e(TAG, "onCreateView: ON CREATE CALLED");
        return this.fragmentView;


 /*       this.fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);
        this.resultListView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
        setupListView();
        setRetainInstance(true);
        return this.fragmentView;*/
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: ON RESUME CALLED");
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ON STOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
        if (this.fragmentView.getParent() != null) {
            ((ViewGroup)fragmentView.getParent()).removeView(fragmentView);
        }
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void setupListView() {
        ArrayList<SchoolObject> arrayOfUsers = new ArrayList<SchoolObject>();
        SchoolAdapter adapter = new SchoolAdapter(getMainActivity(), arrayOfUsers);
        ListView listView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
        listView.setAdapter(adapter);

        SchoolObject schoolObject = new SchoolObject("test_name", "test_address", 10.0, 20.0);
        adapter.add(schoolObject);
        Log.e(TAG, "setupListView: Added object");
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "onItemClick: item was clicked");
    }

    public class SchoolAdapter extends ArrayAdapter<SchoolObject> {
        public SchoolAdapter(Context context, ArrayList<SchoolObject> schools) {
            super(context, 0, schools);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            SchoolObject schoolObject = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_result_list_item, parent, false);
            }
            // Lookup view for data population
            TextView schoolName = (TextView) convertView.findViewById(R.id.list_item_school_name);
            TextView schoolInfo1 = (TextView) convertView.findViewById(R.id.list_item_school_info1);
            TextView schoolInfo2 = (TextView) convertView.findViewById(R.id.list_item_school_info2);
            // Populate the data into the template view using the data object
            schoolName.setText(schoolObject.getName());
            schoolInfo1.setText(schoolObject.getAddress());
            schoolInfo2.setText("Example text here");
            // Return the completed view to render on screen

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "onClick: item clicked: " + getItem(position).getName());
                    Bundle bundle = new Bundle();
                    bundle.putString("name", getItem(position).getName());
                    getMainActivity().fragmentSwitcher(FRAGMENT_SCHOOL_PROFILE, bundle);
                }
            });

            return convertView;
        }
    }
}
