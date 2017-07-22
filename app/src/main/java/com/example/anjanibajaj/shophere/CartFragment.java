package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.CartViewModel;

import java.util.concurrent.ExecutionException;

public class CartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCartBinding fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        Product product = new Product("","",null, null,null,null);
        CartViewModel cartViewModel = new CartViewModel(product, this, fragmentCartBinding);
        try {
            cartViewModel.getCartDetails();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1,1);
        fragmentCartBinding.recyclerView3.setLayoutManager(sglm);
        fragmentCartBinding.setCavm(cartViewModel);
        return fragmentCartBinding.getRoot();
    }
}