package com.windhoek.hackathon.schoolfinder.Fragments;

import android.util.Log;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.windhoek.hackathon.schoolfinder.DataHandlerSingleton;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.MessageEvent;
import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;
import com.windhoek.hackathon.schoolfinder.Observer;
import com.windhoek.hackathon.schoolfinder.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


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
        Log.e(TAG, "MapFragment: MAP FRAGMENT CONSTRUCTOR");
        dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
      //  addMarkersToMap();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //addMarkersToMap(event.getSchoolObjects());
        Log.e(TAG, "onMessageEvent: onMEssageEvent RECEIVED");
        addMarkersToMap();
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        addMarkersToMap();
        if (dataHandlerSingleton == null) {
            dataHandlerSingleton = DataHandlerSingleton.getDataHandlerSingleton();
        }
        //EventBus.getDefault().register(this);
        // getMainActivity().registerObservers(this);
        Log.e(TAG, "MapFragment: MAP FRAGMENT ON RESUME");
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
        addMarkersToMap();
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
        //addMarkersToMap(event.getSchoolObjects());

    }

    // private void addMarkersToMap(ArrayList<SchoolObject> schoolObjects) {
    private void addMarkersToMap() {
        // Log.e(TAG, "addMarkersToMap: ADDING MARKERS TO MAP " + schoolObjects.size() );
        Log.e(TAG, "addMarkersToMap: ADDING MARKERS TO MAP " + dataHandlerSingleton.getSchoolObjects().size());

        if (dataHandlerSingleton.getSchoolObjects().size() > 0 && mMap != null) {

            //     for (SchoolObject so : schoolObjects) {
            for (SchoolObject so : dataHandlerSingleton.getSchoolObjects()) {
                mMap.addMarker(new MarkerOptions()
                                .position(so.getLatLng())
                                .title(so.getName())
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                );
                Log.e(TAG, "addMarkersToMap: Marker added to map");
            }

        }
    }
}
