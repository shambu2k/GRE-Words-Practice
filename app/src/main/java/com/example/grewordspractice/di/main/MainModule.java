package com.example.grewordspractice.di.main;

import com.example.grewordspractice.api.WordsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static WordsApi provideApi(Retrofit retrofit){
        return retrofit.create(WordsApi.class);
    }
}
