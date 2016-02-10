package com.example.dishantkaushik.movieapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class vollySingleton {
    private RequestQueue requestQueue;
    private static vollySingleton instance;

    public vollySingleton(Context context){
        requestQueue= Volley.newRequestQueue(context);
    }
    public static vollySingleton getInstance(Context context){
        if (instance==null){
           instance=new vollySingleton(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
