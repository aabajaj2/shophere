package com.example.anjanibajaj.shophere.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.example.anjanibajaj.shophere.LoginActivity;
import com.example.anjanibajaj.shophere.MainActivity;
import com.example.anjanibajaj.shophere.model.Product;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 * Functions helping session maintenance
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String LOGIN = "login";
    public static final String CART = "cart";
    public static final String WISHLIST = "wishlist";
    private static final String IS_LOGGED_IN = "isLoggedin";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String IS_PID_THERE = "check";
    private static final String IS_WPID_THERE = "check";
    public static final String PIDLIST = "pidList";
    public static final String WPIDLIST = "wpidList";

    private static Set<String> pidList;
    private static Set<String> wpidList;


    public SessionManager(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(LOGIN, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String email, String password) {
        editor.putBoolean(IS_LOGGED_IN, true);  // Storing login value as TRUE
        editor.putString(EMAIL, email);         // Storing username in pref
        editor.putString(PASSWORD, password);   // Storing password in pref
        editor.commit();                        // commit changes
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return user;
    }

    public void logoutUser() {
        editor.clear();         // Clearing all data from Shared Preferences
        editor.commit();
        clearCart();
        context.startActivity(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void addTocart(Integer pid) {
//        sharedPreferences = context.getSharedPreferences(CART, 0);
        editor.putBoolean(IS_PID_THERE, true);          // Storing PID  value as TRUE
        pidList = sharedPreferences.getStringSet(PIDLIST, null);
        if(pidList  == null){
            pidList = new TreeSet<>();
            pidList.add(String.valueOf(pid));               // Storing pid in a set of Pid
        } else {
            pidList = sharedPreferences.getStringSet(PIDLIST, null);
            pidList.add(String.valueOf(pid));               // Storing pid in a set of Pid
        }
        editor.putStringSet(PIDLIST, pidList);
        editor.apply();                                     // commit changes
        Toast.makeText(context, "Added to list, size: "+ pidList.size(), Toast.LENGTH_LONG).show();
    }

    public HashMap<String, Set<String>> getProductDetails(String list) {
        HashMap<String, Set<String>> productDetails = new HashMap<>();
        productDetails.put(list, sharedPreferences.getStringSet(list, null));
        return productDetails;
    }

    public void clearCart(){
        pidList = sharedPreferences.getStringSet(PIDLIST, null);
        if(pidList!=null){
            pidList.clear();
        }else {
            Toast.makeText(context.getApplicationContext(), "Your cart is empty!", Toast.LENGTH_LONG).show();
        }
    }

    public void addToWishList(Integer pid) {
        wpidList = sharedPreferences.getStringSet(WPIDLIST, null);

        if(wpidList  == null){
            wpidList = new TreeSet<>();
            wpidList.add(String.valueOf(pid));// Storing pid in a new TreeSet of Pid

        } else {
            wpidList = sharedPreferences.getStringSet(WPIDLIST, null);
            wpidList.add(String.valueOf(pid));               // Storing pid in the old set of Pids
        }

        editor.putStringSet(WPIDLIST, wpidList);
        editor.commit();                                     // commit changes
        Toast.makeText(context, "Added to wishList, size: "+ wpidList.size(), Toast.LENGTH_SHORT).show();
    }

    public void clearWishList(){
        pidList = sharedPreferences.getStringSet(WPIDLIST, null);
        if(wpidList!=null){
            wpidList.clear();
        }else {
            Toast.makeText(context.getApplicationContext(), "Your wishlist is empty!", Toast.LENGTH_LONG).show();
        }
    }

    public void onRemoveClicked(Integer pid){
        pidList = sharedPreferences.getStringSet(PIDLIST, null);
        assert pidList != null;
        pidList.remove(String.valueOf(pid));
    }
}