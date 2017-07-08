package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.adapters.ProductsAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.viewModel.CategoryViewModel;
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
        Category category = new Category(null,"");
        CategoryViewModel categoryViewModel = new CategoryViewModel(category, this, fragmentIndexBinding);
        categoryViewModel.getCategories(buildUrl("category"));
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, 1);
        fragmentIndexBinding.recyclerView.setLayoutManager(sglm);
        fragmentIndexBinding.setCvm(categoryViewModel);
        return fragmentIndexBinding.getRoot();
    }

    @NonNull
    public String buildUrl(String type) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL).append(type);
        Log.d("URL", url.toString());
        return url.toString();
    }
}
