package com.example.kurokainos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kurokainos.adapters.Degalines;
import com.example.kurokainos.adapters.DegalinesAdaptor;
import com.example.kurokainos.adapters.DegalinesCommentList;
import com.example.kurokainos.adapters.DegalinesCommentListAdapter;
import com.example.kurokainos.adapters.degalinesSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentListActivity extends AppCompatActivity {
 private TextView degalinesPavadinimas;
 private TextView degalinesAdresas;
 private ListView commentListView;
private EditText comBenz;
    private EditText comDyz;
    private EditText comDuj;
    private Button siustiBtn;
    private ArrayList<DegalinesCommentList> productList = new ArrayList<>();
    private static final String sendcommentapi = "https://192.168.0.90/MyApi/sendcommentapi.php";
    private static final String commentList =  "https://192.168.0.90/MyApi/commentlistapi.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);


        comBenz=findViewById(R.id.CommentBenzinoKainaEditText);
        comDyz=findViewById(R.id.CommentDyzelinoKainaEditText);
        comDuj=findViewById(R.id.CommentDujuKainaEditText);
        degalinesPavadinimas= findViewById(R.id.degalinesPavadinimas);
        degalinesAdresas= findViewById(R.id.degalinesAdresas);
        siustiBtn = findViewById(R.id.siustiBtn);

        Intent intent = getIntent();


        String degPavadinimas = intent.getStringExtra("degalinesPavadinimas");
        String degAdresas = intent.getStringExtra("degalinesAdresas");

        degalinesPavadinimas.setText(degPavadinimas);
        degalinesAdresas.setText(degAdresas);

        commentListView = findViewById(R.id.commentListView);
        loadData();



        siustiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String adresas,benz,dyz,duj,laikas;
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = formatter.format(date);
                benz=comBenz.getText().toString();
                dyz=comDyz.getText().toString();
                duj=comDuj.getText().toString();
                laikas=strDate;
                adresas=degAdresas;


                if(benz.equals("")||dyz.equals("")||duj.equals("")){
                    Toast.makeText(getApplicationContext(), "Prasau uzpildyti visas kainas!", Toast.LENGTH_SHORT).show();
                }
                if(benz.equals("0")&&dyz.equals("0")&&duj.equals("0")){
                    Toast.makeText(getApplicationContext(), "Prasau parasyti normalias kainas!", Toast.LENGTH_SHORT).show();
                } else{

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sendcommentapi,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(error);
                            error.printStackTrace();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("adresas",adresas);
                            params.put("benzinoKaina", benz);
                            params.put("dyzelioKaina", dyz);
                            params.put("dujuKaina", duj);
                            params.put("commentData",laikas);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(), "Komentaras pridetas!", Toast.LENGTH_SHORT).show();
                }

                }
            });


    }

    private void sendComment(){

    }


    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, commentList,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray products = new JSONArray(response);

                            for(int i =0;i<products.length();i++){
                                JSONObject productObject = products.getJSONObject(i);

                                String adresas = productObject.getString("adresas");
                                String benzinoKaina = productObject.getString("benzinoKaina");
                                String dyzelioKaina = productObject.getString("dyzelioKaina");
                                String dujuKaina = productObject.getString("dujuKaina");
                                String komentaroData = productObject.getString("commentData");

                                DegalinesCommentList degaline = new DegalinesCommentList(adresas,benzinoKaina,dyzelioKaina,dujuKaina,komentaroData);
                                productList.add(degaline);

                                System.out.println(adresas+" ");
                                System.out.println(benzinoKaina+" ");
                                System.out.println(dyzelioKaina+" ");
                                System.out.println(dujuKaina+" ");
                                System.out.println(komentaroData+" ");

                            }
                            DegalinesCommentListAdapter degalinesAdaptor = new DegalinesCommentListAdapter(getApplicationContext(), R.layout.commentlistview_row_data,productList);

                            commentListView.setAdapter(degalinesAdaptor);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }









}










