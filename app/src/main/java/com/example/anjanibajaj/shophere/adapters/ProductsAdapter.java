package com.example.anjanibajaj.shophere.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.ActivityProductCardViewBinding;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/7/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter {
    List<Product> productList;
    private IndexFragment indexFragment;

    public ProductsAdapter(List<Product> products, IndexFragment indexFragment ){
        this.productList = products;
        this.indexFragment = indexFragment;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityProductCardViewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_product_card_view, parent, false);
        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductViewModel productViewModel = new ProductViewModel(productList.get(position), indexFragment);
        myViewHolder myViewHolder = (myViewHolder) holder;
        ActivityProductCardViewBinding binding = ((myViewHolder) holder).cvb;
        binding.setPvm(productViewModel);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    private class myViewHolder extends RecyclerView.ViewHolder{
        ActivityProductCardViewBinding cvb;
        public myViewHolder(View itemView) {
            super(itemView);
            cvb = DataBindingUtil.bind(itemView);
        }

    }
}
