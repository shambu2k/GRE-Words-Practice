package com.example.grewordspractice.models;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grewordspractice.SharedPrefHelper;
import com.example.grewordspractice.api.WordsApi;
import com.example.grewordspractice.models.jsonModels.WordJson;
import com.example.grewordspractice.utils.RandomNumberListGenerator;
import com.example.grewordspractice.utils.SeenListIndexes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.grewordspractice.utils.Constants.RANDOM_WORD;
import static com.example.grewordspractice.utils.Constants.SP_SEEN_LIST_KEY;

public class WordRepository {

    private static final String TAG = "WordRepository";

    private WordDao wordDao;
    private SavedWordDao savedWordDao;

    private WordJson wordJson;
    private Word word;
    private Application application;
    private ProgressDialog progressDialog;

    @Inject
    SharedPrefHelper prefHelper;

    public WordRepository(Application application){
        this.application = application;
        WordDatabase database = WordDatabase.getInstance(application);
        wordDao = database.wordDao();
        savedWordDao = database.savedWordDao();
    }

    public WordDao getWordDao() {
        return wordDao;
    }

    public SavedWordDao getSavedWordDao() {
        return savedWordDao;
    }

    public void insertWord(Word word){
        new InsertWordAsyncTask(wordDao).execute(word);
    }

    public void insertSavedWord(SavedWord savedWord) {
        new InsertSavedWordAsyncTask(savedWordDao).execute(savedWord);
    }

    public LiveData<List<SavedWord>> getAllLiveSavedWords() {
        return savedWordDao.getAllLiveSavedWords();
    }

    public void deleteWord(Word word) {
        new DeleteWordAsyncTask(wordDao).execute(word);
    }

    public void deleteSavedWord(SavedWord word) { new DeleteSavedWordAsyncTask(savedWordDao).execute(word); }

    public LiveData<List<Word>> getAllWords(){ return wordDao.getAllWords(); }

    public void deleteAllWords() { new DeleteAllWordsAsyncTask(wordDao).execute();}

    public void deleteAllSavedWords() { new DeleteAllSavedWordsAsyncTask(savedWordDao).execute(); }

    public GetSomeWordsTask fetchRandomWords(WordsApi wordsApi){
        return new GetSomeWordsTask(wordsApi);
    }

