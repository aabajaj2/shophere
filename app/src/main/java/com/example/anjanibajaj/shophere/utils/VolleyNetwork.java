package com.example.anjanibajaj.shophere.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Anjani Bajaj on 7/5/2017.
 * This is a singleton class for the network connection of the app
 * Adds a new request to the requestQueue and sends request to the node.js server
 */

public class VolleyNetwork {
    private static VolleyNetwork mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleyNetwork(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyNetwork getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyNetwork(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}