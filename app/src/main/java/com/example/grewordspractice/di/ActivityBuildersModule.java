package com.example.grewordspractice.di;


import com.example.grewordspractice.ui.AddNewWordActivity;
import com.example.grewordspractice.ui.AnalysisActivity;
import com.example.grewordspractice.ui.MainActivity;
import com.example.grewordspractice.ui.PracticeSavedWordsActivity;
import com.example.grewordspractice.ui.SavedWordsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract AddNewWordActivity contributeAddNewActivity();

    @ContributesAndroidInjector
    abstract SavedWordsActivity contributeSavedWordsActivity();

    @ContributesAndroidInjector
    abstract PracticeSavedWordsActivity contributePracticeWordsActivity();

    @ContributesAndroidInjector
    abstract AnalysisActivity contributeAnalysisActivity();


}
