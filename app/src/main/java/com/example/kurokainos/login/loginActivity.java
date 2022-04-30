package com.example.kurokainos.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.kurokainos.R;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prisijungimasFragment = new PrisijungimasFragment();
        fragmentTransaction.replace(R.id.container,prisijungimasFragment).commit();


    }
}