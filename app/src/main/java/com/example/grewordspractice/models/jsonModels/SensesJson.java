package com.example.grewordspractice.models.jsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SensesJson {

    @SerializedName("definitions")
    private List<String> listDef;

    @SerializedName("synonyms")
    private List<SynonymsJson> synonymsList;

    public List<String> getListDef() {
        return listDef;
    }

    public List<SynonymsJson> getSynonymsList() {
        return synonymsList;
    }
}
