package com.example.kurokainos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PrisijungimasFragment extends Fragment {

private TextView registrationText;

    public PrisijungimasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_prisijungimas, container, false);

        registrationText = view.findViewById(R.id.registrationText);

        registrationText.setOnClickListener(view1 -> {
            Fragment registracijaFragment = new RegistracijaFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.container,registracijaFragment).commit();
        });

        return view;

    }
}