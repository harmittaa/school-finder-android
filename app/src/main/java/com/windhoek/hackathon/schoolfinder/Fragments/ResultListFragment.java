package com.windhoek.hackathon.schoolfinder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.windhoek.hackathon.schoolfinder.DataHandlerSingleton;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.Observer;
import com.windhoek.hackathon.schoolfinder.R;

import java.util.ArrayList;

import static com.windhoek.hackathon.schoolfinder.Constants.fragmentTypes.FRAGMENT_SCHOOL_PROFILE;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultListFragment extends Fragment implements AdapterView.OnItemClickListener, Observer {
    private static final String TAG = "ResultListFragment";
    private View fragmentView;
    private ListView resultListView;
    private View rootView;
    private ImageView isAvailableImageView;
    private SchoolAdapter adapter;
    private DataHandlerSingleton dataHandlerSingleton;
    private ArrayList<SchoolObject> schoolObjects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragmentView == null) {
            // Inflate the layout for this fragment
            fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);
            // Find and setup subviews
            dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
            schoolObjects = new ArrayList<>();
            this.resultListView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
            setupListView();
            setRetainInstance(true);
        }
        getMainActivity().registerObservers(this);
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
            ((ViewGroup) fragmentView.getParent()).removeView(fragmentView);
        }
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void setupListView() {
        adapter = new SchoolAdapter(getMainActivity(), schoolObjects);
        // adapter = new SchoolAdapter(getMainActivity(), new ArrayList<SchoolObject>());
        // adapter = new SchoolAdapter(getMainActivity(), getMainActivity().getSchoolObjects());
        ListView listView = (ListView) this.fragmentView.findViewById(R.id.result_list_view);
        listView.setAdapter(adapter);
        Log.e(TAG, "setupListView: Added object");
    }

    public void updateDataset() {
//        adapter.addAll(getMainActivity().getSchoolObjects());
        //adapter.notifyDataSetChanged();
    }


    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "onItemClick: item was clicked");
    }

    @Override
    public void receiveUpdate() {
        schoolObjects = dataHandlerSingleton.getSchoolObjects();
        Log.e(TAG, "receiveUpdate: UPDATE RECEIVED SIZE " + schoolObjects.size());
        adapter.clear();
        adapter.addAll(schoolObjects);
        //adapter.notifyDataSetChanged();
    }

    public class SchoolAdapter extends ArrayAdapter<SchoolObject> {
        public SchoolAdapter(Context context, ArrayList<SchoolObject> schools) {
            super(context, 0, schools);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
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
            isAvailableImageView = (ImageView) convertView.findViewById(R.id.available_image_view);
            TextView schoolName = (TextView) convertView.findViewById(R.id.list_item_school_name);
            TextView schoolInfo1 = (TextView) convertView.findViewById(R.id.list_item_school_info1);
            TextView schoolInfo2 = (TextView) convertView.findViewById(R.id.list_item_school_info2);
            // Populate the data into the template view using the data object
            schoolName.setText(schoolObject.getName());
            schoolInfo1.setText(schoolObject.getAddress());
            schoolInfo2.setText(schoolObject.getPhoneNumber());
            // Return the completed view to render on screen

            if (!schoolObject.hasOpenPositions()) {
                isAvailableImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_block_black_24dp));
            } else {
                isAvailableImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "onClick: item clicked: " + getItem(position).getName());
                    Bundle bundle = new Bundle();
                    bundle.putString("name", getItem(position).getName());
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getItem(position).getPhoneNumber()));
                    getMainActivity().startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
