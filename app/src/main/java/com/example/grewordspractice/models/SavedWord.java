package com.example.grewordspractice.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "saved_words")
public class SavedWord {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String definitions;

    private String synonym;

    private boolean showInTenMin;

    private boolean showNextWeek;

    private boolean showNextDay;

    private Date markedDate;

    private int totCorrect;

    private int totTaken;


    public SavedWord() {
    }

    public SavedWord(String word, String definitions, String synonym, boolean showInTenMin, boolean showNextWeek,
                     boolean showNextDay, Date markedDate, int totCorrect, int totTaken) {
        this.word = word;
        this.definitions = definitions;
        this.synonym = synonym;
        this.showInTenMin = showInTenMin;
        this.showNextWeek = showNextWeek;
        this.showNextDay = showNextDay;
        this.markedDate = markedDate;
        this.totCorrect = totCorrect;
        this.totTaken = totTaken;
    }

    public SavedWord(int id, String word, String definitions, String synonym, boolean showInTenMin,
                     boolean showNextWeek, boolean showNextDay, Date markedDate, int totCorrect, int totTaken) {
        this.id = id;
        this.word = word;
        this.definitions = definitions;
        this.synonym = synonym;
        this.showInTenMin = showInTenMin;
        this.showNextWeek = showNextWeek;
        this.showNextDay = showNextDay;
        this.markedDate = markedDate;
        this.totCorrect = totCorrect;
        this.totTaken = totTaken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public boolean isShowInTenMin() {
        return showInTenMin;
    }

    public void setShowInTenMin(boolean showInTenMin) {
        this.showInTenMin = showInTenMin;
    }

    public boolean isShowNextWeek() {
        return showNextWeek;
    }

    public void setShowNextWeek(boolean showNextWeek) {
        this.showNextWeek = showNextWeek;
    }

    public boolean isShowNextDay() {
        return showNextDay;
    }

    public void setShowNextDay(boolean showNextDay) {
        this.showNextDay = showNextDay;
    }

    public Date getMarkedDate() {
        return markedDate;
    }

    public void setMarkedDate(Date markedDate) {
        this.markedDate = markedDate;
    }

    public int getTotCorrect() {
        return totCorrect;
    }

    public void setTotCorrect(int totCorrect) {
        this.totCorrect = totCorrect;
    }

    public int getTotTaken() {
        return totTaken;
    }

    public void setTotTaken(int totTaken) {
        this.totTaken = totTaken;
    }
}
