package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container, false);
        Product product = new Product("TV","",null, null,null,null);
        CartViewModel cartViewModel = new CartViewModel(product, this, fragmentCartBinding);
        cartViewModel.getCartDetails();
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1,1);
        fragmentCartBinding.recyclerView3.setLayoutManager(sglm);
        fragmentCartBinding.setCavm(cartViewModel);
        return fragmentCartBinding.getRoot();
    }
}