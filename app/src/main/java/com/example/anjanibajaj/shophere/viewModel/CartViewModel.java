package com.example.anjanibajaj.shophere.viewModel;

import android.arch.persistence.room.Room;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.anjanibajaj.shophere.CartFragment;
import com.example.anjanibajaj.shophere.adapters.CartAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentCartBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;
import com.example.anjanibajaj.shophere.utils.AppDatabase;
import com.example.anjanibajaj.shophere.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 * This class binds the data for the view cart
 */

public class CartViewModel extends BaseObservable {
    private Product product;
    private CartFragment cartFragment;
    private FragmentCartBinding fcb;
    private SessionManager sessionManager;
    private ProductViewModel pvm;
    List<Product> cartProducts;

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
    public String getProductName() {
        return product.getName();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<String> getCartDetails() {
        sessionManager = new SessionManager(cartFragment.getActivity());
        if (sessionManager.getProductDetails().get(SessionManager.PIDLIST) != null) {
            Toast.makeText(cartFragment.getActivity().getApplicationContext(), "SIZE OF CART=" + String.valueOf(sessionManager.getProductDetails().get(SessionManager.PIDLIST).size()), Toast.LENGTH_LONG).show();
            Set<String> pidList = sessionManager.getProductDetails().get(SessionManager.PIDLIST);
            new FetchProducts(pidList).execute();
//            setAdapterCart();
            return sessionManager.getProductDetails().get(SessionManager.PIDLIST);
        } else {
            Toast.makeText(cartFragment.getActivity().getApplicationContext(), "Your cart is empty!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void setAdapterCart() {
        CartAdapter cartAdapter = new CartAdapter(cartProducts, cartFragment, fcb);
        fcb.recyclerView3.setAdapter(cartAdapter);
    }

    private class FetchProducts extends AsyncTask<Void, Void, Void> {

        private Set<String> pidList;
        AppDatabase db;

        FetchProducts(Set<String> pidList) {
            this.pidList = pidList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartProducts = new ArrayList<>();
            db = Room.databaseBuilder(cartFragment.getActivity().getApplicationContext(),
                    AppDatabase.class, "product-db").build();
            ProductTable pt;
            List<ProductTable> products = db.productDao().fetchAllData();
            Log.d("Size in cart= ", String.valueOf(db.productDao().fetchAllData().size()));
            for (String p : pidList) {
                pt = db.productDao().getSingleRecord(Integer.parseInt(p));
                Product pro = new Product(pt.getName(), pt.getCategory(), pt.getPrice(), pt.getPid(), pt.getCid(), pt.getImageUrl());
                cartProducts.add(pro);
            }
            Log.d("Size of Cart Products:", String.valueOf(cartProducts.size()));
            return null;
        }
    }
}