    public MutableLiveData<WordJson> getNewWord(WordsApi wordsApi, String word_str, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting data..");
        progressDialog.show();

        MutableLiveData<WordJson> mutableWordJson = new MutableLiveData<>();
        Call<WordJson> call = wordsApi.getWordData("df391a54", "08fc8169d6b54e2557c26932e4cf55a1", word_str);
        call.enqueue(new Callback<WordJson>() {
            @Override
            public void onResponse(Call<WordJson> call, Response<WordJson> response) {
                if (response.body() != null) {
                    if(response.isSuccessful()){
                        mutableWordJson.setValue(response.body());
                    }
                } else {
                    Toast.makeText(application, "Api returned null for " + word_str, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WordJson> call, Throwable t) {
                mutableWordJson.setValue(null);
                progressDialog.dismiss();
            }
        });
        return mutableWordJson;
    }

   /* public MutableLiveData<List<SavedWord>> getWeakWords(){
        MutableLiveData<List<SavedWord>> weakList = new MutableLiveData<>();


    } */

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

    private static class InsertSavedWordAsyncTask extends AsyncTask<SavedWord, Void, Void> {

        private SavedWordDao dao;

        public InsertSavedWordAsyncTask(SavedWordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SavedWord... savedWords) {
            dao.saveWord(savedWords[0]);
            return null;
        }
    }

    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void>{

        private WordDao dao;

        public DeleteWordAsyncTask(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.deleteWord(words[0]);
            return null;
        }
    }

    private static class DeleteSavedWordAsyncTask extends AsyncTask<SavedWord, Void, Void>{

        private SavedWordDao dao;

        public DeleteSavedWordAsyncTask(SavedWordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SavedWord... savedWords) {
            dao.deleteSavedWord(savedWords[0]);
            return null;
        }
    }

    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void>{

        private WordDao dao;

        public DeleteAllWordsAsyncTask(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllWords();
            return null;
        }
    }

    private static class DeleteAllSavedWordsAsyncTask extends AsyncTask<Void, Void, Void>{

        private SavedWordDao dao;

        public DeleteAllSavedWordsAsyncTask(SavedWordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllSavedWords();
            return null;
        }
    }

    public class GetSomeWordsTask extends AsyncTask<Integer, Void, Void> {

        WordsApi wordsApi;
        List<Integer> nonRepNums = new ArrayList<>();
        List<Integer> seenList = new ArrayList<>();
        SeenListIndexes seenListIndexes;
        SharedPreferences preferences = application.getSharedPreferences("GREWords", 0);

        public GetSomeWordsTask(WordsApi wordsApi) {
            this.wordsApi = wordsApi;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            nonRepNums = RandomNumberListGenerator.generateNumbers(integers[0]);
            if(preferences.getString(SP_SEEN_LIST_KEY, "").equals("")) {
                Log.d(TAG, "doInBackground: Just inside: nonRepnums.size " + nonRepNums.size() + "SeenList size " + seenList.size());
                for (int j = 0; j < nonRepNums.size(); j++) {
                        Call<WordJson> call2 = wordsApi.getWordData("df391a54", "08fc8169d6b54e2557c26932e4cf55a1", RANDOM_WORD.get(nonRepNums.get(j)));
                        Log.d(TAG, "doInBackground: index of Word = " + RANDOM_WORD.get(nonRepNums.get(j)));
                        int finalJ = j;
                        call2.enqueue(new Callback<WordJson>() {
                            @Override
                            public void onResponse(Call<WordJson> call, Response<WordJson> response) {
                                Log.d(TAG, "onResponse of def: " + response.body());
                                if (response.body() != null) {
                                    wordJson = response.body();
                                    if (wordJson.getError() == null) {
                                        Log.d(TAG, "onResponse: Word from Response " + wordJson.getWord());
                                        if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() != null) {
                                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getListDef().get(0), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getSynonymsList().get(0).getSynonyms());
                                            insertWord(word);
                                        } else if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() == null) {
                                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getListDef().get(0), "Not found in API");
                                            insertWord(word);
                                        } else if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() == null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() == null) {

                                            Toast.makeText(application, wordJson.getWord() + " def: null synonyms: null", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(application, wordJson.getWord() + " : Word Not recognized", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                } else {
                                    Toast.makeText(application, "Api returned null for " + RANDOM_WORD.get(nonRepNums.get(finalJ)), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WordJson> call, Throwable t) {

                            }
                        });
                }
            } else {
                seenListIndexes = new SeenListIndexes();
                seenList = seenListIndexes.getFromSP(application);
                Log.d(TAG, "doInBackground: Just inside: nonRepnums.size " + nonRepNums.size() + "SeenList size " + seenList.size());
                for (int j = 0; j < nonRepNums.size(); j++) {
                    if (Collections.frequency(seenList, nonRepNums.get(j)) == 0) {
                        Call<WordJson> call2 = wordsApi.getWordData("df391a54", "08fc8169d6b54e2557c26932e4cf55a1", RANDOM_WORD.get(nonRepNums.get(j)));
                        Log.d(TAG, "doInBackground: index of Word = " + RANDOM_WORD.get(nonRepNums.get(j)));
                        int finalJ = j;
                        call2.enqueue(new Callback<WordJson>() {
                            @Override
                            public void onResponse(Call<WordJson> call, Response<WordJson> response) {
                                Log.d(TAG, "onResponse of def: " + response.body());
                                if (response.body() != null) {
                                    wordJson = response.body();

                                    if (wordJson.getError() == null) {
                                        Log.d(TAG, "onResponse: Word from Response " + wordJson.getWord());
                                        if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() != null) {
                                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getListDef().get(0), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getSynonymsList().get(0).getSynonyms());
                                            insertWord(word);
                                        } else if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() == null) {
                                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getListDef().get(0), "Not found in API");
                                            insertWord(word);
                                        } else if (wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef() == null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getSynonymsList() == null) {

                                            Toast.makeText(application, wordJson.getWord() + " def: null synonyms: null", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(application, wordJson.getWord() + " : Word Not recognized", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                } else {
                                    Toast.makeText(application, "Api returned null for " + RANDOM_WORD.get(nonRepNums.get(finalJ)), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WordJson> call, Throwable t) {

                            }
                        });
                    } else {
                        Log.d(TAG, "doInBackground: Omitted Repeating Word");
                    }
                }
            }
            return null;
        }
    }
}

/*
if (wordJson.getError() == null) {
                        Log.d(TAG, "onResponse: NewWord from Response " + wordJson.getWord());
                        if (wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getSynonymsList() != null) {
                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                    getSensesList().get(0).getListDef().get(0), wordJson.getResultsJsonList().get(0).
                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                    getSensesList().get(0).getSynonymsList().get(0).getSynonyms());
                            insertSavedWord(new SavedWord(word.getWord(), word.getDefinitions(),
                                    word.getSynonym(), false, false,
                                    false, null, 0,0));
                        } else if (wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getSynonymsList() == null) {
                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                    getSensesList().get(0).getListDef().get(0), "Not found in API");
                            insertSavedWord(new SavedWord(word.getWord(), word.getDefinitions(),
                                    word.getSynonym(), false, false,
                                    false, null, 0,0));
                        } else if (wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList() != null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getListDef() == null && wordJson.getResultsJsonList().get(0).
                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                getSensesList().get(0).getSynonymsList() == null) {

                            Toast.makeText(application, wordJson.getWord() + " def: null synonyms: null", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(application, wordJson.getWord() + " : Word Not recognized", Toast.LENGTH_LONG).show();
                        }

                    }
 */