package com.example.kurokainos.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kurokainos.R;
import com.example.kurokainos.login.loginActivity;
import com.google.android.material.navigation.NavigationView;




public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // PRIDEDAM SIDE NAV
            drawerLayout = findViewById(R.id.drawerLayout);

            findViewById(R.id.imageMenu).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

            loginBtn = findViewById(R.id.loginBtn);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLoginActivity();

                }
            });

            //side nav mygtuku prrogramavimas
            NavigationView navigationView = findViewById(R.id.navigationView);

            NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
            NavigationUI.setupWithNavController(navigationView, navController);

        }

    public void openLoginActivity() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }


}