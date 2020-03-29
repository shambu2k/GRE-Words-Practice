package com.example.grewordspractice.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;

import javax.inject.Inject;

public class AnalysisActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory factory;

    private AnalysisViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        viewModel = new ViewModelProvider(this, factory).get(AnalysisViewModel.class);
    }


}
