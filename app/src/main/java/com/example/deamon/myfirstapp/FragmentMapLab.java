package com.example.deamon.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMapLab extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private MapView mMapView;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);


        return rootView;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(5));
        mMap.setOnInfoWindowClickListener(this);

        MarkerOptions[] universities = {
                new MarkerOptions().position(new LatLng(38.5493, -121.7671)).title("University of California Davis").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("davis", 100, 100))),
                new MarkerOptions().position(new LatLng(37.8764984, -122.2804342)).title("University of California Berkeley").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("cal", 100, 100))),
                new MarkerOptions().position(new LatLng(36.984, -122.0613)).title("University of California Santa Cruz").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ucsc", 100, 100))),
                new MarkerOptions().position(new LatLng(37.3664, -120.4246)).title("University of California Merced").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("merced", 100, 100))),
                new MarkerOptions().position(new LatLng(34.4075, -119.8449)).title("University of California Santa Barbara").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ucsb", 100, 100))),
                new MarkerOptions().position(new LatLng(34.074949, -118.441318)).title("University of California Los Angeles").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ucla", 100, 100))),
                new MarkerOptions().position(new LatLng(33.636984, -117.835287)).title("University of California Irvine").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("uci", 100, 100))),
                new MarkerOptions().position(new LatLng(34.013873, -117.331040)).title("University of California Riverside").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ucr", 100, 100))),
                new MarkerOptions().position(new LatLng(32.8866612, -117.2413398)).title("University of California San Diego").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ucsd", 100, 100)))
        };

        mMap.moveCamera(CameraUpdateFactory.newLatLng(universities[8].getPosition()));

        for (int a = 0; a < universities.length; a++) {
            mMap.addMarker(universities[a]);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        System.out.println("Marker: " + marker.getTitle());
        System.out.println("Marker: " + marker.getId().substring(1));
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        intent.putExtra("locationName", marker.getTitle());
        startActivity(intent);
    }

    private Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}