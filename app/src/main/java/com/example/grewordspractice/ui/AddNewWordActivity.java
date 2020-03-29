package com.example.grewordspractice.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.grewordspractice.BaseActivity;
import com.example.grewordspractice.R;
import com.example.grewordspractice.ViewModels.ViewModelProviderFactory;
import com.example.grewordspractice.models.SavedWord;
import com.example.grewordspractice.models.jsonModels.WordJson;

import javax.inject.Inject;

public class AddNewWordActivity extends BaseActivity implements View.OnClickListener {

    private EditText word_edt;
    private Button fetchdata_button;
    private TextView word_tv, def_tv, syn_tv;

    private AddNewViewModel viewModel;

    @Inject
    ViewModelProviderFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        word_edt = findViewById(R.id.addnew_edt);
        fetchdata_button = findViewById(R.id.addnew_getdata_butt);
        word_tv = findViewById(R.id.addnew_word_tv);
        def_tv = findViewById(R.id.addnew_def_tv);
        syn_tv = findViewById(R.id.addnew_syn_tv);

        viewModel = new ViewModelProvider(this, factory).get(AddNewViewModel.class);
        fetchdata_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addnew_getdata_butt :
                if(!TextUtils.isEmpty(word_edt.getText().toString())){
                    viewModel.getNewWord(word_edt.getText().toString(), AddNewWordActivity.this).observe(this, wordJson -> {
                        if(wordJson!=null){
                            updateCard(wordJson);
                        } else {
                            Toast.makeText(this, "Word not present in api", Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    Toast.makeText(this, "Enter Word", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void updateCard(WordJson wordJson){
        if (wordJson.getError() == null) {
            if (wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList() != null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getSynonymsList() != null) {
                viewModel.insertSavedWord(new SavedWord(wordJson.getWord(), wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getListDef().get(0), wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getSynonymsList().get(0).getSynonyms(), false, false, false,
                        null, 0,0));
                Toast.makeText(this, wordJson.getWord() + ": Saved", Toast.LENGTH_LONG).show();
                word_tv.setText(wordJson.getWord());
                def_tv.setText(wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getListDef().get(0));
                syn_tv.setText(wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getSynonymsList().get(0).getSynonyms());
            } else if (wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList() != null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getListDef() != null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getSynonymsList() == null) {
                viewModel.insertSavedWord(new SavedWord(wordJson.getWord(), wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getListDef().get(0), "Synonyms Not Available",
                        false, false, false,
                        null, 0,0));
                Toast.makeText(this, wordJson.getWord() + ": Saved", Toast.LENGTH_LONG).show();
                word_tv.setText(wordJson.getWord());
                def_tv.setText(wordJson.getResultsJsonList().get(0).getLexicalEntriesList().get(0).getEntriesList()
                        .get(0).getSensesList().get(0).getListDef().get(0));
                syn_tv.setText("Synonyms Not Available");
            } else if (wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList() != null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getListDef() == null && wordJson.getResultsJsonList().get(0).
                    getLexicalEntriesList().get(0).getEntriesList().get(0).
                    getSensesList().get(0).getSynonymsList() == null) {

                Toast.makeText(this, wordJson.getWord() + " def: null synonyms: null", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, wordJson.getWord() + " : Word Not recognized", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
