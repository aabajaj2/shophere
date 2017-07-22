package com.example.anjanibajaj.shophere.viewModel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.anjanibajaj.shophere.BR;
import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.LoginActivity;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.adapters.CartAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.FetchProducts;
import com.example.anjanibajaj.shophere.utils.SessionManager;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.example.anjanibajaj.shophere.R.string.email;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 * This class binds the data for the view cart
 * Sets the adapter for cart recycler view
 * Removes elements from the cart, updates cart total
 */

public class CartViewModel extends BaseObservable {
    private Product product;
    private CartFragment cartFragment;
    private FragmentCartBinding fcb;
    private Integer cartTotal;
    public boolean isVisible = true;

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
    public String getCartTotal(){
        Log.d("getCartTotal", String.valueOf(cartTotal));
        return String.valueOf(cartTotal);
    }

    public void setCartTotal(String cartTotal){
        this.cartTotal = Integer.valueOf(cartTotal);
        notifyPropertyChanged(BR.cartTotal);
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    @SuppressWarnings("ConstantConditions")
    public void getCartDetails() throws ExecutionException, InterruptedException {
        SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
        Set<String> sessionPidList = sessionManager.getProductDetails(SessionManager.PIDLIST).get(SessionManager.PIDLIST);
        if (sessionPidList != null) {
            List<Product> cartProducts = new FetchProducts(sessionPidList, cartFragment.getContext()).execute().get();
            updateCartTotal(cartProducts);
            CartAdapter cartAdapter = new CartAdapter(cartProducts, cartFragment, fcb);
            fcb.recyclerView3.setAdapter(cartAdapter);
        }
    }

    private void updateCartTotal(List<Product> cartProducts) {
        cartTotal = 0;
        for (Product p: cartProducts) {
            cartTotal = cartTotal+ p.getPrice();
        }
        setCartTotal(String.valueOf(cartTotal));
        Log.d("update cart total", String.valueOf(cartTotal));
    }

    public View.OnClickListener onClearCartClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
                    sessionManager.clearCart();
                    getCartDetails();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public View.OnClickListener onRemoveClicked(){
        return new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(cartFragment.getActivity());
                sessionManager.onRemoveClicked(product.getPid());
                CartFragment newCart = new CartFragment();
                FragmentManager fragmentManager = cartFragment.getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, newCart);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Snackbar.make(cartFragment.getView(),product.getName()+" is removed from the cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
    }

    public View.OnClickListener onProceedToPayClicked(){
        return new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(cartFragment.getContext());
                String email = sessionManager.getUserDetails().get(SessionManager.EMAIL);
                if (email != null) {
                    if(cartTotal>0) {
                        startPayment(email);
                    }else {
                        Toast.makeText(cartFragment.getActivity().getApplicationContext(), "Add some products in the cart first", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(cartFragment.getActivity(), LoginActivity.class);
                    cartFragment.getActivity().startActivity(intent);
                    Toast.makeText(cartFragment.getActivity().getApplicationContext(), "You need to login for payment!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void startPayment(String email) {
        Checkout checkout = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", email);
            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");

            options.put("currency", "USD");

            options.put("amount", cartTotal * 100);

            checkout.open(cartFragment.getActivity(), options);

        } catch (Exception e) {
            Log.e("razor pay", "Error in starting Razorpay Checkout", e);
        }
    }
}