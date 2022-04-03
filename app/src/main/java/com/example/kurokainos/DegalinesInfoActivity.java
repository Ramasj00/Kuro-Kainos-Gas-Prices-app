package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DegalinesInfoActivity extends AppCompatActivity {
TextView degalinesPavadinimas;
TextView degalinesAdresas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degalines_info);

        degalinesPavadinimas = (TextView)findViewById(R.id.degalinesPavadinimas);
        degalinesAdresas = (TextView) findViewById(R.id.degalinesAdresas);

        Intent intent = getIntent();

        degalinesPavadinimas.setText(intent.getStringExtra("degalinesPavadinimas"));
        degalinesAdresas.setText(intent.getStringExtra("degalinesAdresas"));
    }
}