package com.example.anjanibajaj.shophere;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
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
import com.example.anjanibajaj.shophere.databinding.FragmentLoginBinding;
import com.example.anjanibajaj.shophere.model.User;
import com.example.anjanibajaj.shophere.viewModel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    EditText email, password;
    Button loginButton, registerButton;
    private FragmentLoginBinding loginBinding;
    private LoginViewModel loginViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container, false);
        User user = new User("","");
        loginViewModel = new LoginViewModel(user, loginBinding, this);
        loginBinding.setLvm(loginViewModel);
        return loginBinding.getRoot();
    }

}
