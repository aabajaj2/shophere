package com.example.anjanibajaj.shophere.utils;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Anjani Bajaj on 7/20/2017.
 * This is a helper class for fetching the products from Room DB
 */

public class FetchProducts extends AsyncTask<Void,Void, List<Product>> {
    private Set<String> pidList;
    private Context context;


    public FetchProducts(Set<String> pidList, Context context) {
        this.pidList = pidList;
        this.context = context;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        List<Product> cartProducts = new ArrayList<>();
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "product-db").build();
        ProductTable pt;
        for (String p : pidList) {
            pt = db.productDao().getSingleRecord(Integer.parseInt(p));
            Product pro = new Product(pt.getName(), pt.getCategory(), pt.getPrice(), pt.getPid(), pt.getCid(), pt.getImageUrl());
            cartProducts.add(pro);
        }
        return cartProducts;
    }
}
