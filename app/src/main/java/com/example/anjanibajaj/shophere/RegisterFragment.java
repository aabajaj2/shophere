package com.example.anjanibajaj.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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


public class RegisterFragment extends Fragment {
    EditText email, password;
    Button registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        email = registerView.findViewById(R.id.remail);
        password = registerView.findViewById(R.id.rpassword);
        registerButton = registerView.findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFunction();
            }
        });

        return registerView;
    }

    private void registerFunction() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL + "register/");
        url.append(email.getText() + "/" + password.getText());
        Log.d("URL", url.toString());
        StringRequest stringRequest = getStringRequest(url);
        VolleyNetwork.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private StringRequest getStringRequest(StringBuilder url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("response");
                            Toast.makeText(getActivity().getApplicationContext(), check, Toast.LENGTH_LONG).show();
                            if (check.equals("Registered")) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error in connection", Toast.LENGTH_LONG).show();
            }
        });
        return stringRequest;
    }
}
