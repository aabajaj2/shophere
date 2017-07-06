package com.example.anjanibajaj.shophere.viewModel;

import com.example.anjanibajaj.shophere.R;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.anjanibajaj.shophere.RegisterFragment;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;
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
    }

    public void setPassword(String password){
        user.setPassword(password);
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

    public View.OnClickListener onRegisterClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = loginFragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.content, registerFragment);
                fragmentTransaction.commit();
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
