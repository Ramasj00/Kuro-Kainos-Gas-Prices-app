package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DegalinesInfoActivity extends AppCompatActivity {
TextView degalinesPavadinimas;
TextView degalinesAdresas;
    TextView benzinas;
    TextView dyzelis;
    TextView dujos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degalines_info);

        degalinesPavadinimas = (TextView)findViewById(R.id.degalinesPavadinimas);
        degalinesAdresas = (TextView) findViewById(R.id.degalinesAdresas);
        benzinas = (TextView) findViewById(R.id.benzinas);
        dyzelis = (TextView) findViewById(R.id.dyzelis);
        dujos = (TextView) findViewById(R.id.dujos);



        Intent intent = getIntent();

        degalinesPavadinimas.setText(intent.getStringExtra("degalinesPavadinimas"));
        degalinesAdresas.setText(intent.getStringExtra("degalinesAdresas"));
        benzinas.setText(intent.getStringExtra("benzinas"));
        dyzelis.setText(intent.getStringExtra("dyzelis"));
        dujos.setText(intent.getStringExtra("dujos"));
    }
}