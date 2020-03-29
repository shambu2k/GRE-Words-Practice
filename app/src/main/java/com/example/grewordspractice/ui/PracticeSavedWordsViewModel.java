package com.example.grewordspractice.ui;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grewordspractice.AsyncCallBack;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.WordRepository;
import com.example.grewordspractice.utils.DateToMinHours;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class PracticeSavedWordsViewModel extends AndroidViewModel {

    private static final String TAG = "PracticeSavedWordsViewM";
    private WordRepository wordRepository;
    private List<SavedWord> allSavedWords = new ArrayList<>();

    @Inject
    public PracticeSavedWordsViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    public void insertSavedWord(SavedWord savedWord) { wordRepository.insertSavedWord(savedWord);}


    public void deleteSavedWord(SavedWord word){
        wordRepository.deleteSavedWord(word);
    }


    public List<SavedWord> getPracticeWords() {
        List<SavedWord> savedWordList =  allSavedWords;
        List<SavedWord> practiceList = new ArrayList<>();
        Date date = new Date();
        for(int i =0; i< savedWordList.size(); i++){
            if(savedWordList.get(i).getMarkedDate()!=null){
                if(!savedWordList.get(i).isShowInTenMin() && !savedWordList.get(i).isShowNextDay() && !savedWordList.get(i).isShowNextWeek()){
                    Log.d(TAG, "getPracticeWords: TimeDiff: "+
                            DateToMinHours.getMinutes(date, savedWordList.get(i).getMarkedDate())+" showin10Min "+savedWordList.get(i).isShowInTenMin()+
                            " showNextDay "+savedWordList.get(i).isShowNextDay());
                    practiceList.add(savedWordList.get(i));
                } else if(savedWordList.get(i).isShowInTenMin() && !savedWordList.get(i).isShowNextDay() && !savedWordList.get(i).isShowNextWeek() && DateToMinHours.getMinutes(date, savedWordList.get(i).getMarkedDate()) <= 10){
                    Log.d(TAG, "getPracticeWords: TimeDiff: "+
                            DateToMinHours.getMinutes(date, savedWordList.get(i).getMarkedDate())+" showin10Min "+savedWordList.get(i).isShowInTenMin()+
                            " showNextDay "+savedWordList.get(i).isShowNextDay());
                    practiceList.add(savedWordList.get(i));
                } else if(!savedWordList.get(i).isShowInTenMin() && savedWordList.get(i).isShowNextDay() && !savedWordList.get(i).isShowNextWeek() && DateToMinHours.getHour(date, savedWordList.get(i).getMarkedDate()) >= 24){
                    Log.d(TAG, "getPracticeWords: TimeDiff: "+
                            DateToMinHours.getMinutes(date, savedWordList.get(i).getMarkedDate())+" showin10Min "+savedWordList.get(i).isShowInTenMin()+
                            " showNextDay "+savedWordList.get(i).isShowNextDay());
                    practiceList.add(savedWordList.get(i));
                } else if(!savedWordList.get(i).isShowInTenMin() && !savedWordList.get(i).isShowNextDay() && savedWordList.get(i).isShowNextWeek() && DateToMinHours.getDays(date, savedWordList.get(i).getMarkedDate()) >= 7){
                    Log.d(TAG, "getPracticeWords: TimeDiff: indays "+
                            DateToMinHours.getDays(date, savedWordList.get(i).getMarkedDate())+" showin10Min "+savedWordList.get(i).isShowInTenMin()+
                            " showNextDay "+savedWordList.get(i).isShowNextDay());
                    practiceList.add(savedWordList.get(i));
                } else {
                    Log.d(TAG, "getPracticeWords: TimeDiff in Hour: "+
                            DateToMinHours.getHour(date, savedWordList.get(i).getMarkedDate())+" showin10Min "+savedWordList.get(i).isShowInTenMin()+
                            " showNextDay "+savedWordList.get(i).isShowNextDay());
                }
            } else {
                practiceList.add(savedWordList.get(i));
            }

        }
        return practiceList;
    }

    public GetData getDataTask(AsyncCallBack listener){
        return new GetData(listener);
    }

    public class GetData extends AsyncTask<Void, Void, List<SavedWord>>{

        AsyncCallBack listener;

        public GetData(AsyncCallBack listener) {
            this.listener = listener;
        }

        @Override
        protected List<SavedWord> doInBackground(Void... voids) {
            allSavedWords = wordRepository.getSavedWordDao().getAllSavedWords();
            return null;
        }

        @Override
        protected void onPostExecute(List<SavedWord> savedWords) {
            listener.getPracticeDataFromVM();
        }
    }
}
