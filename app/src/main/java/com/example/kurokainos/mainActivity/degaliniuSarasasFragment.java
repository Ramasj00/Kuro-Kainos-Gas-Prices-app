package com.example.kurokainos.mainActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kurokainos.adapters.Constant;
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
    private ArrayList<Degalines> degaliniuList = new ArrayList<>();
    private ListView listview;
    private TextView atnaujinta;
    private Button didejimo;
    private Button mazejimo;
    private DegalinesAdaptor adaptor;
    private RadioGroup kurasRadioGroup;
    private boolean didejimoTvarka;
    private String kuroTipas;
    private String filtruojamasMiestas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleSSLHandshake();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        didejimoTvarka=false;
        kuroTipas="benzinas";
        filtruojamasMiestas="visi";
        View v = inflater.inflate(R.layout.fragment_degaliniu_sarasas, container, false);

        didejimo = v.findViewById(R.id.didejimoButton);
        mazejimo = v.findViewById(R.id.mazejimoButton);
        kurasRadioGroup = v.findViewById(R.id.kurasRadioGroup);
        listview = v.findViewById(R.id.listView);
        mySpinner=v.findViewById(R.id.spinner);
        atnaujinta=v.findViewById(R.id.atnaujinta);

        didejimo.setVisibility(View.GONE);
        mazejimo.setOnClickListener(view -> {
           didejimoTvarka=true;
           loadData();
            mazejimo.setVisibility(View.GONE);
            didejimo.setVisibility(View.VISIBLE);
         });

        didejimo.setOnClickListener(view -> {
            didejimoTvarka=false;
            loadData();
            mazejimo.setVisibility(View.VISIBLE);
            didejimo.setVisibility(View.GONE);
        });
        spinneris();
        radioButtonFiltras();

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), DegalinesInfoActivity.class);
            Degalines list = degaliniuList.get(position);
            intent.putExtra("degalinesPavadinimas", list.getPavadinimas());
            intent.putExtra("degalinesAdresas", list.getAdresas());
            intent.putExtra("benzinas", list.getBenzinoKaina());
            intent.putExtra("dyzelis", list.getDyzelioKaina());
            intent.putExtra("dujos", list.getDujuKaina());
            intent.putExtra("latitude", list.getLatitude());
            intent.putExtra("longtitude", list.getLongtitude());
            startActivity(intent);
        });

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
        degaliniuList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DEGALINIU_LIST_API, response -> {
                    try {
                        JSONArray degalines = new JSONArray(response);
                        for(int i =0;i<degalines.length();i++){
                            JSONObject productObject = degalines.getJSONObject(i);

                            int id = productObject.getInt("id");
                            String miestas = productObject.getString("miestas");
                            String pavadinimas = productObject.getString("pavadinimas");
                            String adresas = productObject.getString("adresas");
                            String benzinoKaina = productObject.getString("benzinoKaina");
                            String dyzelioKaina = productObject.getString("dyzelioKaina");
                            String dujuKaina = productObject.getString("dujuKaina");
                            double latitude = productObject.getDouble("latitude");
                            double longtitude = productObject.getDouble("longtitude");
                            String atnaujinimoData = productObject.getString("ikelimoData");

                            if(filtruojamasMiestas.equals(miestas) || filtruojamasMiestas.equals("visi")) {
                                Degalines degaline = new Degalines(id, miestas, pavadinimas, adresas, benzinoKaina, dyzelioKaina, dujuKaina, latitude, longtitude, atnaujinimoData);

                                if(Float.parseFloat(degaline.getKuroKaina(kuroTipas)) > 0) {
                                    degaliniuList.add(degaline);
                                }
                                atnaujinta.setText(atnaujinimoData);
                            }
                        }
                        Collections.sort(degaliniuList, new Comparator<Degalines>() {
                            @Override
                            public int compare(Degalines degalines, Degalines t1) {
                                float kaina1 = Float.parseFloat(degalines.getKuroKaina(kuroTipas));
                                float kaina2 = Float.parseFloat(t1.getKuroKaina(kuroTipas));
                                if(kaina1<kaina2){
                                    if(didejimoTvarka){
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }else{
                                    if(didejimoTvarka){
                                        return 1;
                                    } else {
                                        return -1;
                                    }
                                }
                            }});

                        adaptor = new DegalinesAdaptor(getContext(), R.layout.listview_row_data,degaliniuList);
                        listview.setAdapter(adaptor);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> System.out.println(error));
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    private void radioButtonFiltras(){
        kurasRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.benzinasRadioBtn:
                    degaliniuList.clear();
                    kuroTipas="benzinas";
                    loadData();
                    break;
                case R.id.dyzelinasRadioBtn:
                    degaliniuList.clear();
                    kuroTipas="dyzelis";
                    loadData();
                    break;
                case R.id.dujosRadioBtn:
                    degaliniuList.clear();
                    kuroTipas="dujos";
                    loadData();
                    break;
            }
        });
    }
 private void spinneris (){
        ArrayList<String> miestai = new ArrayList<>();
        miestai.add("visi");
        miestai.add("Vilnius");
        miestai.add("Kaunas");
        miestai.add("Klaipėda");
        miestai.add("Šiauliai");
        miestai.add("Alytus");
        ArrayAdapter<String> spinerioAdapteris =  new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,miestai);
        mySpinner.setAdapter(spinerioAdapteris);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                degaliniuList.clear();
                String text = mySpinner.getSelectedItem().toString();
                filtruojamasMiestas = text;

                loadData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}