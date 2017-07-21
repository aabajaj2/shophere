package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.viewModel.CategoryViewModel;


public class IndexFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentIndexBinding fragmentIndexBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_index, container, false);
        Category category = new Category(null,"");
        CategoryViewModel categoryViewModel = new CategoryViewModel(category, this, fragmentIndexBinding);
        categoryViewModel.getCategories(Constants.APP_URL + "category");
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, 1);
        fragmentIndexBinding.recyclerView.setLayoutManager(sglm);
        fragmentIndexBinding.setCvm(categoryViewModel);
        return fragmentIndexBinding.getRoot();
    }
}
