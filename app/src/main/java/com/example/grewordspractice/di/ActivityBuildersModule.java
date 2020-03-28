package com.example.grewordspractice.di;

import com.example.grewordspractice.di.main.MainModule;
import com.example.grewordspractice.di.main.MainViewModelModule;
import com.example.grewordspractice.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeAuthActivity();
}
