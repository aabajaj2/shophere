package com.example.anjanibajaj.shophere.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.ProductFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.databinding.FragmentProductBinding;
import com.example.anjanibajaj.shophere.databinding.ViewProductCardBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/7/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter {
    List<Product> productList;
    private ProductFragment productFragment;
    private FragmentProductBinding fragmentProductBinding;

    public ProductsAdapter(List<Product> products, ProductFragment productFragment, FragmentProductBinding fragmentProductBinding ){
        this.productList = products;
        this.productFragment = productFragment;
        this.fragmentProductBinding = fragmentProductBinding;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewProductCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_product_card, parent, false);
        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductViewModel productViewModel = new ProductViewModel(productList.get(position), productFragment, fragmentProductBinding);
        myViewHolder myViewHolder = (myViewHolder) holder;
        holder.itemView.setTag(productViewModel.getProduct());
        ViewProductCardBinding binding = ((myViewHolder) holder).cvb;
        binding.setPvm(productViewModel);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    private class myViewHolder extends RecyclerView.ViewHolder{
        ViewProductCardBinding cvb;
        public myViewHolder(View itemView) {
            super(itemView);
            cvb = DataBindingUtil.bind(itemView);
        }
    }
}
