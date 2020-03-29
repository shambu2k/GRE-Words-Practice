package com.example.grewordspractice.ui;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.grewordspractice.api.WordsApi;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.Word;
import com.example.grewordspractice.models.WordRepository;
import com.example.grewordspractice.models.jsonModels.WordJson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private WordRepository wordRepository;
    private final WordsApi wordsApi;
    private Word word;
    private List<String> words = new ArrayList<>();
    private LiveData<List<Word>> allWords;
    private WordJson wordJson;



    @Inject
    public MainViewModel(Application application, WordsApi wordsApi) {
        super(application);
        wordRepository = new WordRepository(application);
        this.wordsApi = wordsApi;
        allWords = wordRepository.getAllWords();
    }

    public void insertWord(Word word){
        wordRepository.insertWord(word);
    }

    public void insertSavedWord(SavedWord savedWord) { wordRepository.insertSavedWord(savedWord);}

    public void delete(Word word) { wordRepository.deleteWord(word);}

    public void deleteAll() {
        wordRepository.deleteAllWords();
    }

    public LiveData<List<Word>> getAllWords(Activity activity){
        return allWords;
    }

    public void fetchRandomData(int numOfnum){
        wordRepository.fetchRandomWords(wordsApi).execute(numOfnum);
    }
}