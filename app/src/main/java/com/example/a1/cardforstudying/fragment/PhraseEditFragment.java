package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.Phrase;
import com.example.a1.cardforstudying.model.PhraseLab;

/**
 * Created by 1 on 15.07.2018.
 */

public class PhraseEditFragment extends Fragment {
    final String TAG = getClass().getSimpleName();
    private EditText mPhraseMeaning;
    private EditText mPhraseTranslation;
    private Button mCancel;
    private Button mSave;

    private View view;
    private Phrase phrase = new Phrase();
    private int dictionaryID;
    private int elementID;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.phrase_edit_fragment, container, false);

        setHasOptionsMenu(true);
        initGUI();

        return view;
    }

    public int getDictionaryID() {
        return dictionaryID;
    }

    private void initGUI() {
        mPhraseMeaning = view.findViewById(R.id.set_phrase_meaning);
        mPhraseTranslation = view.findViewById(R.id.set_phrase_translation);
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
        phrase = PhraseLab.get(getActivity()).getPhraseByID(elementID);

        mPhraseMeaning.setText(phrase.getPhraseMeaning());
        mPhraseTranslation.setText(phrase.getPhraseMeaning());
    }

    private void setListeners() {
        mCancel.setOnClickListener(view -> cancel());
        mSave.setOnClickListener(view -> savePhrase());
    }

    private void savePhrase() {
        phrase.setPhraseMeaning(mPhraseMeaning.getText().toString());
        phrase.setPhraseTranslation(mPhraseTranslation.getText().toString());
        phrase.setDictionaryID(dictionaryID);
        PhraseLab.get(getActivity()).savePhraseInDateBase(phrase);
    }

    private void cancel() {
        ((ListActivity) getActivity()).onBackPressed();
    }
}
