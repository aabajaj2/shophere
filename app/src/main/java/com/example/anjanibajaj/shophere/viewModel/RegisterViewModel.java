package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjanibajaj.shophere.LoginFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.RegisterFragment;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;
import com.example.anjanibajaj.shophere.databinding.FragmentRegisterBinding;
import com.example.anjanibajaj.shophere.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 *  ViewModel for RegisterFragment
 * Sends request /register/email/password using Singleton VolleyNetwork Class
 */

public class RegisterViewModel extends BaseObservable{
    private User user;
    private FragmentRegisterBinding fragmentRegisterBinding;
    private RegisterFragment registerFragment;

    public RegisterViewModel(User user, FragmentRegisterBinding fragmentRegisterBinding, RegisterFragment registerFragment) {
        this.user = user;
        this.fragmentRegisterBinding = fragmentRegisterBinding;
        this.registerFragment = registerFragment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Bindable
    public  String getUsername(){
        return user.getUsername();
    }

    @Bindable
    public  String getPassword(){
        return user.getPassword();
    }

    public void setFragmentRegisterBinding(FragmentRegisterBinding fragmentRegisterBinding) {
        this.fragmentRegisterBinding = fragmentRegisterBinding;
    }

    public void setUsername(String username){
        user.setUsername(username);
        Log.d("Username  = ", username);
    }

    public void setPassword(String password){
        user.setPassword(password);
    }

    public View.OnClickListener onUserRegisterClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFunction();
            }
        };
    }

    private void registerFunction() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL + "register/");
        url.append(user.getUsername()).append("/").append(user.getPassword());
        Log.d("URL", url.toString());
        StringRequest stringRequest = getStringRequest(url);
        VolleyNetwork.getInstance(registerFragment.getActivity()).addToRequestQueue(stringRequest);
    }

    private StringRequest getStringRequest(StringBuilder url) {
        return new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("response");
                            Toast.makeText(registerFragment.getActivity().getApplicationContext(), check, Toast.LENGTH_LONG).show();
                            if (check.equals("Registered")) {
                                FragmentManager fragmentManager = registerFragment.getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                LoginFragment loginFragment = new LoginFragment();
                                fragmentTransaction.replace(R.id.login_content, loginFragment);
                                fragmentTransaction.commit();
                            } else{
                                Toast.makeText(registerFragment.getActivity().getApplicationContext(),jsonObject.getString("response"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registerFragment.getActivity().getApplicationContext(),"Connection Error: Cannot reach the server!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
