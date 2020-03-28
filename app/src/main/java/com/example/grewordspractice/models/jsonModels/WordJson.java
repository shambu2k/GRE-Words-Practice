package com.example.grewordspractice.models.jsonModels;

import com.example.grewordspractice.models.jsonModels.ResultsJson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordJson {

    @SerializedName("word")
    private String word;

    @SerializedName("results")
    private List<ResultsJson> resultsJsonList;

    @SerializedName("error")
    private String error;

    public String getWord() {
        return word;
    }

    public List<ResultsJson> getResultsJsonList() {
        return resultsJsonList;
    }

    public String getError() {
        return error;
    }
}
