package com.example.anjanibajaj.shophere.pojofiles;

import android.databinding.BaseObservable;

/**
 * Created by Anjani Bajaj on 7/3/2017.
 */

public class User extends BaseObservable{
    private final String firstName;
    private final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
}
