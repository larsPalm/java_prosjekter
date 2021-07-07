package com.example.valutaapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.LinkedList;

public class ImgViewModel extends AndroidViewModel {
    public ImgViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getCurrentImg() {
        return DataInterface.livedata;
    }
}
