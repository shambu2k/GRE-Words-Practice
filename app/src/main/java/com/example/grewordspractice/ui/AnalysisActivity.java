package com.example.grewordspractice.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.models.SavedWord;

import java.util.List;

import javax.inject.Inject;

public class AnalysisActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory factory;

    private AnalysisViewModel viewModel;

    TextView w, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        w = findViewById(R.id.totWrong_tv);
        list = findViewById(R.id.wronglist_tv);

        String analysisJson = getIntent().getExtras().getString("ANALYSIS");

        viewModel = new ViewModelProvider(this, factory).get(AnalysisViewModel.class);
        List<SavedWord> analysisList = viewModel.getAnalysisList(analysisJson);

        w.setText("Total Words Wrong: "+analysisList.size());
        StringBuilder str = new StringBuilder();
        for(int i =0 ; i< analysisList.size(); i++){
            str.append(analysisList.get(i).getWord()+" ");
        }
        list.setText("Wrong: "+str.toString());

    }


}
