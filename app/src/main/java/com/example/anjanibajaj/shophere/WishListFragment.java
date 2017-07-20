package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentWishListBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.WishListViewModel;

import java.util.concurrent.ExecutionException;


public class WishListFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentWishListBinding fragmentWishListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list,container, false);
        Product product = new Product("TV","",null, null,null,null);
        WishListViewModel wishListViewModel = new WishListViewModel(product, this, fragmentWishListBinding);
        try {
            wishListViewModel.getListDetails();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2,1);
        fragmentWishListBinding.recyclerView4.setLayoutManager(sglm);
        fragmentWishListBinding.setWlvm(wishListViewModel);
        return fragmentWishListBinding.getRoot();
    }
}
