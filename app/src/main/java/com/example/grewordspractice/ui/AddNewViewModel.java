package com.example.grewordspractice.ui;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.grewordspractice.api.WordsApi;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.Word;
import com.example.grewordspractice.models.WordRepository;
import com.example.grewordspractice.models.jsonModels.WordJson;

import javax.inject.Inject;

public class AddNewViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private final WordsApi wordsApi;
    private Application application;

    @Inject
    public AddNewViewModel(@NonNull Application application, WordsApi wordsApi) {
        super(application);
        wordRepository = new WordRepository(application);
        this.wordsApi = wordsApi;
        this.application = application;
    }

    public void insertSavedWord(SavedWord savedWord) { wordRepository.insertSavedWord(savedWord);}

    public MutableLiveData<WordJson> getNewWord(String word, Context context){
        return wordRepository.getNewWord(wordsApi, word, context);
    }
}
