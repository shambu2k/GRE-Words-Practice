package com.example.grewordspractice.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import javax.inject.Inject;

public class AnalysisViewModel extends AndroidViewModel {

    @Inject
    public AnalysisViewModel(@NonNull Application application) {
        super(application);
    }
}
