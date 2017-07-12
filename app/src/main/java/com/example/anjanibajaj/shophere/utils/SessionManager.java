package com.example.anjanibajaj.shophere.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.anjanibajaj.shophere.LoginActivity;
import com.example.anjanibajaj.shophere.MainActivity;

import java.util.HashMap;

/**
 * Created by Anjani Bajaj on 7/12/2017.
 * Functions helping session maintenance
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String LOGIN = "login";
    public static final String IS_LOGGED_IN = "isLoggedin";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public SessionManager(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(LOGIN, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String email, String password) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGGED_IN, true);

        // Storing username in pref
        editor.putString(EMAIL, email);

        // Storing password in pref
        editor.putString(PASSWORD, password);

        // commit changes
        editor.commit();

//        Toast.makeText(context.getApplicationContext(), "Session set for "+ email, Toast.LENGTH_SHORT).show();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            // Closing all the Activities
            // Add new Flag to start new Activity
            // Staring Login Activity
            context.startActivity(new Intent(context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    // Get Login State
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        // user password
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));

        // return user
        return user;
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        // Closing all the Activities
        // Add new Flag to start new Activity

        // Staring Login Activity
        context.startActivity(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
