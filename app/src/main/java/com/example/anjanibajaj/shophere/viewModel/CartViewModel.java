package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;

import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.model.Product;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 */

public class CartViewModel extends BaseObservable {
    private Product product;
    private CartFragment cartFragment;

    public CartViewModel(Product product, CartFragment cartFragment) {
        this.product = product;
        this.cartFragment = cartFragment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartFragment getCartFragment() {
        return cartFragment;
    }

    public void setCartFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }

}