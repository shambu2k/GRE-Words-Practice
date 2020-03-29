package com.example.grewordspractice.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.WordRepository;

import java.util.List;

import javax.inject.Inject;

public class SavedWordsActivityViewModel extends AndroidViewModel {

    private WordRepository wordRepository;

    @Inject
    public SavedWordsActivityViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    public LiveData<List<SavedWord>> getAllLiveSavedWords(){
        return wordRepository.getAllLiveSavedWords();
    }
}
