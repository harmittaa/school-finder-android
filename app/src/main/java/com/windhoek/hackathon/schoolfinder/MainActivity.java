package com.windhoek.hackathon.schoolfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.windhoek.hackathon.schoolfinder.Fragments.ResultHandlerFragment;
import com.windhoek.hackathon.schoolfinder.Fragments.SearchFragment;


// TODO: 04/03/2017 Add cities programatically from the DB entries instead of having a list
// of hardocded cities


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fragmentSwitcher(Constants.fragmentTypes.FRAGMENT_SEARCH, null);
    }

    public void fragmentSwitcher(Constants.fragmentTypes fragmentToSwitchTo, Bundle bundleToSend) {
        // initialize the fragment manager and transaction
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
        }

        // add the extras to the fragment if required
        if (bundleToSend != null && fragment != null)  {
            fragment.setArguments(bundleToSend);
        }
        Log.e(TAG, "fragmentSwitcher: SWITCHING FRAGMENTS");
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
    }
}

