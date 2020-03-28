package com.example.grewordspractice.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWord(Word word);

    @Query("SELECT * FROM saved_words")
    LiveData<List<Word>> getAllWords();
}
