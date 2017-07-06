package com.example.anjanibajaj.shophere.viewModel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjanibajaj.shophere.LoginFragment;
import com.example.anjanibajaj.shophere.MainActivity;
import com.example.anjanibajaj.shophere.Utils.Constants;
import com.example.anjanibajaj.shophere.Utils.VolleyNetwork;
import com.example.anjanibajaj.shophere.databinding.FragmentLoginBinding;
import com.example.anjanibajaj.shophere.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 */

public class LoginViewModel extends BaseObservable {
    private User user;
    private FragmentLoginBinding fragmentLoginBinding;
    private LoginFragment loginFragment;

    public LoginViewModel(User user, FragmentLoginBinding fragmentLoginBinding, LoginFragment loginFragment) {
        this.user = user;
        this.fragmentLoginBinding = fragmentLoginBinding;
        this.loginFragment = loginFragment;
    }

    public User getUser() {
        return user;
    }

    public FragmentLoginBinding getFragmentLoginBinding() {
        return fragmentLoginBinding;
    }
    @Bindable
    public  String getUsername(){
        return user.getUsername();
    }

    @Bindable
    public  String getPassword(){
        return user.getPassword();
    }

    public void setUsername(String username){
        user.setUsername(username);
        Log.d("Username:", username+" is set");
    }

    public void setPassword(String password){
        user.setPassword(password);
        Log.d("Password:", password+" is set");
    }

    public View.OnClickListener onLoginClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), user.getUsername()+" Clicked", Toast.LENGTH_SHORT).show();
                loginFunction(buildUrl());
            }
        };
    }
    @NonNull
    public String buildUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL+"login/");
        url.append(user.getUsername() + "/" + user.getPassword());
        Log.d("URL", url.toString());
        return url.toString();
    }

    public void loginFunction(String url) {
        StringRequest stringRequest = getStringRequest(url);
        VolleyNetwork.getInstance(loginFragment.getActivity()).addToRequestQueue(stringRequest);
    }

    @NonNull
    private StringRequest getStringRequest(String url) {
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(loginFragment.getActivity().getApplicationContext(), jsonObject.getString("response"), Toast.LENGTH_LONG).show();
                            if(jsonObject.getString("response").equals("Login Successful")){
                                Intent intent = new Intent(loginFragment.getActivity().getApplicationContext(), MainActivity.class);
                                loginFragment.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginFragment.getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
