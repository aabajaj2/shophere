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
        productViewModel = new ProductViewModel(product, this);
        productViewModel.getCategories(productViewModel.buildUrl("category"));
        productViewModel.getAllProducts(productViewModel.buildUrl("products"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        fragmentIndexBinding.recyclerView.setLayoutManager(layoutManager);
        List<Product> products = new ArrayList<>();
        products.add(new Product("CHeck", "check", 0 , 0 , 0));
        ProductsAdapter productsAdapter = new ProductsAdapter(products, this);
        fragmentIndexBinding.recyclerView.setAdapter(productsAdapter);
        fragmentIndexBinding.setPvm(productViewModel);
        return fragmentIndexBinding.getRoot();
    }
}
