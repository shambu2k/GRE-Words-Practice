package com.example.grewordspractice.adapters;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.AdapterClickListener;
import com.example.grewordspractice.R;
import com.example.grewordspractice.models.SavedWord;

import java.util.ArrayList;
import java.util.List;

public class SavedWordsListAdapter extends RecyclerView.Adapter<SavedWordsListAdapter.SavedWordsViewHolder> {

    private static final String TAG = "SavedWordsListAdapter";

    private List<SavedWord> allSavedWords = new ArrayList<>();
    private int wrong;
    private AdapterClickListener listener;
    private SparseBooleanArray selectedItems;
    private static int currentSelectedIndex = -1;

    public SavedWordsListAdapter(AdapterClickListener listener) {
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

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
        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return allSavedWords.size();
    }

    public void refreshList(List<SavedWord> newList){
        this.allSavedWords = newList;
        notifyDataSetChanged();
    }

    public SavedWord getDataFromList(int position){
        return allSavedWords.get(position);
    }

    protected class SavedWordsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView word, def, syn, stats;
        ConstraintLayout container;

        public SavedWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.saved_word_tv);
            def = itemView.findViewById(R.id.saved_def_tv);
            syn = itemView.findViewById(R.id.saved_syn_tv);
            stats = itemView.findViewById(R.id.saved_stats_tv);
            container = itemView.findViewById(R.id.saved_cl);
            container.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onRowClicked(getAdapterPosition());
        }
    }

    public void wordToggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void wordSelectAll() {
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();
    }

    public void wordClearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }


}
