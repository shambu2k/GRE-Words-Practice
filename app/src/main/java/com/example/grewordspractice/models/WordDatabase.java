package com.example.grewordspractice.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.grewordspractice.utils.Converters;

@Database(entities = {Word.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase instance;

    public abstract WordDao wordDao();

    public static synchronized WordDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WordDatabase.class, "word_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
