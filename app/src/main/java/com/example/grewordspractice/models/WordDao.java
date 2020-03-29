package com.example.grewordspractice.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWord(Word word);

    @Query("SELECT * FROM temp_words")
    LiveData<List<Word>> getAllWords();

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM temp_words")
    void deleteAllWords();

    @Query("SELECT * FROM temp_words WHERE word LIKE :word")
    List<Word> getRepeatWords(String word);
}
