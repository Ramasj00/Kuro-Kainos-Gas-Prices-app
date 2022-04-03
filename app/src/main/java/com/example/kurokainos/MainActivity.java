package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
private TextView atnaujinta;
private Spinner selectCity;
private String Items;
private ListView listView;
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


        //IDEDAM LSITVIEW
        listView=(ListView) findViewById(R.id.listView);
        ArrayList<Degalines>arrayList = new ArrayList<>();

        arrayList.add(new Degalines("Viada","Gelezinio Vilko g. 25","1.57","2.57","1.38"));
        arrayList.add(new Degalines("Circle K","Gelezasdasd","1.57","2.57","1.38"));
        arrayList.add(new Degalines("Emsi","Gel12321321321","1.57","2.57","1.38"));
        arrayList.add(new Degalines("Baltic Petrolium","Gteststestests","1.57","2.57","1.38"));
        arrayList.add(new Degalines("test","123456789g","1.57","2.57","1.38"));
        arrayList.add(new Degalines("tsetsts","Gzzzzzzz","1.57","2.57","1.38"));

        DegalinesAdaptor degalinesAdaptor = new DegalinesAdaptor(this,R.layout.listview_row_data,arrayList);

        listView.setAdapter(degalinesAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DegalinesInfoActivity.class);
                Degalines list = arrayList.get(position);
               intent.putExtra("degalinesPavadinimas",list.getPavadinimas());
               intent.putExtra("degalinesAdresas",list.getAdresas());
                intent.putExtra("benzinas",list.getBenzinoKaina());
                intent.putExtra("dyzelis",list.getDyzelioKaina());
                intent.putExtra("dujos",list.getDujuKaina());
                startActivity(intent);

            }
        });
    }
}