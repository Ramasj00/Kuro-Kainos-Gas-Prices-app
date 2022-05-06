package com.example.kurokainos.singleDegaline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.kurokainos.R;
import com.example.kurokainos.adapters.Constant;
import com.example.kurokainos.adapters.DegalinesCommentList;
import com.example.kurokainos.adapters.DegalinesCommentListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CommentListActivity extends AppCompatActivity {
    private TextView degalinesPavadinimas;
    private TextView degalinesAdresas;
    private ListView commentListView;
    private EditText comBenz;
    private EditText comDyz;
    private EditText comDuj;
    private Button siustiBtn;
    private final ArrayList<DegalinesCommentList> CommentList = new ArrayList<>();
    private DegalinesCommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        loadData();
        comBenz=findViewById(R.id.CommentBenzinoKainaEditText);
        comDyz=findViewById(R.id.CommentDyzelinoKainaEditText);
        comDuj=findViewById(R.id.CommentDujuKainaEditText);
        degalinesPavadinimas= findViewById(R.id.degalinesPavadinimas);
        degalinesAdresas= findViewById(R.id.degalinesAdresas);
        siustiBtn = findViewById(R.id.siustiBtn);
        commentListView = findViewById(R.id.commentListView);

        Intent intent = getIntent();

        String degPavadinimas = intent.getStringExtra("degalinesPavadinimas");
        String degAdresas = intent.getStringExtra("degalinesAdresas");

        degalinesPavadinimas.setText(degPavadinimas);
        degalinesAdresas.setText(degAdresas);

        siustiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String benz,dyz,duj;
                benz=comBenz.getText().toString();
                dyz=comDyz.getText().toString();
                duj=comDuj.getText().toString();

                if(benz.equals("")||dyz.equals("")||duj.equals("")||(Double.parseDouble(benz)>2.50||Double.parseDouble(dyz)>2.50||Double.parseDouble(duj)>2.50)){
                    Toast.makeText(getApplicationContext(), "Blogai ivestos kainos!", Toast.LENGTH_SHORT).show();
                }else{
                sendComment();
                }

                }
            });
    }

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, Constant.COMMENT_LIST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("sitoje vietoje "+response);
                            JSONArray commentsList = new JSONArray(response);

                            for(int i =0;i<commentsList.length();i++){
                                JSONObject Object = commentsList.getJSONObject(i);

                                String adresas = Object.getString("adresas");
                                String benzinoKaina = Object.getString("benzinoKaina");
                                String dyzelioKaina = Object.getString("dyzelioKaina");
                                String dujuKaina = Object.getString("dujuKaina");
                                String komentaroData = Object.getString("commentData");

                                DegalinesCommentList degaline = new DegalinesCommentList(adresas,benzinoKaina,dyzelioKaina,dujuKaina,komentaroData);
                                CommentList.add(degaline);

                            }
                            adapter = new DegalinesCommentListAdapter(getApplicationContext(), R.layout.commentlistview_row_data,CommentList);
                            commentListView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.out.println(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String adr = degalinesAdresas.getText().toString();
                params.put("adresas", adr);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void sendComment(){
        Intent intent = getIntent();

        String degAdresas = intent.getStringExtra("degalinesAdresas");
        String adresas,benz,dyz,duj,laikas;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String strDate = formatter.format(date);
        benz=comBenz.getText().toString();
        dyz=comDyz.getText().toString();
        duj=comDuj.getText().toString();
        laikas=strDate;
        adresas=degAdresas;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.SEND_COMMENT_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CommentList.clear();
                        adapter.notifyDataSetChanged();
                        loadData();
                        commentListView.invalidateViews();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("Cia erroras"+error);
                error.printStackTrace();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

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
