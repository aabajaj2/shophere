package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.anjanibajaj.shophere.ProductDetailsFragment;
import com.example.anjanibajaj.shophere.databinding.FragmentProductDetailsBinding;
import com.example.anjanibajaj.shophere.model.Product;


/**
 * Created by Anjani Bajaj on 7/11/2017.
 *
 */

public class ProductDetailsViewModel extends BaseObservable {
    private Product product;
    private FragmentProductDetailsBinding fragmentProductDetailsBinding;
    private ProductDetailsFragment productDetailsFragment;
    private ProductDetailsViewModel pdvm;

    public ProductDetailsViewModel(Product product, FragmentProductDetailsBinding fragmentProductDetailsBinding, ProductDetailsFragment productDetailsFragment) {
        this.product = product;
        this.fragmentProductDetailsBinding = fragmentProductDetailsBinding;
        this.productDetailsFragment = productDetailsFragment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public FragmentProductDetailsBinding getFragmentProductDetailsBinding() {
        return fragmentProductDetailsBinding;
    }

    public void setFragmentProductDetailsBinding(FragmentProductDetailsBinding fragmentProductDetailsBinding) {
        this.fragmentProductDetailsBinding = fragmentProductDetailsBinding;
    }

    public ProductDetailsFragment getProductDetailsFragment() {
        return productDetailsFragment;
    }

    public void setProductDetailsFragment(ProductDetailsFragment productDetailsFragment) {
        this.productDetailsFragment = productDetailsFragment;
    }

    @Bindable
    public String getName() {
        return "Name: "+ product.getName();
    }

    @Bindable
    public String getPrice() {
        return "Price: $"+String.valueOf(product.getPrice());
    }

    public void setPrice(String price){
        product.setPrice(Integer.valueOf(price));
    }

    public void setName(String name){
        product.setName(name);
    }

    @Bindable
    public String getCategory() {
        return "Category: "+product.getCategory();
    }

    public void setCategory(String category){
        product.setCategory(category);
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    @Bindable
    public String getPid() {
        return "Product id: "+String.valueOf(product.getPid());
    }

    public void setPid(String pid){
        product.setPid(Integer.valueOf(pid));
    }

}
