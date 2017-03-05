package com.windhoek.hackathon.schoolfinder.Fragments;

import android.util.Log;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.windhoek.hackathon.schoolfinder.DataHandlerSingleton;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.Observer;
import com.windhoek.hackathon.schoolfinder.R;


/**
 * Created by Asus on 04/03/2017.
 */

public class MapFragment extends SupportMapFragment implements
        OnMapReadyCallback, Observer {
    private DataHandlerSingleton dataHandlerSingleton;
    private final static String TAG = "MapFragment";
    private final LatLng WINDHOEK = new LatLng(-22.566, 17.074);
    private final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;

    public MapFragment() {
        getMainActivity().registerObservers(this);
        dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dataHandlerSingleton == null) {
            dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
        }
        getMainActivity().registerObservers(this);
        Log.d("MyMap", "onResume");
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "onMapReady");
        mMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {

        //  mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WINDHOEK, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getContext();
    }

    @Override
    public void receiveUpdate() {
        addMarkersToMap();

    }

    private void addMarkersToMap() {
        for (SchoolObject so : dataHandlerSingleton.getSchoolObjects()) {
            mMap.addMarker(new MarkerOptions()
                    .position(so.getLatLng())
                    .title(so.getName())
            );
        }
    }
}
