package com.example.kurokainos.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;


public class RegistracijaFragment extends Fragment {

    private TextView loginButtonText;

    private EditText username;
    private EditText password1;
    private EditText password2;
    private Button registerButton;
    private AlertDialog.Builder builder;

    private static final String registerapi = "https://192.168.0.90/MyApi/registerapi.php";
    public RegistracijaFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_registracija, container, false);



        username = view.findViewById(R.id.username);
        password1 = view.findViewById(R.id.password1);
        password2 = view.findViewById(R.id.password2);
        builder = new AlertDialog.Builder(getContext());




        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user,passwordas1,passwordas2;

                user=username.getText().toString();
                passwordas1=password1.getText().toString();
                passwordas2=password2.getText().toString();



                if(user.equals("")||passwordas1.equals("")||passwordas2.equals(""))
                {
                    Toast.makeText(getContext(), "Ne visi laukai uzpildyti!", Toast.LENGTH_SHORT).show();
                } else if (!passwordas1.equals(passwordas2)){
                    Toast.makeText(getContext(), "Slaptazodziai nesutampa!", Toast.LENGTH_SHORT).show();
                }else {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, registerapi,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    builder.setTitle("Server response");
                                    builder.setMessage("Response:" + response);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            username.setText("");
                                            password1.setText("");
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Kazkas blogai!", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", user);
                            params.put("password", passwordas1);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);

                    Fragment PrisijungimasFragment = new PrisijungimasFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.container,PrisijungimasFragment).commit();
                }

            }
        });





        loginButtonText = view.findViewById(R.id.loginButtonText);

        loginButtonText.setOnClickListener(view1 -> {
            Fragment PrisijungimasFragment = new PrisijungimasFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.container,PrisijungimasFragment).commit();
        });

        return view;
    }


}