package com.example.valutaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String logTag = "MainActivity";
    Spinner spinnerFrom;
    Spinner spinnerTo;
    TextView tw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataInterface(this).getData();
        List<String> aList = new ArrayList<String>();
        aList.addAll(new SharedPrefInterface(this).getBaseCur());
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,aList);
        Collections.sort(aList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerTo = (Spinner) findViewById(R.id.to_cur);
        spinnerFrom = (Spinner) findViewById(R.id.from_cur);
        spinnerTo.setAdapter(spinnerArrayAdapter);
        spinnerFrom.setAdapter(spinnerArrayAdapter);
        tw = findViewById(R.id.twUt);
    }

    public void convert(View view) {
        EditText editText=findViewById(R. id. ammount);
        String stringAmmount = editText. getText(). toString();
        if (!"". equals(stringAmmount)){
            double value=Double. parseDouble(stringAmmount);
            double c1 = new SharedPrefInterface(this).getValue(spinnerFrom.getSelectedItem().toString());
            double c2 = new SharedPrefInterface(this).getValue(spinnerTo.getSelectedItem().toString());
            double newVal = (c1/c2)*value;
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();
            tw.setText(String.format("%.2f %s = %.2f %s", value,from,newVal,to));
        }
    }

    public void toImg(View view) {
        Intent intent = new Intent(MainActivity.this, ImgActivity.class);
        startActivity(intent);
    }
}