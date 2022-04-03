package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private TextView atnaujinta;
private Spinner selectCity;
private String Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //PAIMA DABARTINE DATA
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        //UZDEDA DABARTINE DATA TEXTVIEW
        atnaujinta = (TextView)findViewById(R.id.atnaujinta);
        atnaujinta.setText(strDate);


        //uzdedam spinneriui miestus, is kuriu galima rinktis
        selectCity = (Spinner)findViewById(R.id.selectCity);
        String[]Item = new String[]{"Vilnius","Kaunas","Siauliai","Panevezys","Klaipeda"};
        selectCity.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,Item));
        selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Items = selectCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });








    }


}