package com.example.valutaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.util.Base64;



public class ImgActivity extends AppCompatActivity {
    String TAG = "ImgActivity";
    Spinner spinnerFrom;
    Spinner spinnerTo;
    Activity activity;
    ImageView img;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        List<String> aList = new ArrayList<String>();
        aList.addAll(new SharedPrefInterface(this).getBaseCur());
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,aList);
        Collections.sort(aList);
        activity = this;
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerTo = (Spinner) findViewById(R.id.to_cur);
        spinnerFrom = (Spinner) findViewById(R.id.from_cur);
        spinnerTo.setAdapter(spinnerArrayAdapter);
        spinnerFrom.setAdapter(spinnerArrayAdapter);
        img = (ImageView) findViewById(R.id.img);
        viewModel = new ViewModelProvider(this).get(ImgViewModel.class);
        final Observer<String> imgObserver = new Observer<String>(){
            @Override
            public void onChanged(@Nullable final String imgString){
                byte[] decodedString = Base64.decode(imgString, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                img.setImageBitmap(decodedByte);
            }
        };
        ((ImgViewModel) viewModel).getCurrentImg().observe(this, imgObserver);
        new DataInterface(this).getStoredImg();
    }

    public void fetchImg(View view) {
        String v1 = spinnerFrom.getSelectedItem().toString();
        String v2 = spinnerTo.getSelectedItem().toString();
        new DataInterface(this).getImg(v1,v2);
        String urlString = "http://10.0.2.2:8080/compareImg2/"+v1+"/"+v2;
        Log.d(TAG,urlString);
        Log.d(TAG,new SharedPrefInterface(this).getImg());
        new DataInterface(this).getStoredImg();
        byte[] decodedString = Base64.decode(new SharedPrefInterface(this).getImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
    }

    public void toMain(View view) {
        Intent intent = new Intent(ImgActivity.this, MainActivity.class);
        startActivity(intent);
    }
}