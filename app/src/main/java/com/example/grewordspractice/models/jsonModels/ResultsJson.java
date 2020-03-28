package com.example.grewordspractice.models.jsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsJson {

    @SerializedName("lexicalEntries")
    private List<LexicalEntriesJson> lexicalEntriesList;

    public List<LexicalEntriesJson> getLexicalEntriesList() {
        return lexicalEntriesList;
    }
}
