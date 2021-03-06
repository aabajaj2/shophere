package com.example.anjanibajaj.shophere;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.anjanibajaj.shophere.databinding.FragmentProductDetailsBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.viewModel.ProductDetailsViewModel;


public class ProductDetailsFragment extends Fragment {

    private Integer pid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //noinspection ConstantConditions
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Product Details");
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
        // Testing slider without data binding
        SliderLayout sliderShow = fragmentProductDetailsBinding.getRoot().findViewById(R.id.slider);

        for (String i : product.getImageList()) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(product.getName())
                    .image(i);
            sliderShow.addSlider(textSliderView);
        }

        ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(product, fragmentProductDetailsBinding, this);
        fragmentProductDetailsBinding.setPdvm(productDetailsViewModel);
        return fragmentProductDetailsBinding.getRoot();
    }
}
