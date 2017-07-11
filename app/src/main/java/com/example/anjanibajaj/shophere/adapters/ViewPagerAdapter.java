package com.example.anjanibajaj.shophere.adapters;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anjanibajaj.shophere.ProductDetailsFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.FragmentProductDetailsBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductDetailsViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/11/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<String> images;
    private LayoutInflater inflater;
    private ProductDetailsFragment productDetailsFragment;

    public ViewPagerAdapter(List<String> images, LayoutInflater inflater, ProductDetailsFragment productDetailsFragment) {
        this.images = images;
        this.inflater = inflater;
        this.productDetailsFragment = productDetailsFragment;
    }

    public List<String> getImages() {

        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public ProductDetailsFragment getProductDetailsFragment() {
        return productDetailsFragment;
    }

    public void setProductDetailsFragment(ProductDetailsFragment productDetailsFragment) {
        this.productDetailsFragment = productDetailsFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        FragmentProductDetailsBinding fragmentProductDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), R.layout.image_slide, view, false);
        Product product = new Product(null,null,null,null,null,null);
        ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(product,fragmentProductDetailsBinding, productDetailsFragment);
        fragmentProductDetailsBinding.setPdvm(productDetailsViewModel);
        return fragmentProductDetailsBinding.getRoot();
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
