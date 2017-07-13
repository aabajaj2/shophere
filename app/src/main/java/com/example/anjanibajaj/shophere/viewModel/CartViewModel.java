package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.util.Log;
import android.widget.Toast;

import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.SessionManager;

import java.util.List;
import java.util.Set;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 */

public class CartViewModel extends BaseObservable {
    private Product product;
    private CartFragment cartFragment;
    private FragmentCartBinding fcb;
    private SessionManager sessionManager;

    public CartViewModel(Product product, CartFragment cartFragment) {
        this.product = product;
        this.cartFragment = cartFragment;
    }

    public CartViewModel(Product product, CartFragment cartFragment, FragmentCartBinding fragmentCartBinding) {
        this.product = product;
        this.cartFragment = cartFragment;
        this.fcb = fragmentCartBinding;
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

    public Set<String> getCartDetails(){
        sessionManager = new SessionManager(cartFragment.getActivity());
        Toast.makeText(cartFragment.getActivity().getApplicationContext(), "SIZE OF CART="+String.valueOf(sessionManager.getProductDetails().get(SessionManager.PIDLIST).size()), Toast.LENGTH_LONG).show();
        return sessionManager.getProductDetails().get(SessionManager.PIDLIST);
    }
}