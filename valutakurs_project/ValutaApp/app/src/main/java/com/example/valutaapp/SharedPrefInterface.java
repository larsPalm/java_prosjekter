package com.example.valutaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class SharedPrefInterface {
    Context context;
    SharedPreferences sharedPref;
    private static final String TAG = "SharedPrefInterface";

    public SharedPrefInterface(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(context.getString(R.string.preference_user_data), Context.MODE_PRIVATE);
    }
    public void putData(String data,String date){
        try{
            Log.d(TAG,data.toString());
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(data);
            JsonObject obj = element.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
            String key = "";
            for(Map.Entry<String, JsonElement> entry: entries) {
                Log.d(TAG,entry.getKey());
                key = entry.getKey();
            }
            JSONObject jsonObj = new JSONObject((String) data);
            HashMap<String,Double> hm =new Gson().fromJson(jsonObj.get(key).toString(), HashMap.class);
            Set<String> base_cur = new HashSet<String>();
            hm.forEach((k, v) -> base_cur.add(k));
            hm.forEach((k, v) -> putValue(k,v));
            putBaseCurs(base_cur);
            putDate(date);
        }
        catch (Exception e){}
    }

    public void putDate(String date){
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.d(TAG,"insert:"+date);
        editor.putString("dato", date);
        editor.apply();
    }

    private void putValue(String key,Double value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();
    }

    private void putBaseCurs(Set<String> names){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("baseCur", names);
        editor.apply();
    }

    public String getDate(){
        return sharedPref.getString("dato", "");
    }

    public double getValue(String key){
        return Double.parseDouble(sharedPref.getString(key, "0"));
    }

    public Set<String> getBaseCur(){
        return sharedPref.getStringSet("baseCur",null);
    }
}
