package com.example.grewordspractice;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.models.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "WordListAdapter";
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private CardListener listener;
    private List<Word> allWords = new ArrayList<>();

    public WordListAdapter() {
    }

    public WordListAdapter(CardListener listener, RecyclerView recyclerView) {
        this.listener = listener;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (listener != null) {
                                    listener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM; // TODO: Add progress bar
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.word_card_item, parent, false);
            vh = new WordListAdapterViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.word_card_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof WordListAdapterViewHolder) {
            Word word = allWords.get(position);
            ((WordListAdapterViewHolder) holder).word.setText(word.getWord());
            ((WordListAdapterViewHolder) holder).def.setText(word.getDefinitions());
            ((WordListAdapterViewHolder) holder).syn.setText(word.getSynonym());
            ((WordListAdapterViewHolder) holder).constraintLayout.setVisibility(View.GONE);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    public void refreshList(List<Word> words) {
        Log.d(TAG, "refreshList: size " + words.size());
        this.allWords = words;
        notifyDataSetChanged();
    }

    public void removeTop() {
        allWords.remove(0);
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
    }


    protected class WordListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView word, def, syn;
        Button show;
        ConstraintLayout constraintLayout;

        public WordListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word_tv);
            def = itemView.findViewById(R.id.def_tv);
            syn = itemView.findViewById(R.id.syn_tv);
            show = itemView.findViewById(R.id.showMeaning_button);
            constraintLayout = itemView.findViewById(R.id.answer_container);
            show.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
