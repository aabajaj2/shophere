package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentProductBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.viewModel.ProductViewModel;

public class ProductFragment extends Fragment {
    private Integer cid;
    private String type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProductBinding fragmentProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        Product product = new Product(null,null,null,null,null);
        ProductViewModel productViewModel = new ProductViewModel(product, this, fragmentProductBinding);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cid = bundle.getInt("cid");
            type = bundle.getString("ctype");
        }
        productViewModel.getAllProducts(Constants.APP_URL + "products", cid);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, 1);
        fragmentProductBinding.recyclerView2.setLayoutManager(sglm);
        fragmentProductBinding.setPvm(productViewModel);
        return fragmentProductBinding.getRoot();
    }

}
