package com.example.anjanibajaj.shophere.utils;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/14/2017.
 *
 */

public class DatabaseAsync extends AsyncTask<Void, Void, Void> {

    private List<Product> products;
    private Context mContext;
    private AppDatabase db;

    public DatabaseAsync(List<Product> products, Context mContext){
        this.products = products;
        this.mContext = mContext;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Perform pre-adding operation here.
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = Room.databaseBuilder(mContext,
                AppDatabase.class, "product-db").build();
        ProductTable pt;
        for (Product p: products) {
            pt = new ProductTable();
            pt.setPid(p.getPid());
            pt.setName(p.getName());
            pt.setPrice(p.getPrice());
            pt.setCategory(p.getCategory());
            pt.setCid(p.getCid());
            pt.setImageUrl(p.getImageUrl());
            db.productDao().deleteProducts(pt);
            //Check is pid is unique, i.e. if the table already contains the entry.
            if(db.productDao().getSingleRecord(p.getPid()) == null){
                db.productDao().insertOnlySingleRecord(pt);
            }
        }
        Log.d("Size of pt from da", String.valueOf(db.productDao().fetchAllData().size()));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //To after addition operation here.
    }
}