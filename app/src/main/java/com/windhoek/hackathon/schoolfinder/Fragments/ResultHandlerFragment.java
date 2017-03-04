package com.windhoek.hackathon.schoolfinder.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TableLayout;

import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.PagerAdapter.PagerAdapter;
import com.windhoek.hackathon.schoolfinder.R;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultHandlerFragment extends Fragment {
    private static final String TAG = "ResultHandlerFragment";
    private View fragmentView;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_result_handler, container, false);
        //this.toolbar = (Toolbar) this.fragmentView.findViewById(R.id.toolbar);
        //getMainActivity().setSupportActionBar(this.toolbar);

        this.tabLayout = (TabLayout) this.fragmentView.findViewById(R.id.tab_layout);
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Result list"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Result map"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) this.fragmentView.findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getMainActivity().getSupportFragmentManager(), this.tabLayout.getTabCount());
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

        return this.fragmentView;

    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
