package com.example.anjanibajaj.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjanibajaj.shophere.Utils.Constants;
import com.example.anjanibajaj.shophere.Utils.VolleyNetwork;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    EditText email, password;
    Button loginButton, registerButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.editText);
        password = view.findViewById(R.id.editText2);
        loginButton =  view.findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFunction();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private void loginFunction() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL+"login/");
        url.append(email.getText() + "/" + password.getText());
        Log.d("URL", url.toString());
        StringRequest stringRequest = getStringRequest(url);
        VolleyNetwork.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @NonNull
    private StringRequest getStringRequest(StringBuilder url) {
        return new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getActivity().getApplicationContext(), jsonObject.getString("response"), Toast.LENGTH_LONG).show();
                            if(jsonObject.getString("response").equals("Login Sucessful")){
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
