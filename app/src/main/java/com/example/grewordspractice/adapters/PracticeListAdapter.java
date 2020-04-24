package com.example.grewordspractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grewordspractice.R;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.Word;

import java.util.ArrayList;
import java.util.List;

public class PracticeListAdapter extends RecyclerView.Adapter<PracticeListAdapter.PracticeListAdapterViewHolder> {

    private List<SavedWord> savedWordList = new ArrayList<>();

    public PracticeListAdapter(){

    }

    public PracticeListAdapter(List<SavedWord> savedWordList) {
        this.savedWordList = savedWordList;
    }

    @NonNull
    @Override
    public PracticeListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.word_card_item, parent, false);
        return new PracticeListAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeListAdapterViewHolder holder, int position) {
        SavedWord savedWord = savedWordList.get(position);
        holder.word.setText(savedWord.getWord());
        holder.def.setText(savedWord.getDefinitions());
        holder.syn.setText(savedWord.getSynonym());
        holder.constraintLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return savedWordList.size();
    }

    public void refreshList(List<SavedWord> list){
        this.savedWordList = list;
        notifyDataSetChanged();
    }

    public void removeTop() {
        savedWordList.remove(0);
        notifyDataSetChanged();
    }

    public List<SavedWord> getPracticeWordsList(){
        return savedWordList;
    }

    protected class PracticeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView word, def, syn;
        Button show;
        ConstraintLayout constraintLayout;

        public PracticeListAdapterViewHolder(@NonNull View itemView) {
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
}
