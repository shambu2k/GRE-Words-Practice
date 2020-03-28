package com.example.grewordspractice.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WordRepository {

    private WordDao wordDao;

    public WordRepository(Application application){
        WordDatabase database = WordDatabase.getInstance(application);
        wordDao = database.wordDao();
    }

    public void insert(Word word){
        new InsertWordAsyncTask(wordDao).execute(word);
    }

    public LiveData<List<Word>>getAllWords(){ return wordDao.getAllWords(); }


    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao dao;

        public InsertWordAsyncTask(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.addWord(words[0]);
            return null;
        }
    }

}
