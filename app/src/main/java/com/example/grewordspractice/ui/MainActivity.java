package com.example.grewordspractice.ui;
            
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;


import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.adapters.WordListAdapter;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.Word;
import com.example.grewordspractice.models.jsonModels.WordJson;
import com.example.grewordspractice.utils.SeenListIndexes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

import static com.example.grewordspractice.utils.Constants.RANDOM_WORD;


public class MainActivity extends BaseActivity implements OnItemSwiped, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    private RecyclerView rv;
    private WordJson wordJson;
    private Word word;
    private List<String> words = new ArrayList<>();
    private WordListAdapter adapter;
    private NumberPicker np;
    private Button button_start_new;

    private List<Integer> seenList;
    private SeenListIndexes seenListIndexes;


    @Inject
    ViewModelProviderFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seenList = new ArrayList<>();
        seenListIndexes = new SeenListIndexes();
        seenList = seenListIndexes.getFromSP(getApplication());
        seenListIndexes.setSeenList(seenList);
        rv = findViewById(R.id.main_rv);
        np = findViewById(R.id.rand_np);
        button_start_new = findViewById(R.id.start_rand_butt);
        np.setMinValue(1);
        np.setMaxValue(10);
        button_start_new.setOnClickListener(this);

        adapter = new WordListAdapter();

        SwipeableTouchHelperCallback swipeableTouchHelperCallback =
                new SwipeableTouchHelperCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rv);

        SwipeableLayoutManager swipeableLayoutManager = new SwipeableLayoutManager();
        swipeableLayoutManager
                .setAngle(10)
                .setAnimationDuratuion(500)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0);
        rv.setLayoutManager(swipeableLayoutManager);
        rv.setAdapter(adapter);


        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        viewModel.getAllWords(this).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.refreshList(words);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        viewModel.deleteAll();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_newCard:
                Intent intent1 = new Intent(this, AddNewWordActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_savedCards:
                Intent intent2 = new Intent(this, SavedWordsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_practiceCard:
                Intent intent = new Intent(this, PracticeSavedWordsActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSwiped() {

    }

    @Override
    public void onItemSwipedLeft() {
        Word word = adapter.getWordsList().get(0);
        viewModel.delete(word);
        viewModel.insertSavedWord(new SavedWord(word.getWord(), word.getDefinitions(), word.getSynonym(),
                true, false, false, new Date(), 0,1));
        adapter.removeTop();
        seenListIndexes.addToSeenList(RANDOM_WORD.indexOf(word.getWord()));
        seenListIndexes.saveInSP(getApplication());
    }

    @Override
    public void onItemSwipedRight() {
        Word word = adapter.getWordsList().get(0);
        viewModel.delete(word);
        viewModel.insertSavedWord(new SavedWord(word.getWord(), word.getDefinitions(), word.getSynonym(),
                false, false, true, new Date(), 1, 1));
        adapter.removeTop();
        seenListIndexes.addToSeenList(RANDOM_WORD.indexOf(word.getWord()));
        seenListIndexes.saveInSP(getApplication());
    }

    @Override
    public void onItemSwipedUp() {

    }

    @Override
    public void onItemSwipedDown() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_rand_butt :
                viewModel.fetchRandomData(np.getValue());
                np.setVisibility(View.GONE);
                button_start_new.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
        }
    }


}
