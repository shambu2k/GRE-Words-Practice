package com.example.grewordspractice.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.adapters.SavedWordsListAdapter;
import com.example.grewordspractice.models.SavedWord;

import java.util.List;

import javax.inject.Inject;

public class SavedWordsActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory factory;

    private SavedWordsActivityViewModel viewModel;

    private RecyclerView rv;
    private SavedWordsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_words);
        rv = findViewById(R.id.saved_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        adapter = new SavedWordsListAdapter();
        rv.setAdapter(adapter);
        viewModel = new ViewModelProvider(this, factory).get(SavedWordsActivityViewModel.class);
        viewModel.getAllLiveSavedWords().observe(this, new Observer<List<SavedWord>>() {
            @Override
            public void onChanged(List<SavedWord> savedWords) {
                adapter.refreshList(savedWords);
            }
        });

    }
}
