package com.example.kurokainos.mainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kurokainos.adapters.Constant;
import com.example.kurokainos.adapters.DegalinesLocation;
import com.example.kurokainos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class sideNavFragment extends Fragment {

    private final ArrayList<DegalinesLocation> productList = new ArrayList<>();
    private GoogleMap mMap;
    private LocationManager locationManager;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            loadLocation();

            mMap = googleMap;

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mMap.setMyLocationEnabled(true);
            Criteria criteria = new Criteria();
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng myPosition = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15f));
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side_nav, container, false);
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
        private void loadLocation() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.LOCATION_API,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray locationList = new JSONArray(response);

                                for (int i = 0; i < locationList.length(); i++) {
                                    JSONObject object = locationList.getJSONObject(i);

                                    String pavadinimas = object.getString("pavadinimas");
                                    String adresas = object.getString("adresas");
                                    String latitude = object.getString("latitude");
                                    String longtitude = object.getString("longtitude");

                                    double lat = Double.parseDouble(latitude);
                                    double longt = Double.parseDouble(longtitude);

                                    DegalinesLocation location = new DegalinesLocation(pavadinimas, adresas, lat, longt);

                                    productList.add(location);

                                    for (int j = 0; j < productList.size(); j++) {
                                        LatLng locationn = new LatLng(productList.get(j).getLatitude(), productList.get(j).getLongtitude());
                                        mMap.addMarker(new MarkerOptions().position(locationn).title(productList.get(j).getPavadinimas()));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

            Volley.newRequestQueue(requireContext()).add(stringRequest);
        }
    }
