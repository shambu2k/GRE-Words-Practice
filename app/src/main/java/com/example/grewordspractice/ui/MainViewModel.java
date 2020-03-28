package com.example.grewordspractice.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.api.WordsApi;
import com.example.grewordspractice.models.Word;
import com.example.grewordspractice.models.WordRepository;
import com.example.grewordspractice.models.jsonModels.WordJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

import static com.example.grewordspractice.utils.Constants.BASE_RW_URL;
import static com.example.grewordspractice.utils.Constants.TASK_GETDEF;
import static com.example.grewordspractice.utils.Constants.TASK_GETWORD;


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

    public void insert(Word word){
        wordRepository.insert(word);
    }

    public void sampleData(){
        wordRepository.insert(new Word("experience", "practical contact with and observation of facts or events.", "incident"));
        wordRepository.insert(new Word("murder", "he unlawful premeditated killing of one human being by another.", "homicide"));
        wordRepository.insert(new Word("procession", "a number of people or vehicles moving forward in an orderly fashion, especially as part of a ceremony..", "parade"));
    }

    public WordsApi getOxData(){
        return wordsApi;
    }

    public LiveData<List<Word>> getAllWords(Activity activity){

        return allWords;
    }

    public GetSomeWordsTask fetchRandomData(){
        return new GetSomeWordsTask();
    }

    public class GetSomeWordsTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {

            if(integers[0]==TASK_GETWORD){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_RW_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

               RandWordApi service = retrofit.create(RandWordApi.class);

                try {
                    Call<String> call = service.getRandomWord();
                    Response<String> response = call.execute();
                    Log.d(TAG, "doInBackground: "+response.body().toString().substring(2, response.body().toString().length()-2));
                    words.add(response.body().toString().substring(2, response.body().toString().length()-2));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(integers[0]==TASK_GETDEF){
                Log.d(TAG, "doInBackground: doing GETDEF size of words "+words.size());
                if(words.size()!=0) {
                    for (int i = 0; i < 1; i++) {
                        Call<WordJson> call2 = getOxData().getWordData("26af18f6", "7e013e30eefe409c6f95b42c979b6fcf", words.get(0));

                        call2.enqueue(new Callback<WordJson>() {
                            @Override
                            public void onResponse(Call<WordJson> call, Response<WordJson> response) {
                                Log.d(TAG, "onResponse of def: " + response.body());
                                if(response.body()!=null) {
                                    wordJson = response.body();
                                    if (wordJson.getError() == null) {
                                        if(wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList()!=null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef()!=null){
                                            try {
                                                word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                        getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                        getSensesList().get(0).getListDef().get(0), wordJson.getResultsJsonList().get(0).
                                                        getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                        getSensesList().get(0).getSynonymsList().get(0).getSynonyms());
                                                insert(word);
                                            } catch (NullPointerException e){

                                            }
                                        } else if(wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList()==null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef()==null) {
                                            word = new Word(wordJson.getWord(), "Definition not found", "Not found in API");
                                            insert(word);
                                        } else if(wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList()==null && wordJson.getResultsJsonList().get(0).
                                                getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                getSensesList().get(0).getListDef()!=null){
                                            word = new Word(wordJson.getWord(), wordJson.getResultsJsonList().get(0).
                                                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                                                    getSensesList().get(0).getListDef().get(0), "Not found in API");
                                            insert(word);
                                        } else {

                                            Toast.makeText(getApplication(), wordJson.getWord()+" def: null synonyms: null", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                } else {
                                    Toast.makeText(getApplication(), "Api returned null", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WordJson> call, Throwable t) {

                            }
                        });
                    }
                }
            }
            return null;
        }
    }

    private interface RandWordApi {
        @GET("/word?number=1")
        Call<String> getRandomWord();
    }


}