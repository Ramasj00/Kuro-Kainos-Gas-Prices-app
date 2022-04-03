package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


//DEGALINES KLASE - JEIGU PAVYKS - REIKES SUKURTI ATSKIRAME FAILE SITA KLASE
/*class degalines{
    String Pavadinimas;
    String Adresas;

    public degalines(String pavadinimas, String adresas){
        Pavadinimas = pavadinimas;
        adresas = adresas;
    }

   public String getPavadinimas(){
        return Pavadinimas;
    }

    public void setPavadinimas(String pavadinimas){
        Pavadinimas = pavadinimas;
    }

    public String getAdresas(){
        return Adresas;
    }

    public void setgetAdresas(String adresas){
        Adresas = adresas;
    }

}
*/



public class MainActivity extends AppCompatActivity {
private TextView atnaujinta;
private Spinner selectCity;
private String Items;
private ListView listView;
    String[]degalinesPavadinimai = {"Viada","CircleK","Emsi","Baltic Petrolium"};
    String[]degalinesAdresai = {"Kazkokia g.1","Kazkokia g.3","Kazkokia g.6","Kazkokia g.11",};
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

        arrayList.add(new Degalines("Viada","Gelezinio Vilko g. 25"));
        arrayList.add(new Degalines("Circle K","Gelezasdasd"));
        arrayList.add(new Degalines("Emsi","Gel12321321321"));
        arrayList.add(new Degalines("Baltic Petrolium","Gteststestests"));
        arrayList.add(new Degalines("test","123456789g"));
        arrayList.add(new Degalines("tsetsts","Gzzzzzzz"));

        DegalinesAdaptor degalinesAdaptor = new DegalinesAdaptor(this,R.layout.listview_row_data,arrayList);

        listView.setAdapter(degalinesAdaptor);
        //CustomAdapter customAdapter = new CustomAdapter();

        //listView.setAdapter(customAdapter);
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DegalinesInfoActivity.class);
                intent.putExtra("degalinesPavadinimas",degalinesPavadinimai[i]);
                intent.putExtra("degalinesAdresas",degalinesAdresai[i]);
                startActivity(intent);

            }
        });

*/

    }
/*
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.listview_row_data,null);

            TextView degalinesPavadinimas = view1.findViewById(R.id.degalinesPavadinimas);
            TextView degalinesAdresas = view1.findViewById(R.id.degalinesAdresas);

            degalinesPavadinimas.setText(degalinesPavadinimai[i]);
            degalinesAdresas.setText(degalinesAdresai[i]);

            return view1;
        }
    }*/
}