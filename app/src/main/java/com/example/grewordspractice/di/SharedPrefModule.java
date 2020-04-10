package com.example.grewordspractice.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.grewordspractice.SharedPrefHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.grewordspractice.utils.Constants.PREF_FILE_NAME;

@Module
public class SharedPrefModule {

    @Singleton
    @Provides
    SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    SharedPrefHelper provideSharedPrefHelper(SharedPreferences pref) {
        return new SharedPrefHelper(pref);
    }

}
