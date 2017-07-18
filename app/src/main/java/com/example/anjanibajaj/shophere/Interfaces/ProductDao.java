package com.example.anjanibajaj.shophere.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.model.ProductTable;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/14/2017.
 *
 */

@Dao
public interface ProductDao {
    @Insert
    void insertMultipleRecords(ProductTable... products);

    @Insert
    void insertOnlySingleRecord(ProductTable product);

    @Query("SELECT * FROM ProductTable")
    List<ProductTable> fetchAllData();

    @Query("SELECT * FROM ProductTable WHERE pid =:pid")
    ProductTable getSingleRecord(int pid);

    @Delete
    void deleteProducts(ProductTable... productTables);

}
