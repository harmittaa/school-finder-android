package com.windhoek.hackathon.schoolfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.windhoek.hackathon.schoolfinder.Fragments.ResultListFragment;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.PagerAdapter.PagerAdapter;

import java.util.ArrayList;


// TODO: 04/03/2017 Add cities programatically from the DB entries instead of having a list
// of hardocded cities
// TODO: 04/03/2017 Add back arrow to the result screen
// TODO: 04/03/2017 Show UI tools  

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseReference databaseReference;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ArrayList<SchoolObject> schoolObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData();
        setContentView(R.layout.main_activity);
        fragmentSwitcher(Constants.fragmentTypes.FRAGMENT_SEARCH, null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // STUFFS:
        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Search"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("List"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Map"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this.tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        Log.e(TAG, "onCreateView: fragment switch completed");



    }

    private void fetchData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                schoolObjects = new ArrayList<>();
                SchoolObject schoolObject = dataSnapshot.getValue(SchoolObject.class);
                Log.e(TAG, "onDataChange: TEST TO FETCH DATA" + dataSnapshot.getValue() );
                Log.e(TAG, "onDataChange: count " + dataSnapshot.getChildrenCount() + " has? " + dataSnapshot.hasChildren() );
                for (DataSnapshot schoolsSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot plzWork : schoolsSnapshot.getChildren()) {
                        String name = (String) plzWork.child("name").getValue();
                        String message = (String) plzWork.child("city").getValue();
                        String latitude = (String) plzWork.child("Latitude").getValue();
                        Log.e(TAG, "onDataChange: LATitUDE " + latitude );
                        Log.e(TAG, "onDataChange: name " + name + " message " + message );
                        SchoolObject so = new SchoolObject(
                                (String) plzWork.child("name").getValue(),
                                (String) plzWork.child("Address").getValue(),
                                (String) plzWork.child("Latitude").getValue(),
                                (String) plzWork.child("Longitude").getValue(),
                                (String) plzWork.child("Public").getValue(),
                                (String) plzWork.child("City").getValue(),
                                (String) plzWork.child("Phone").getValue(),
                                (String) plzWork.child("Region").getValue()
                        );
                        schoolObjects.add(so);
                    }
                }
                ResultListFragment resultListFragment = new ResultListFragment();
                resultListFragment.updateDataset();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        databaseReference.addValueEventListener(postListener);
    }

    public ArrayList<SchoolObject> getSchoolObjects() {
        return this.schoolObjects;
    }

    public void fragmentSwitcher(Constants.fragmentTypes fragmentToSwitchTo, Bundle bundleToSend) {
        // initialize the fragment manager and transaction
/*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        String tag = "default";

        // switch case handling the changing of the fragment
        switch (fragmentToSwitchTo) {
            case FRAGMENT_SEARCH:
                fragment = new SearchFragment();
                break;
            case FRAGMENT_RESULT_HANDLER:
                fragment = new ResultHandlerFragment();
                break;
            case FRAGMENT_SCHOOL_PROFILE:
                fragment = new SchoolProfileFragment();
                break;
        }

        // add the extras to the fragment if required
        if (bundleToSend != null && fragment != null)  {
            fragment.setArguments(bundleToSend);
        }
        Log.e(TAG, "fragmentSwitcher: SWITCHING FRAGMENTS");
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
*/
    }
}

