package com.example.grewordspractice.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> fromJsonDS(String defOrSyn){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(defOrSyn, type);
    }

    @TypeConverter
    public static String toJsonDS(List<String> defOrSyn){
        Gson gson = new Gson();
        return gson.toJson(defOrSyn);
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
