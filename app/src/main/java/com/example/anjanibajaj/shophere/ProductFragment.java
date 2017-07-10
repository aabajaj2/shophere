package com.example.anjanibajaj.shophere;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentProductBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.viewModel.ProductViewModel;

public class ProductFragment extends Fragment {
    private FragmentProductBinding fragmentProductBinding;
    private Integer cid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           fragmentProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product,container, false);
           Product product = new Product(null,null,null,null,null);
           ProductViewModel productViewModel = new ProductViewModel(product, this, fragmentProductBinding);
           Bundle bundle = this.getArguments();
           if (bundle != null) {
               cid = bundle.getInt("cid");
           }
           Log.d("CID", String.valueOf(cid));
           productViewModel.getAllProducts(buildUrl("products"), cid);
           StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(4, 1);
           fragmentProductBinding.recyclerView2.setLayoutManager(sglm);
           fragmentProductBinding.setPvm(productViewModel);
           return fragmentProductBinding.getRoot();
       }

    @NonNull
    public String buildUrl(String type) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL).append(type);
        Log.d("URL", url.toString());
        return url.toString();
    }
}
