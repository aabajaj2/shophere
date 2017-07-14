package com.example.anjanibajaj.shophere.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.anjanibajaj.shophere.Interfaces.ProductDao;
import com.example.anjanibajaj.shophere.model.ProductTable;

/**
 * Created by Anjani Bajaj on 7/14/2017.
 *
 */

@Database(entities = {ProductTable.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}