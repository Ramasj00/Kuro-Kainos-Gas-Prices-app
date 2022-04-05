package com.example.kurokainos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class zemelapisFragment extends Fragment {


    public zemelapisFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = new degaliniuSarasasFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, fragment).commit();

    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_zemelapis, container, false);
    }
}