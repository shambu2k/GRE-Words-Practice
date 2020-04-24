package com.example.grewordspractice.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.grewordspractice.models.SavedWord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class AnalysisViewModel extends AndroidViewModel {

    @Inject
    public AnalysisViewModel(@NonNull Application application) {
        super(application);
    }

    public List<SavedWord> getAnalysisList(String json){
        Type listType = new TypeToken<List<SavedWord>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, listType);
    }
}
