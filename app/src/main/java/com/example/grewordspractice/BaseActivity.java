package com.example.grewordspractice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.grewordspractice.ui.AddNewViewModel;
import com.example.grewordspractice.ui.AddNewWordActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
