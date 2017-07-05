package com.example.anjanibajaj.shophere.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Anjani Bajaj on 7/5/2017.
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

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}