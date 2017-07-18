package com.example.anjanibajaj.shophere.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.databinding.ViewCategoryCardBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.viewModel.CategoryViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/8/2017.
 *
 */

public class CategoryAdapter extends RecyclerView.Adapter{
    private List<Category> categoryList;
    private IndexFragment indexFragment;
    private FragmentIndexBinding fragmentIndexBinding;

    public CategoryAdapter(List<Category> categories, IndexFragment indexFragment, FragmentIndexBinding fragmentIndexBinding ){
        this.categoryList = categories;
        this.indexFragment = indexFragment;
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewCategoryCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_category_card, parent, false);
        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewModel categoryViewModel = new CategoryViewModel(categoryList.get(position), indexFragment, fragmentIndexBinding);
//        Integer cid = categoryViewModel.getCid();
//        String type = categoryViewModel.getType();
        Category category = categoryViewModel.getCategory();
        holder.itemView.setTag(category);
//        holder.itemView.setTag(cid);
//        holder.itemView.setTag(type);
        ViewCategoryCardBinding binding = ((myViewHolder) holder).cvb;
        binding.setCvm(categoryViewModel);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private class myViewHolder extends RecyclerView.ViewHolder{
        ViewCategoryCardBinding cvb;
        myViewHolder(View itemView) {
            super(itemView);
            cvb = DataBindingUtil.bind(itemView);
        }
    }
}
