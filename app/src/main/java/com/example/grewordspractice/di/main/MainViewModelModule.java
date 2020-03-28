package com.example.grewordspractice.di.main;

import androidx.lifecycle.ViewModel;

import com.example.grewordspractice.di.ViewModelKey;
import com.example.grewordspractice.ui.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
