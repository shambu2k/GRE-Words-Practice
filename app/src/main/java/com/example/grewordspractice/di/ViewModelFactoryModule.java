package com.example.grewordspractice.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.grewordspractice.ui.AddNewViewModel;
import com.example.grewordspractice.ui.AnalysisViewModel;
import com.example.grewordspractice.ui.MainViewModel;
import com.example.grewordspractice.ui.PracticeSavedWordsViewModel;
import com.example.grewordspractice.ui.SavedWordsActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddNewViewModel.class)
    public abstract ViewModel bindAddNewViewModel(AddNewViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SavedWordsActivityViewModel.class)
    public abstract ViewModel bindSavedWordsActivityViewModel(SavedWordsActivityViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PracticeSavedWordsViewModel.class)
    public abstract ViewModel bindPracticeWordsActivityViewModel(PracticeSavedWordsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AnalysisViewModel.class)
    public abstract ViewModel bindAnalysisActivityViewModel(AnalysisViewModel viewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvider.Factory factory);
}
