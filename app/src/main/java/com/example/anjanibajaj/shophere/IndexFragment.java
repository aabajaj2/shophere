package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.adapters.ProductsAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;


public class IndexFragment extends Fragment {

    private FragmentIndexBinding fragmentIndexBinding;
    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentIndexBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_index,container, false);
        Product product = new Product("","",null,null,null);
        productViewModel = new ProductViewModel(product, this, fragmentIndexBinding);
        productViewModel.getCategories(productViewModel.buildUrl("category"));
        productViewModel.getAllProducts(productViewModel.buildUrl("products"));
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, 1);
        fragmentIndexBinding.recyclerView.setLayoutManager(sglm);
        fragmentIndexBinding.setPvm(productViewModel);
        return fragmentIndexBinding.getRoot();
    }
}
