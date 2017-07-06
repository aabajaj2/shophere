package com.example.anjanibajaj.shophere;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentLoginBinding;
import com.example.anjanibajaj.shophere.model.User;
import com.example.anjanibajaj.shophere.viewModel.LoginViewModel;


public class LoginFragment extends Fragment {

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
