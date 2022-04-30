package com.example.kurokainos.mainActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.example.kurokainos.adapters.degalinesSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class degaliniuSarasasFragment extends Fragment {
    private Spinner selectCity;
    private ArrayList<Degalines> productList = new ArrayList<>();
    private ListView listview;
    private ArrayList<degalinesSpinner> spinnerList = new ArrayList<>();

    private static final String degaliniulistapi = "https://192.168.0.90/MyApi/Api.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleSSLHandshake();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //PAIMA DABARTINE DATA
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        //UZDEDA DABARTINE DATA TEXTVIEW
        View v = inflater.inflate(R.layout.fragment_degaliniu_sarasas, container, false);
        TextView atnaujinta = v.findViewById(R.id.atnaujinta);
        atnaujinta.setText(strDate);

        listview = v.findViewById(R.id.listView);

        loadProducts();

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
        selectCity = v.findViewById(R.id.selectCity);


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



    private void loadProducts() {
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

                                Degalines degaline = new Degalines(id,miestas,pavadinimas,adresas,benzinoKaina,dyzelioKaina,dujuKaina,latitude,longtitude);


                                productList.add(degaline);
                                System.out.println(miestas+" ");
                                System.out.println(pavadinimas+" ");
                                System.out.println(adresas+" ");
                                System.out.println(benzinoKaina+" ");
                                System.out.println(dyzelioKaina+" ");
                                System.out.println(dujuKaina+" ");
                                System.out.println(latitude+" ");
                                System.out.println(longtitude+" ");

                                degalinesSpinner degalinespinneris = new degalinesSpinner(pavadinimas);
                                spinnerList.add(degalinespinneris);



                                String[] Item = {"All","Kaunas","Vilnius"};
                                selectCity.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, Item));

                               selectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                    }



                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }
                            DegalinesAdaptor degalinesAdaptor = new DegalinesAdaptor(getActivity(), R.layout.listview_row_data,productList);

                            listview.setAdapter(degalinesAdaptor);




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
}



