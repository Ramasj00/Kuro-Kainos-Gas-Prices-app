package com.example.kurokainos.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;

import com.example.kurokainos.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            drawerLayout = findViewById(R.id.drawerLayout);
            NavigationView navigationView = findViewById(R.id.navigationView);

            findViewById(R.id.imageMenu).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

            //side nav mygtuku programavimas
            NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
            NavigationUI.setupWithNavController(navigationView, navController);

        }
}