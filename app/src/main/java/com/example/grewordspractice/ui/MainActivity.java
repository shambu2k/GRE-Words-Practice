package com.example.grewordspractice.ui;
            
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.CardListener;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.WordListAdapter;
import com.example.grewordspractice.models.Word;
import com.example.grewordspractice.models.jsonModels.WordJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

import static com.example.grewordspractice.utils.Constants.BASE_RW_URL;
import static com.example.grewordspractice.utils.Constants.TASK_GETDEF;
import static com.example.grewordspractice.utils.Constants.TASK_GETWORD;

public class MainActivity extends DaggerAppCompatActivity implements OnItemSwiped {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    private RecyclerView rv;
    private WordJson wordJson;
    private Word word;
    private List<String> words = new ArrayList<>();
    private WordListAdapter adapter;


    @Inject
    ViewModelProviderFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.main_rv);
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
        viewModel.fetchRandomData().execute(TASK_GETWORD);
        viewModel.fetchRandomData().execute(TASK_GETDEF);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSwiped() {
        adapter.removeTop();
    }

    @Override
    public void onItemSwipedLeft() {

    }

    @Override
    public void onItemSwipedRight() {

    }

    @Override
    public void onItemSwipedUp() {

    }

    @Override
    public void onItemSwipedDown() {

    }

}
