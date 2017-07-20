package com.example.anjanibajaj.shophere.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.WishListFragment;
import com.example.anjanibajaj.shophere.databinding.FragmentWishListBinding;
import com.example.anjanibajaj.shophere.databinding.ViewWishlistCardBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.WishListViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/20/2017.
 *
 */

public class WishListAdapter extends RecyclerView.Adapter {
    private List<Product> products;
    private WishListFragment wishListFragment;
    private FragmentWishListBinding fragmentWishListBinding;


    public WishListAdapter(List<Product> products, WishListFragment wishListFragment, FragmentWishListBinding fragmentWishListBinding) {
        this.products = products;
        this.wishListFragment = wishListFragment;
        this.fragmentWishListBinding = fragmentWishListBinding;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewWishlistCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_wishlist_card, parent, false);
        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WishListViewModel wishListViewModel = new WishListViewModel(products.get(position), wishListFragment, fragmentWishListBinding);
        ViewWishlistCardBinding binding = ((myViewHolder) holder).wishlistCardBinding;
        binding.setWlvm(wishListViewModel);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private class myViewHolder extends RecyclerView.ViewHolder{
        ViewWishlistCardBinding wishlistCardBinding;
        myViewHolder(View itemView) {
            super(itemView);
            wishlistCardBinding = DataBindingUtil.bind(itemView);
        }
    }
}