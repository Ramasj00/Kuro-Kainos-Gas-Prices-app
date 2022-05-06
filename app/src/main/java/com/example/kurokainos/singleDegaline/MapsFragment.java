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
private final double longt;
private final double lat;
private final String degPavadinimas;

   public MapsFragment(double longt, double lat, String degPavadinimas){
       this.longt=longt;
       this.lat=lat;
       this.degPavadinimas=degPavadinimas;
   }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

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
        return inflater.inflate(R.layout.fragment_zemelapis, container, false);
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