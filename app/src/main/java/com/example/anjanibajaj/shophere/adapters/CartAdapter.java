package com.example.anjanibajaj.shophere.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.databinding.ViewCartCardBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.CartViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/13/2017.
 *
 */

public class CartAdapter extends RecyclerView.Adapter {
    private List<Product> products;
    private CartFragment cartFragment;
    private FragmentCartBinding fcb;

    public CartAdapter(List<Product> products, CartFragment cartFragment, FragmentCartBinding fcb) {
        this.products = products;
        this.cartFragment = cartFragment;
        this.fcb = fcb;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewCartCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_cart_card, parent, false);
        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewModel cartViewModel = new CartViewModel(products.get(position), cartFragment, fcb);
        Log.d("IN Adapterrrrr", String.valueOf(products.size()));
        ViewCartCardBinding binding = ((myViewHolder) holder).ccb;
        binding.setCavm(cartViewModel);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private class myViewHolder extends RecyclerView.ViewHolder{
        ViewCartCardBinding ccb;
        myViewHolder(View itemView) {
            super(itemView);
            ccb = DataBindingUtil.bind(itemView);
        }
    }
}
