package com.example.anjanibajaj.shophere.adapters;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anjanibajaj.shophere.ProductDetailsFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.databinding.FragmentProductDetailsBinding;
import com.example.anjanibajaj.shophere.databinding.ImageSlideBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductDetailsViewModel;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/11/2017.
 *
 *
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<String> images;
    private ProductDetailsFragment productDetailsFragment;
    private FragmentProductDetailsBinding fragmentProductDetailsBinding;

    public ViewPagerAdapter(List<String> images, ProductDetailsFragment productDetailsFragment, FragmentProductDetailsBinding fragmentProductDetailsBinding) {
        this.images = images;
        this.productDetailsFragment = productDetailsFragment;
        this.fragmentProductDetailsBinding = fragmentProductDetailsBinding;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(productDetailsFragment.getContext());
        View itemView = layoutInflater.inflate(R.layout.fragment_image_slide, view, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        view.addView(imageView);
        return itemView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }


}
