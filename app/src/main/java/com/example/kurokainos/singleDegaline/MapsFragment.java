package com.example.kurokainos.singleDegaline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kurokainos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
private double longt;
private double lat;
private String degPavadinimas;


   public MapsFragment(double longt, double lat, String degPavadinimas){
       this.longt=longt;
       this.lat=lat;
       this.degPavadinimas=degPavadinimas;
   }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {


    //UZDEDAMAS MARKERIS ANT PARASYTOS POZICIJOS

            if(!String.valueOf(lat).equals(null) && !String.valueOf(longt).equals(null)) {
                LatLng vilnius = new LatLng(lat, longt);
                googleMap.addMarker(new MarkerOptions().position(vilnius).title(degPavadinimas));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vilnius, 16f));

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}