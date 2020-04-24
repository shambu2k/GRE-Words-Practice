package com.example.grewordspractice.api;

import androidx.lifecycle.LiveData;

import com.example.grewordspractice.models.jsonModels.WordJson;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface WordsApi {
    @GET("entries/en-gb/{word}?strictMatch=false")
    Call<WordJson> getWordData(@Header("app_id") String app_id,
                                   @Header("app_key") String app_key,
                                   @Path("word") String word);
}
