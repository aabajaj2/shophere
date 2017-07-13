package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.CartViewModel;

public class CartFragment extends Fragment {
    private FragmentCartBinding fragmentCartBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_cart, container, false);
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container, false);
        Product product = new Product(null,"",null, null,null,null);
        CartViewModel cartViewModel = new CartViewModel(product, this, fragmentCartBinding);
        cartViewModel.getCartDetails();
        fragmentCartBinding.setCavm(cartViewModel);
        return fragmentCartBinding.getRoot();
    }
}