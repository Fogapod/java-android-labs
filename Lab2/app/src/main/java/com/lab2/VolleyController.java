package com.lab2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleyController {
    private static VolleyController mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private VolleyController(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    static synchronized VolleyController getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new VolleyController(ctx);
        }

        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }

    void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}
