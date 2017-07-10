package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentRegisterBinding;
import com.example.anjanibajaj.shophere.model.User;
import com.example.anjanibajaj.shophere.viewModel.RegisterViewModel;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding fragmentRegisterBinding;
    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register,container, false);
        User user = new User("","");
        registerViewModel = new RegisterViewModel(user, fragmentRegisterBinding, this);
        fragmentRegisterBinding.setRvm(registerViewModel);
        return fragmentRegisterBinding.getRoot();
    }
}
