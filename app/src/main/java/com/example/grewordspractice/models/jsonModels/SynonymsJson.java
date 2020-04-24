package com.example.grewordspractice.models.jsonModels;

import com.google.gson.annotations.SerializedName;

public class SynonymsJson {

    @SerializedName("text")
    private String synonyms;

    public String getSynonyms() {
        return synonyms;
    }
}
