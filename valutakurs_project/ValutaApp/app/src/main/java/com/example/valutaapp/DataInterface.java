package com.example.valutaapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Map;

public class DataInterface {
    Context context;
    ServerInterface si;
    SharedPrefInterface spi;
    private static final String TAG = "DataInterface";
    public static MutableLiveData<String> livedata = new MutableLiveData<String>();

    public DataInterface(Context context) {
        this.context = context;
        si = new ServerInterface(String.format(Locale.getDefault(), "http://%s:%s/", this.context.getResources().getString(R.string.server_ip), this.context.getResources().getString(R.string.server_port)), this.context);
        spi = new SharedPrefInterface(this.context);
    }

    public void getData(){
        Log.d(TAG,new SharedPrefInterface(context).getDate());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String oldDate = new SharedPrefInterface(context).getDate();
        String currentDate = dtf.format(now);
        if(oldDate.compareTo(currentDate)<0){
            si.volleyGet("latestValues/", new Callback() {
                @Override
                public void callback(Object data) {
                    Log.d(TAG, String.valueOf(data));
                    if (data == null) {
                        Log.d(TAG, "is null");
                        return;
                    }
                    if (data instanceof String) {
                        //checks if the response from the server is empty or not
                        if (String.valueOf(data).equals("")) {
                            Log.d(TAG, "return was empty, data length =" + String.valueOf(data).length() + ", end");
                            Log.d(TAG, "return was empty, data =" + String.valueOf(data) + ", end");
                        }
                        else{
                            try{
                                JSONObject obj = new JSONObject((String) data);
                                spi.putData((String)data,currentDate);
                                spi.putDate(currentDate);
                            }
                            catch (Throwable t){return;}
                        }
                    }
                }
            });
        }
    }
    public void getImg(String from,String to){
        Log.d(TAG,"from_cur "+from+",to_cur:"+to);
        si.volleyGet("compareImg2/"+from+"/"+to, new Callback() {
            @Override
            public void callback(Object data) {
                if(data instanceof String) {
                    spi.storeImg((String)data);
                } else {
                    Toast.makeText(context, "Invalid response from server", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void getStoredImg(){
        livedata.setValue(spi.getImg());
    }
}
