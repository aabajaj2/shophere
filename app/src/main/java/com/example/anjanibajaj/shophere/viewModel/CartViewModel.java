package com.example.anjanibajaj.shophere.viewModel;

import android.arch.persistence.room.Room;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.adapters.CartAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;
import com.example.anjanibajaj.shophere.utils.AppDatabase;
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
    private String price;

    public CartViewModel(Product product, CartFragment cartFragment, FragmentCartBinding fragmentCartBinding) {
        this.product = product;
        this.cartFragment = cartFragment;
        this.fcb = fragmentCartBinding;
    }

    @Bindable
    public Product getProduct() {
        return product;
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
        Log.d("Image url from glide", url);
        Glide.with(view.getContext()).load(url).centerCrop().into(view);
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    public Set<String> getCartDetails() throws ExecutionException, InterruptedException {
        SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
        if (sessionManager.getProductDetails().get(SessionManager.PIDLIST) != null) {
            Toast.makeText(cartFragment.getActivity().getApplicationContext(), "SIZE OF CART=" + String.valueOf(sessionManager.getProductDetails().get(SessionManager.PIDLIST).size()), Toast.LENGTH_LONG).show();
            Set<String> pidList = sessionManager.getProductDetails().get(SessionManager.PIDLIST);
            cartProducts = new FetchProducts(pidList).execute().get();
            Log.d("Cp getcartdetails ", String.valueOf(cartProducts.size()));
            CartAdapter cartAdapter = new CartAdapter(cartProducts, cartFragment, fcb);
            fcb.recyclerView3.setAdapter(cartAdapter);
            return sessionManager.getProductDetails().get(SessionManager.PIDLIST);
        } else {
            Toast.makeText(cartFragment.getActivity().getApplicationContext(), "Your cart is empty!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private class FetchProducts extends AsyncTask<Void,Void, List<Product>> {

        private Set<String> pidList;
        AppDatabase db;

        FetchProducts(Set<String> pidList) {
            this.pidList = pidList;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            cartProducts = new ArrayList<>();
            db = Room.databaseBuilder(cartFragment.getActivity().getApplicationContext(),
                    AppDatabase.class, "product-db").build();
            ProductTable pt;
            for (String p : pidList) {
                pt = db.productDao().getSingleRecord(Integer.parseInt(p));
                Product pro = new Product(pt.getName(), pt.getCategory(), pt.getPrice(), pt.getPid(), pt.getCid(), pt.getImageUrl());
                Log.d("price in cavm", String.valueOf(pt.getPrice()));
                cartProducts.add(pro);
            }
            return cartProducts;
        }
    }

    public View.OnClickListener onClearCartClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    clearCartFunction();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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