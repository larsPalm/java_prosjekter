package com.example.valutaapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class ServerInterface {
    private final String baseUrl;
    private final Context context;
    private static final String TAG = "ServerInterface";


    public ServerInterface(String url, Context context) {
        this.baseUrl = url;
        this.context = context;
    }

    public void volleyGet(String endPoint, Callback success) {
        String url = baseUrl + endPoint;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.callback(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Got error when querying on: " + url + " error: " + error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(sr);
    }
    public void volleyPost(String endPoint, Map<String, String> params, Callback success) {
        String url = baseUrl + endPoint;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.callback(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Got error when querying on: " + url + " error: " + error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(sr);
    }
}
