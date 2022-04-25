package com.example.kurokainos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RegistracijaFragment extends Fragment {

private TextView loginButtonText;

    public RegistracijaFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_registracija, container, false);

        loginButtonText = view.findViewById(R.id.loginButtonText);

        loginButtonText.setOnClickListener(view1 -> {
            Fragment PrisijungimasFragment = new PrisijungimasFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.container,PrisijungimasFragment).commit();
        });

        return view;
    }
}