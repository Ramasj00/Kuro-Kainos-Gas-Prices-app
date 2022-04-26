package com.example.kurokainos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DegalinesInfoActivity extends AppCompatActivity {
    TextView degalinesPavadinimas;
     TextView degalinesAdresas;
    TextView benzinas;
    TextView dyzelis;
    TextView dujos;
    Button commentListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if(findViewById(R.id.mapView)!=null){
        Fragment map = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mapView, map).commit();//}

        setContentView(R.layout.activity_degalines_info);

        degalinesPavadinimas = (TextView) findViewById(R.id.degalinesPavadinimas);
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


        commentListButton = (Button)findViewById(R.id.commentListButton);
        commentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCommentListActivity();
            }

            private void openCommentListActivity() {
                Intent intent = new Intent(getApplicationContext(), CommentListActivity.class);
                startActivity(intent);

                intent.putExtra("degalinesPavadinimas",degalinesPavadinimas.toString());
                intent.putExtra("degalinesAdresas",degalinesAdresas.toString());
            }
        });


    }
}