package com.windhoek.hackathon.schoolfinder.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.clustering.ClusterManager;
import com.windhoek.hackathon.schoolfinder.MainActivity;
import com.windhoek.hackathon.schoolfinder.R;

import java.util.List;

/**
 * Created by Asus on 04/03/2017.
 */

public class ResultMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMarkerClickListener {
    private static final String TAG = "ResultMapFragment";
    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 111;
    private View fragmentView;
    private Location lastKnownLoc;
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;

    private Location lastLoc;
    private MapFragment mapFragment;
    private VisibleRegion visibleRegion;
    private List<String> submissionMarkerIdList;
    private LocationRequest locationRequest;
    private Location longPressLoc;
    private ImageButton menuButton;
    private ImageButton filtersButon;
    private ImageButton newSubmissionButton;
    private long minDateInMs = 0;
    private int tempY;
    private int tempM;
    private int tempD;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.fragmentView == null) {
            this.fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
        }

        setRetainInstance(true);

        checkPermissions();

        Log.e(TAG, "onCreateView: MAPS " + this.googleMap);

        connectToGoogleApi();
        setupGoogleMap();
        return this.fragmentView;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // PERMISSION_REQUEST_ACCESS_FINE_LOCATION can be any unique int
            ActivityCompat.requestPermissions(getMainActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: Accepted");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    createAlert();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ON RESUME" );
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ON PAUSE" );
    }

    private void createAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
        builder.setMessage("Please activate location")
                .setCancelable(false)
                .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getMainActivity().getBaseContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Nope, quit app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    /*
     * GOOGLE MAPS METHODS BELOW
     */
    private void connectToGoogleApi() {
        this.googleApiClient = new GoogleApiClient.Builder(getMainActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        this.googleApiClient.connect();
    }


    /**
     * Setup method for open street map in this fragment
     * Enables touch controls
     * Sets starting position and zooms in
     */
    private void setupGoogleMap() {
        //init map
        //get current phone position and zoom to location
        LocationManager lm = (LocationManager) getMainActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 21/11/2016 ask for permission
            Log.e(TAG, "setupGoogleMap: ASK FOR PERMISSION");
        } else {
            this.lastKnownLoc = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setMyLocationEnabled(true);
        zoomMap();
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
    }

    /**
     * Zooms the map on the last known location
     */
    private void zoomMap() {
        if (getLastLoc() != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(getLastLoc().getLatitude(), getLastLoc().getLongitude()))      // Sets the center of the map to Mountain View
                    .zoom(17)                  // Sets the tilt of the luke_camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    /**
     * Returns the previously known user location, either the last location or last known location
     *
     * @return Location object, either long press location, last known location or null if no location is available.
     */
    private Location getLastLoc() {
        Location location = new Location("");
        if (this.lastLoc != null) {
            location.setAltitude(this.lastLoc.getAltitude());
            location.setLatitude(this.lastLoc.getLatitude());
            location.setLongitude(this.lastLoc.getLongitude());
            return location;
        } else if (this.lastKnownLoc != null) {
            location.setAltitude(this.lastKnownLoc.getAltitude());
            location.setLatitude(this.lastKnownLoc.getLatitude());
            location.setLongitude(this.lastKnownLoc.getLongitude());
            return location;
        } else {
            return null;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
