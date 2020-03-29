package com.example.grewordspractice.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveWord(SavedWord word);

    @Delete
    void deleteSavedWord(SavedWord word);

    @Query("DELETE FROM saved_words")
    void deleteAllSavedWords();

    @Query("SELECT * FROM saved_words WHERE word LIKE :theword")
    List<SavedWord> getSavedWord(String theword);

    @Query("SELECT * FROM saved_words")
    List<SavedWord> getAllSavedWords();

    @Query("SELECT * FROM saved_words")
    LiveData<List<SavedWord>> getAllLiveSavedWords();

}
