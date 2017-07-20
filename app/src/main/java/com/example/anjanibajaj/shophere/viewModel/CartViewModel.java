package com.example.anjanibajaj.shophere.viewModel;

import android.arch.persistence.room.Room;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.adapters.CartAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;
import com.example.anjanibajaj.shophere.utils.AppDatabase;
import com.example.anjanibajaj.shophere.utils.FetchProducts;
import com.example.anjanibajaj.shophere.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 * This class binds the data for the view cart
 */

public class CartViewModel extends BaseObservable {
    private Product product;
    private CartFragment cartFragment;
    private FragmentCartBinding fcb;
    private List<Product> cartProducts;

    public CartViewModel(Product product, CartFragment cartFragment, FragmentCartBinding fragmentCartBinding) {
        this.product = product;
        this.cartFragment = cartFragment;
        this.fcb = fragmentCartBinding;
    }

    @Bindable
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    @Bindable
    public String getProductPrice() {
        return "$"+String.valueOf(product.getPrice());
    }

    public void setProductPrice(String price) {
        this.product.setPrice(Integer.valueOf(price));
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).centerCrop().into(view);
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    public void getCartDetails() throws ExecutionException, InterruptedException {
        SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
        Set<String> sessionPidList = sessionManager.getProductDetails(SessionManager.PIDLIST).get(SessionManager.PIDLIST);
        if (sessionPidList != null) {
            Toast.makeText(cartFragment.getActivity().getApplicationContext(), "Size of your cart is " + String.valueOf(sessionPidList.size()), Toast.LENGTH_SHORT).show();
            cartProducts = new FetchProducts(sessionPidList, cartFragment.getContext()).execute().get();
            CartAdapter cartAdapter = new CartAdapter(cartProducts, cartFragment, fcb);
            fcb.recyclerView3.setAdapter(cartAdapter);
        }
    }

    public View.OnClickListener onClearCartClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    clearCartFunction();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void clearCartFunction() throws ExecutionException, InterruptedException {
        SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
        sessionManager.clearCart();
        getCartDetails();
    }
}