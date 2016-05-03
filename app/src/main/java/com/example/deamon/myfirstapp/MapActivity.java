package com.example.deamon.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MarkerOptions[] universities = {
            new MarkerOptions() .position(new LatLng(38.5493, -121.7671)) .title("UCDavis"),
            new MarkerOptions() .position(new LatLng(37.8764984, -122.2804342)) .title("UCBerkeley"),
            new MarkerOptions() .position(new LatLng(36.984, -122.0613)) .title("UCSantaCruz"),
            new MarkerOptions() .position(new LatLng(37.3664, -120.4246)) .title("UCMerced"),
            new MarkerOptions() .position(new LatLng(34.4075, -119.8449)) .title("UCSB"),
            new MarkerOptions() .position(new LatLng(34.074949, -118.441318)) .title("UCLA"),
            new MarkerOptions() .position(new LatLng(33.636984, -117.835287)) .title("UCIrvine"),
            new MarkerOptions() .position(new LatLng(34.013873, -117.331040)) .title("UCR"),
            new MarkerOptions() .position(new LatLng(32.8866612, -117.2413398)) .title("UCSD")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng UCSD = new LatLng(32.8866612, -117.2413398);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UCSD));


       for(MarkerOptions marker : universities){
           mMap.addMarker(marker);
       }

    }
}