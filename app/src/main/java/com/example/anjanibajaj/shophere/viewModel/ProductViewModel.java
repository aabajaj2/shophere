package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;

import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Product;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 */

public class ProductViewModel extends BaseObservable {
    private Product product;
    private FragmentIndexBinding fragmentIndexBinding;
    private IndexFragment indexFragment;

    public ProductViewModel(Product product, FragmentIndexBinding fragmentIndexBinding, IndexFragment indexFragment) {
        this.product = product;
        this.fragmentIndexBinding = fragmentIndexBinding;
        this.indexFragment = indexFragment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public FragmentIndexBinding getFragmentIndexBinding() {
        return fragmentIndexBinding;
    }

    public void setFragmentIndexBinding(FragmentIndexBinding fragmentIndexBinding) {
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    public IndexFragment getIndexFragment() {
        return indexFragment;
    }

    public void setIndexFragment(IndexFragment indexFragment) {
        this.indexFragment = indexFragment;
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name){
        product.setName(name);
    }

}
