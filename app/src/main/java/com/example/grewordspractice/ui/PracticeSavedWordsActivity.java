package com.example.grewordspractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.AsyncCallBack;
import com.example.grewordspractice.adapters.PracticeListAdapter;
import com.example.grewordspractice.models.SavedWord;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

public class PracticeSavedWordsActivity extends BaseActivity implements OnItemSwiped, AsyncCallBack {

    private static final String TAG = "PracticeSavedWordsActiv";
    @Inject
    ViewModelProviderFactory factory;

    private PracticeSavedWordsViewModel viewModel;

    private RecyclerView rv;
    private PracticeListAdapter adapter;
    private List<SavedWord> anaysisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_saved_words);
        rv = findViewById(R.id.practice_rv);
        anaysisList = new ArrayList<>();
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


        viewModel = new ViewModelProvider(this, factory).get(PracticeSavedWordsViewModel.class);
        
        if(getIntent().getExtras().getString("SESSION_ID").equals("frommain")){
            viewModel.getDataTask(this).execute();
        } else if(getIntent().getExtras().getString("SESSION_ID").equals("fromsaved")){
            
        } else {
            Log.d(TAG, "onCreate: SESSION_ID not Matching");
        }
        
        adapter = new PracticeListAdapter();
        rv.setAdapter(adapter);

    }

    @Override
    public void onItemSwiped() {

    }

    @Override
    public void onItemSwipedLeft() {
        SavedWord savedWord = adapter.getPracticeWordsList().get(0);
        viewModel.deleteSavedWord(adapter.getPracticeWordsList().get(0));
        viewModel.insertSavedWord(new SavedWord(savedWord.getWord(), savedWord.getDefinitions(), savedWord.getSynonym(),
                true, false, false, new Date(), 0,savedWord.getTotTaken()+1));
        adapter.removeTop();
        anaysisList.add(savedWord);
        if(adapter.getItemCount()==0){
            Intent intent = new Intent(this, AnalysisActivity.class);
            Gson gson = new Gson();
            String analysisJson = gson.toJson(anaysisList);
            Log.d(TAG, "onItemSwipedLeft: analysis JSON "+analysisJson);
            intent.putExtra("ANALYSIS", analysisJson);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemSwipedRight() {
        SavedWord savedWord = adapter.getPracticeWordsList().get(0);
        viewModel.deleteSavedWord(adapter.getPracticeWordsList().get(adapter.getPracticeWordsList().indexOf(savedWord)));
        if(savedWord.isShowNextDay()){
            viewModel.insertSavedWord(new SavedWord(savedWord.getWord(), savedWord.getDefinitions(), savedWord.getSynonym(),
                    false, true, true, new Date(), savedWord.getTotCorrect()+1,savedWord.getTotTaken()+1));
        } else {
            viewModel.insertSavedWord(new SavedWord(savedWord.getWord(), savedWord.getDefinitions(), savedWord.getSynonym(),
                    false, false, true, new Date(), savedWord.getTotCorrect()+1,savedWord.getTotTaken()+1));
        }
        adapter.removeTop();
        if(adapter.getItemCount()==0){
            Intent intent = new Intent(this, AnalysisActivity.class);
            Gson gson = new Gson();
            String analysisJson = gson.toJson(anaysisList);
            intent.putExtra("ANALYSIS", analysisJson);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onItemSwipedUp() {

    }

    @Override
    public void onItemSwipedDown() {

    }

    @Override
    public void getPracticeDataFromVM() {
        adapter.refreshList(viewModel.getPracticeWords());
    }
}
