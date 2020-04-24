package com.example.grewordspractice.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.grewordspractice.utils.Constants.SP_SEEN_LIST_KEY;

public class SeenListIndexes {
    private List<Integer> seenList = new ArrayList<>();

    public SeenListIndexes() {
    }

    public SeenListIndexes(List<Integer> seenList) {
        this.seenList = seenList;
    }

    public List<Integer> getSeenList() {
        return seenList;
    }

    public void setSeenList(List<Integer> seenList) {
        this.seenList = seenList;
    }

    public void addToSeenList(int index){
        seenList.add(index);
    }

    public void saveInSP(Application application){
        SharedPreferences preferences = application.getSharedPreferences("GREWords", 0);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(SP_SEEN_LIST_KEY, gson.toJson(seenList));
        editor.commit();
    }

    public List<Integer> getFromSP(Application application){
        SharedPreferences preferences = application.getSharedPreferences("GREWords", 0);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> list = gson.fromJson(preferences.getString(SP_SEEN_LIST_KEY, "[0]"),listType);
        return list;
    }
}
