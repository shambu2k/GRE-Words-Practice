package com.example.grewordspractice.models.jsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LexicalEntriesJson {

    @SerializedName("entries")
    private List<EntriesJson> entriesList;

    public List<EntriesJson> getEntriesList() {
        return entriesList;
    }
}
