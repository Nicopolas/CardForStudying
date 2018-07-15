package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.Word;
import com.example.a1.cardforstudying.model.WordLab;

/**
 * Created by 1 on 15.07.2018.
 */

public class WordEditFragment extends Fragment {
    final String TAG = getClass().getSimpleName();
    private EditText mMeaningWord;
    private EditText mWordTranscription;
    private EditText mTranslationWord;
    private EditText mExample;
    private CheckBox mInTest;
    private Button mCancel;
    private Button mSave;

    private View view;
    private Word word = new Word();
    private int dictionaryID;
    private int elementID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.word_edit_fragment, container, false);

        setHasOptionsMenu(true);
        initGUI();

        return view;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getDictionaryID() {
        return dictionaryID;
    }

    private void initGUI() {
        mMeaningWord = view.findViewById(R.id.set_meaning_word);
        mWordTranscription = view.findViewById(R.id.set_word_transcription);
        mTranslationWord = view.findViewById(R.id.set_translation_word);
        mExample = view.findViewById(R.id.set_example);
        mInTest = view.findViewById(R.id.in_test);
        mCancel = view.findViewById(R.id.cancel);
        mSave = view.findViewById(R.id.save);


        setDataFromWordLab();
        setListeners();
    }

    private void setDataFromWordLab() {
        String __dictionaryID = getArguments().getString(getClass().getSimpleName() + "_dictionaryID");
        //сюда проверку на __dictionaryID==null
        dictionaryID = Integer.valueOf(__dictionaryID);
        ((ListActivity) getActivity()).getSupportActionBar().setTitle(DictionaryLab.get(getActivity()).getDictionaryByID(dictionaryID).getDictionaryName());

        String _elementID = getArguments().getString(getClass().getSimpleName() + "_elementID");
        if (_elementID == null) {
            return;
        }

        elementID = Integer.valueOf(_elementID);
        dictionaryID = Integer.valueOf(__dictionaryID);
        word = WordLab.get(getActivity()).getWordByID(elementID);

        mMeaningWord.setText(word.getMeaningWord());
        mWordTranscription.setText(word.getMeaningWordTranscription());
        mTranslationWord.setText(word.getTranslationWord());
        mExample.setText(word.getExample());
        mInTest.setChecked(word.isInTest());
    }

    private void setListeners() {
        mCancel.setOnClickListener(view -> cancel());
        mSave.setOnClickListener(view -> saveWord());
    }

    private void saveWord() {
        word.setMeaningWord(mMeaningWord.getText().toString());
        word.setMeaningWordTranscription(mWordTranscription.getText().toString());
        word.setTranslationWord(mTranslationWord.getText().toString());
        word.setExample(mExample.getText().toString(), null);
        word.setInTest(mInTest.isChecked());
        word.setDictionaryID(dictionaryID);
        WordLab.get(getActivity()).saveWordInDateBase(word);
    }

    private void cancel() {
        ((ListActivity) getActivity()).onBackPressed();
    }

}