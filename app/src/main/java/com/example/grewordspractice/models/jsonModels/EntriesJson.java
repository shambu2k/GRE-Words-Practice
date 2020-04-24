package com.example.grewordspractice.models.jsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntriesJson {

    @SerializedName("senses")
    private List<SensesJson> sensesList;

    public List<SensesJson> getSensesList() {
        return sensesList;
    }
}
