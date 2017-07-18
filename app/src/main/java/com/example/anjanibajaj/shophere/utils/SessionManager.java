package com.example.anjanibajaj.shophere.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String IS_LOGGED_IN = "isLoggedin";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String IS_PID_THERE = "check";
    public static final String PIDLIST = "pidList";

    private static Set<String> pidList;

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
//        Toast.makeText(context.getApplicationContext(), "Session set for "+ email, Toast.LENGTH_SHORT).show();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * user is not logged in redirect him to Login Activity
      Closing all the Activities
      Add new Flag to start new Activity ,Staring Login Activity
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    // Get Login State
    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return user;
    }

     /*After logout redirect user to Main Activity
     Closing all the Activities
     Add new Flag to start new Activity
     Staring Index Activity*/
    public void logoutUser() {
        editor.clear();                     // Clearing all data from Shared Preferences
        editor.commit();
        context.startActivity(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }



    public void addTocart(Integer pid) {
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
        editor.commit();                                        // commit changes
        Toast.makeText(context, "Added to list: "+ pidList.size(), Toast.LENGTH_LONG).show();
    }

    public HashMap<String, Set<String>> getProductDetails() {
        HashMap<String, Set<String>> pdetails = new HashMap<>();
        pdetails.put(PIDLIST, sharedPreferences.getStringSet(PIDLIST, null));
        return pdetails;
    }

    public void clearCart(){
        Log.d("Before clearing", String.valueOf(pidList.size()));
        sharedPreferences = context.getSharedPreferences(PIDLIST, 0);
        pidList.clear();
    }
}