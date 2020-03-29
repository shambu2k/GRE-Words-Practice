package com.example.grewordspractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.R;
import com.example.grewordspractice.models.SavedWord;

import java.util.ArrayList;
import java.util.List;

public class SavedWordsListAdapter extends RecyclerView.Adapter<SavedWordsListAdapter.SavedWordsViewHolder> {

    private List<SavedWord> allSavedWords = new ArrayList<>();
    private int wrong;


    @NonNull
    @Override
    public SavedWordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.savedwords_rv_item, parent, false);
        return new SavedWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordsViewHolder holder, int position) {
        SavedWord savedWord = allSavedWords.get(position);
        holder.word.setText(savedWord.getWord());
        holder.def.setText(savedWord.getDefinitions());
        holder.syn.setText(savedWord.getSynonym());
        wrong = savedWord.getTotTaken()-savedWord.getTotCorrect();
        holder.stats.setText("Total Times Seen: "+savedWord.getTotTaken()+", Total Times Correct: "+savedWord.getTotCorrect()+", Total Times Wrong: "+wrong);
    }

    @Override
    public int getItemCount() {
        return allSavedWords.size();
    }

    public void refreshList(List<SavedWord> newList){
        this.allSavedWords = newList;
        notifyDataSetChanged();
    }

    protected class SavedWordsViewHolder extends RecyclerView.ViewHolder {

        TextView word, def, syn, stats;

        public SavedWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.saved_word_tv);
            def = itemView.findViewById(R.id.saved_def_tv);
            syn = itemView.findViewById(R.id.saved_syn_tv);
            stats = itemView.findViewById(R.id.saved_stats_tv);
        }
    }

}
