package com.example.grewordspractice;

import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefHelper {
    private static final String TAG = "SharedPrefHelper";
    private final SharedPreferences pref;

    @Inject
    public SharedPrefHelper(SharedPreferences pref) {
        Log.d(TAG, "SharedPrefHelper: InjECTED!!!!!");
        this.pref = pref;
    }

    public String getString(String KEY){
        return pref.getString(KEY, "");
    }
}
