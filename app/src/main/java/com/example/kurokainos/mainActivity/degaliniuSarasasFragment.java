package com.example.kurokainos.mainActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kurokainos.adapters.Degalines;
import com.example.kurokainos.adapters.DegalinesAdaptor;
import com.example.kurokainos.singleDegaline.DegalinesInfoActivity;
import com.example.kurokainos.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class degaliniuSarasasFragment extends Fragment {
    private Spinner mySpinner;
    private final ArrayList<Degalines> productList = new ArrayList<>();
    private ListView listview;
    private TextView atnaujinta;
    private Button didejimo;
    private Button mazejimo;
    private DegalinesAdaptor adaptor;
    private static final String degaliniulistapi = "https://192.168.0.90/MyApi/Api.php";
    private RadioGroup kurasRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SortByMazejimoByBenzinas();
        handleSSLHandshake();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_degaliniu_sarasas, container, false);

        didejimo = v.findViewById(R.id.didejimoButton);
        mazejimo = v.findViewById(R.id.mazejimoButton);
        didejimo.setVisibility(View.GONE);
        mySpinner=v.findViewById(R.id.spinner);
        listview = v.findViewById(R.id.listView);



        //didejimo.setVisibility(v.VISIBLE);

        mazejimo.setOnClickListener(view -> {
            productList.clear();
            mazejimo.setVisibility(View.GONE);
            didejimo.setVisibility(View.VISIBLE);

            SortByDidejmoByBenzinas();
        });

        didejimo.setOnClickListener(view -> {
            productList.clear();
            mazejimo.setVisibility(View.VISIBLE);
            didejimo.setVisibility(View.GONE);

            SortByMazejimoByBenzinas();
        });


        kurasRadioGroup = v.findViewById(R.id.kurasRadioGroup);

        Sortas();

        loadData();

        atnaujinta=v.findViewById(R.id.atnaujinta);


        listview.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), DegalinesInfoActivity.class);
            Degalines list = productList.get(position);
            intent.putExtra("degalinesPavadinimas", list.getPavadinimas());
            intent.putExtra("degalinesAdresas", list.getAdresas());
            intent.putExtra("benzinas", list.getBenzinoKaina());
            intent.putExtra("dyzelis", list.getDyzelioKaina());
            intent.putExtra("dujos", list.getDujuKaina());
            intent.putExtra("latitude", list.getLatitude());
            intent.putExtra("longtitude", list.getLongtitude());
            startActivity(intent);
        });

        //uzdedam spinneriui miestus, is kuriu galima rinktis


            return v;
        }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    public void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                response -> {
                    try {

                        JSONArray products = new JSONArray(response);

                        for(int i =0;i<products.length();i++){
                            JSONObject productObject = products.getJSONObject(i);

                            int id = productObject.getInt("id");
                            String miestas = productObject.getString("miestas");
                            String pavadinimas = productObject.getString("pavadinimas");
                            String adresas = productObject.getString("adresas");
                            String benzinoKaina = productObject.getString("benzinoKaina");
                            String dyzelioKaina = productObject.getString("dyzelioKaina");
                            String dujuKaina = productObject.getString("dujuKaina");
                            double latitude = productObject.getDouble("latitude");
                            double longtitude = productObject.getDouble("longtitude");
                            String ikelimoData = productObject.getString("ikelimoData");

                            Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                            productList.add(degaline);

                            atnaujinta.setText(ikelimoData);

                        }

                        adaptor = new DegalinesAdaptor(getContext(), R.layout.listview_row_data,productList);

                        listview.setAdapter(adaptor);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> System.out.println(error));

        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void SortByMazejimoByBenzinas() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                response -> {
                    try {

                        JSONArray products = new JSONArray(response);

                        for(int i =0;i<products.length();i++){
                            JSONObject productObject = products.getJSONObject(i);

                            int id = productObject.getInt("id");
                            String miestas = productObject.getString("miestas");
                            String pavadinimas = productObject.getString("pavadinimas");
                            String adresas = productObject.getString("adresas");
                            String benzinoKaina = productObject.getString("benzinoKaina");
                            String dyzelioKaina = productObject.getString("dyzelioKaina");
                            String dujuKaina = productObject.getString("dujuKaina");
                            double latitude = productObject.getDouble("latitude");
                            double longtitude = productObject.getDouble("longtitude");
                            String ikelimoData = productObject.getString("ikelimoData");

                            Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                            productList.add(degaline);

                            atnaujinta.setText(ikelimoData);

                        }

                        Collections.sort(productList, new Comparator<Degalines>() {
                            @Override
                            public int compare(Degalines degalines, Degalines t1) {
                                float benz1 = Float.parseFloat(degalines.getBenzinoKaina());
                                float benz2 = Float.parseFloat(t1.getBenzinoKaina());
                                if(benz1>benz2)     {
                                    return -1;
                                } else{
                                    return 1;
                                }
                            }});

                        adaptor = new DegalinesAdaptor(getContext(), R.layout.listview_row_data,productList);

                        listview.setAdapter(adaptor);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> System.out.println(error));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void SortByMazejimoByDiesel() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                response -> {
                    try {

                        JSONArray products = new JSONArray(response);

                        for(int i =0;i<products.length();i++){
                            JSONObject productObject = products.getJSONObject(i);

                            int id = productObject.getInt("id");
                            String miestas = productObject.getString("miestas");
                            String pavadinimas = productObject.getString("pavadinimas");
                            String adresas = productObject.getString("adresas");
                            String benzinoKaina = productObject.getString("benzinoKaina");
                            String dyzelioKaina = productObject.getString("dyzelioKaina");
                            String dujuKaina = productObject.getString("dujuKaina");
                            double latitude = productObject.getDouble("latitude");
                            double longtitude = productObject.getDouble("longtitude");
                            String ikelimoData = productObject.getString("ikelimoData");

                            Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                            productList.add(degaline);

                            atnaujinta.setText(ikelimoData);

                        }

                        Collections.sort(productList, (degalines, t1) -> {
                            float dyz1 = Float.parseFloat(degalines.getDyzelioKaina());
                            float dyz2 = Float.parseFloat(t1.getDyzelioKaina());
                            if(dyz1>dyz2)     {
                                return -1;
                            } else{
                                return 1;
                            }
                        });

                        adaptor = new DegalinesAdaptor(getActivity(), R.layout.listview_row_data,productList);

                        listview.setAdapter(adaptor);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> System.out.println(error));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void SortByDidejmoByDiesel() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                response -> {
                    try {

                        JSONArray products = new JSONArray(response);

                        for(int i =0;i<products.length();i++){
                            JSONObject productObject = products.getJSONObject(i);

                            int id = productObject.getInt("id");
                            String miestas = productObject.getString("miestas");
                            String pavadinimas = productObject.getString("pavadinimas");
                            String adresas = productObject.getString("adresas");
                            String benzinoKaina = productObject.getString("benzinoKaina");
                            String dyzelioKaina = productObject.getString("dyzelioKaina");
                            String dujuKaina = productObject.getString("dujuKaina");
                            double latitude = productObject.getDouble("latitude");
                            double longtitude = productObject.getDouble("longtitude");
                            String ikelimoData = productObject.getString("ikelimoData");

                            Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                            productList.add(degaline);

                            atnaujinta.setText(ikelimoData);

                        }

                        Collections.sort(productList, (degalines, t1) -> {
                            float dyz1 = Float.parseFloat(degalines.getDyzelioKaina());
                            float dyz2 = Float.parseFloat(t1.getDyzelioKaina());
                            if(dyz1<dyz2)     {
                                return -1;
                            } else{
                                return 1;
                            }
                        });

                        adaptor = new DegalinesAdaptor(getActivity(), R.layout.listview_row_data,productList);

                        listview.setAdapter(adaptor);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> System.out.println(error));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void SortByDidejmoByBenzinas() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray products = new JSONArray(response);

                            for(int i =0;i<products.length();i++){
                                JSONObject productObject = products.getJSONObject(i);

                                int id = productObject.getInt("id");
                                String miestas = productObject.getString("miestas");
                                String pavadinimas = productObject.getString("pavadinimas");
                                String adresas = productObject.getString("adresas");
                                String benzinoKaina = productObject.getString("benzinoKaina");
                                String dyzelioKaina = productObject.getString("dyzelioKaina");
                                String dujuKaina = productObject.getString("dujuKaina");
                                double latitude = productObject.getDouble("latitude");
                                double longtitude = productObject.getDouble("longtitude");
                                String ikelimoData = productObject.getString("ikelimoData");

                                Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                                productList.add(degaline);

                                atnaujinta.setText(ikelimoData);

                            }

                            Collections.sort(productList, new Comparator<Degalines>() {
                                @Override
                                public int compare(Degalines degalines, Degalines t1) {
                                    float benz1 = Float.parseFloat(degalines.getBenzinoKaina());
                                    float benz2 = Float.parseFloat(t1.getBenzinoKaina());
                                    if(benz1<benz2)     {
                                        return -1;
                                    } else{
                                        return 1;
                                    }
                                }});

                            adaptor = new DegalinesAdaptor(getActivity(), R.layout.listview_row_data,productList);

                            listview.setAdapter(adaptor);


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

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void SortByMazejimoByDujos() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray products = new JSONArray(response);

                            for(int i =0;i<products.length();i++){
                                JSONObject productObject = products.getJSONObject(i);

                                int id = productObject.getInt("id");
                                String miestas = productObject.getString("miestas");
                                String pavadinimas = productObject.getString("pavadinimas");
                                String adresas = productObject.getString("adresas");
                                String benzinoKaina = productObject.getString("benzinoKaina");
                                String dyzelioKaina = productObject.getString("dyzelioKaina");
                                String dujuKaina = productObject.getString("dujuKaina");
                                double latitude = productObject.getDouble("latitude");
                                double longtitude = productObject.getDouble("longtitude");
                                String ikelimoData = productObject.getString("ikelimoData");

                                Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                                    productList.add(degaline);

                                    atnaujinta.setText(ikelimoData);

                            }

                            Collections.sort(productList, new Comparator<Degalines>() {
                                @Override
                                public int compare(Degalines degalines, Degalines t1) {
                                    float dyz1 = Float.parseFloat(degalines.getDujuKaina());
                                    float dyz2 = Float.parseFloat(t1.getDujuKaina());
                                    if(dyz1>dyz2)     {
                                        return -1;
                                    } else{
                                        return 1;
                                    }
                                }});

                            adaptor = new DegalinesAdaptor(getContext(), R.layout.listview_row_data,productList);

                            listview.setAdapter(adaptor);


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

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void SortByDidejmoByDujos() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, degaliniulistapi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray products = new JSONArray(response);

                            for(int i =0;i<products.length();i++){
                                JSONObject productObject = products.getJSONObject(i);

                                int id = productObject.getInt("id");
                                String miestas = productObject.getString("miestas");
                                String pavadinimas = productObject.getString("pavadinimas");
                                String adresas = productObject.getString("adresas");
                                String benzinoKaina = productObject.getString("benzinoKaina");
                                String dyzelioKaina = productObject.getString("dyzelioKaina");
                                String dujuKaina = productObject.getString("dujuKaina");
                                double latitude = productObject.getDouble("latitude");
                                double longtitude = productObject.getDouble("longtitude");
                                String ikelimoData = productObject.getString("ikelimoData");

                                Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude,ikelimoData);

                                productList.add(degaline);

                                atnaujinta.setText(ikelimoData);

                            }

                            Collections.sort(productList, new Comparator<Degalines>() {
                                @Override
                                public int compare(Degalines degalines, Degalines t1) {
                                    float dyz1 = Float.parseFloat(degalines.getDujuKaina());
                                    float dyz2 = Float.parseFloat(t1.getDujuKaina());
                                    if(dyz1<dyz2)     {
                                        return -1;
                                    } else{
                                        return 1;
                                    }
                                }});

                            adaptor = new DegalinesAdaptor(getContext(), R.layout.listview_row_data,productList);

                            listview.setAdapter(adaptor);


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

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void Sortas(){
        kurasRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.benzinasRadioBtn:
                    mazejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.GONE);
                        didejimo.setVisibility(View.VISIBLE);

                        SortByDidejmoByBenzinas();
                    });

                    didejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.VISIBLE);
                        didejimo.setVisibility(View.GONE);

                        SortByMazejimoByBenzinas();
                    });
                    break;
                case R.id.dyzelinasRadioBtn:
                    mazejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.GONE);
                        didejimo.setVisibility(View.VISIBLE);

                        SortByDidejmoByDiesel();
                    });

                    didejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.VISIBLE);
                        didejimo.setVisibility(View.GONE);

                        SortByMazejimoByDiesel();
                    });
                    break;
                case R.id.dujosRadioBtn:
                    mazejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.GONE);
                        didejimo.setVisibility(View.VISIBLE);

                        SortByDidejmoByDujos();
                    });

                    didejimo.setOnClickListener(view -> {
                        productList.clear();
                        mazejimo.setVisibility(View.VISIBLE);
                        didejimo.setVisibility(View.GONE);

                        SortByMazejimoByDujos();
                    });
                    break;


            }

        });

    }

}