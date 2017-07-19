package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentProductDetailsBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductDetailsViewModel;


public class ProductDetailsFragment extends Fragment {

    private Integer pid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProductDetailsBinding fragmentProductDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        Product product = new Product(null,null,null,null,null);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            product.setPid(bundle.getInt("pid"));
            product.setName(bundle.getString("name"));
            product.setPrice(bundle.getInt("price"));
            product.setCategory(bundle.getString("category"));
            product.setImageList(bundle.getStringArrayList("imageList"));
        }
        ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(product, fragmentProductDetailsBinding, this);
//        productDetailsViewModel.setAdapterImageViewPager(product.getImageList());
        fragmentProductDetailsBinding.setPdvm(productDetailsViewModel);
        return fragmentProductDetailsBinding.getRoot();
    }
}
