package com.example.grewordspractice;

import com.example.grewordspractice.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    // TODO: Save data from new Word [x]
    // TODO: Add Practice from SaveMode
    // TODO: Add Analysis Screen; [x]
    // TODO: Add SavedWordsListActivity [x]

}
