package com.example.anjanibajaj.shophere.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Anjani Bajaj on 7/5/2017.
 * This is a class for a User
 */

public class User{
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
