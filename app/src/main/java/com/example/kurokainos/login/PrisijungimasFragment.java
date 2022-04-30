package com.example.kurokainos.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.kurokainos.mainActivity.MainActivity;

import java.util.HashMap;
import java.util.Map;


public class PrisijungimasFragment extends Fragment {

private TextView registrationText;
private EditText username;
private EditText password;
private Button loginButton;
    private AlertDialog.Builder builder;

    private static final String loginapi = "https://192.168.0.90/MyApi/loginapi.php";

    public PrisijungimasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_prisijungimas, container, false);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,passwordas;
                user=username.getText().toString();
                passwordas=password.getText().toString();
                if(user.equals("")||passwordas.equals("")){
                    Toast.makeText(getContext(), "Ne visi laukai uzpildyti!", Toast.LENGTH_SHORT).show();
                }else{

                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginapi,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("Sekmingai prisijungta!")){
                                            Toast.makeText(getContext(), "Sekmingai prisijungta!", Toast.LENGTH_SHORT).show();
                                        }else if (response.equals("Tokio vartotojo nera!")){

                                            Toast.makeText(getContext(), "Tokio vartotojo nera!", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                System.out.println(error);
                                error.printStackTrace();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", user);
                                params.put("password", passwordas);
                                return params;
                            }
                        };



                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);

                    }

                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(0, 0);
                }

            }
        });


        registrationText = view.findViewById(R.id.registrationText);

        registrationText.setOnClickListener(view1 -> {
            Fragment registracijaFragment = new RegistracijaFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.container,registracijaFragment).commit();
        });

        return view;

    }
}