package com.example.grewordspractice.ui;


import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.grewordspractice.AdapterClickListener;
import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.adapters.SavedWordsListAdapter;
import com.example.grewordspractice.models.SavedWord;

import java.util.List;

import javax.inject.Inject;

public class SavedWordsActivity extends BaseActivity implements AdapterClickListener {

    @Inject
    ViewModelProviderFactory factory;

    private SavedWordsActivityViewModel viewModel;

    private RecyclerView rv;
    private SavedWordsListAdapter adapter;

    private ActionMode actionMode;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuItem item = menu.getItem(1);
            if (adapter.getSelectedItemCount() == adapter.getItemCount()) {
                item.setVisible(false);
            } else {
                item.setVisible(true);
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {


                case R.id.action_delete:
                    deleteRows();
                    mode.finish();
                    return true;

                case R.id.action_select_all:
                    selectAll();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.wordClearSelections();
            actionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_words);
        rv = findViewById(R.id.saved_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        adapter = new SavedWordsListAdapter(this);
        rv.setAdapter(adapter);
        viewModel = new ViewModelProvider(this, factory).get(SavedWordsActivityViewModel.class);
        viewModel.getAllLiveSavedWords().observe(this, new Observer<List<SavedWord>>() {
            @Override
            public void onChanged(List<SavedWord> savedWords) {
                adapter.refreshList(savedWords);
            }
        });

    }


    @Override
    public void onRowClicked(int pos) {
        enableActionMode(pos);
    }



    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = this.startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        adapter.wordToggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.wordSelectAll();
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        //  actionMode = null;
    }

    private void deleteRows() {
        List<Integer> selectedItemPositions =
                adapter.getSelectedItems();
        if(adapter.getItemCount()==selectedItemPositions.size()){
            viewModel.deleteAll();
            actionMode = null;
        }
        else {
            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                viewModel.delete(adapter.getDataFromList(selectedItemPositions.get(i)));
            }
            actionMode = null;
        }
    }
}
