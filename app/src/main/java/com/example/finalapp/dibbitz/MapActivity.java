package com.example.finalapp.dibbitz;


import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalapp.dibbitz.model.Dibbit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import java.util.List;

/**
 * A fragment that launches google map view
 */
public class MapActivity extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;
    private List<Dibbit> mapDibbits;
    private DibbitLab dibbitLab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.activity_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();


        String location ="";
        List<Pair<String, String>> locations = dibbitLab.get(getContext()).getLocations();
        if (locations.size()>0) {
            new GeocoderTask().addMarkers(locations);
        }
            // Perform any camera updates here
            return v;
        }

    // An Geocoder class for accessing the GeoCoding Web Service
    private class GeocoderTask {

        private void addMarkers(List<Pair<String, String>> locationName) {
            double[] latLong  = new double[2];
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
            List<Address> address = null;
            String location;
            for (int i = 0; i < locationName.size(); i++) {
                try {
                     location = locationName.get(i).second;
                    // Getting a maximum of 1 Address that matches the input text
                    address = geocoder.getFromLocationName(location, 1);
                    System.out.println(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ( address.size() > 0) {
                    latLng = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                    latLong[0] = latLong[0]+address.get(0).getLatitude();
                    latLong[1] = latLong[1]+address.get(0).getLongitude();
                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(locationName.get(i).first);
                    markerOptions.snippet(locationName.get(i).second);
//                 Setting marker icon
                    markerOptions.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));


                    googleMap.addMarker(markerOptions);

                }


            }
            latLng = new LatLng(latLong[0]/locationName.size(), latLong[1]/locationName.size());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }

    }





    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}