package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.a1.cardforstudying.CardsForStudying;
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

    private View view;
    private Word word = new Word();
    private int dictionaryID;
    private int elementID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.word_edit_fragment, container, false);

        addActionBar();
        initGUI();
        setListener();

        return view;
    }

    public int getDictionaryID() {
        return dictionaryID;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_element_menu, menu);
        //super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveWord();
                return true;
            case android.R.id.home:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListener() {
        mInTest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (fieldIsEmpty(mMeaningWord)) {
                ((ListActivity) getActivity()).makeToast("You can not save a word without entering its value");
                mInTest.setChecked(false);
            }
            if (fieldIsEmpty(mTranslationWord)) {
                ((ListActivity) getActivity()).makeToast("You can not save a word without entering its meaningful translation");
                mInTest.setChecked(false);
            }
        });
    }

    private void initGUI() {
        mMeaningWord = view.findViewById(R.id.set_meaning_word);
        mWordTranscription = view.findViewById(R.id.set_word_transcription);
        mTranslationWord = view.findViewById(R.id.set_translation_word);
        mExample = view.findViewById(R.id.set_example);
        mInTest = view.findViewById(R.id.in_test);

        setDataFromWordLab();
    }

    private void setDataFromWordLab() {
        String _dictionaryID = getArguments().getString("_dictionaryID");
        if (_dictionaryID == null) {
            Log.e(TAG, "Не получен dictionaryID с предыдущего обьекта");
            back();
            //сюда вывод универсального врагмента с ошибкой
        }
        dictionaryID = Integer.valueOf(_dictionaryID);

        String _elementID = getArguments().getString("_elementID");
        if (_elementID == null) {
            setTitleActionBar(DictionaryLab.get(getActivity()).getDictionaryByID(dictionaryID).getDictionaryName());
            return;
        }
        elementID = Integer.valueOf(_elementID);

        word = WordLab.get(getActivity()).getWordByID(elementID);
        mMeaningWord.setText(word.getMeaningWord());
        mWordTranscription.setText(word.getMeaningWordTranscription());
        mTranslationWord.setText(word.getTranslationWord());
        mExample.setText(word.getExample());
        mInTest.setChecked(word.isInTest());
        setTitleActionBar(word.getMeaningWord());
    }

    private void saveWord() {
        word.setMeaningWord(mMeaningWord.getText().toString());
        word.setMeaningWordTranscription(mWordTranscription.getText().toString());
        word.setTranslationWord(mTranslationWord.getText().toString());
        word.setExample(mExample.getText().toString(), null);
        word.setInTest(mInTest.isChecked());
        word.setDictionaryID(dictionaryID);
        WordLab.get(getActivity()).saveWordInDateBase(word);
        back();
    }

    private boolean fieldIsEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private void back() {
        getActivity().onBackPressed();
    }

    private void setTitleActionBar(String title) {
        getActionBar().setTitle(title);
    }

    private void addActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    private ActionBar getActionBar() {
        try {
            //добавление кнопки back
            return ((ListActivity) getActivity()).getSupportActionBar();
        }
        catch (Exception eL) {
            Log.e(TAG, "getSupportActionBar() error");
            eL.printStackTrace();
            try {
                //добавление кнопки back
                return ((CardsForStudying) getActivity()).getSupportActionBar();
            }
            catch (Exception eC) {
                Log.e(TAG, "getSupportActionBar() error");
                eL.printStackTrace();
                back();
            }
        }
        return null;
    }
}
