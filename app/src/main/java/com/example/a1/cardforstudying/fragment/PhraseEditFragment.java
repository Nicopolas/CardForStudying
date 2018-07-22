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
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.a1.cardforstudying.CardsForStudying;
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

    private View view;
    private Phrase phrase = new Phrase();
    private int dictionaryID;
    private int elementID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.phrase_edit_fragment, container, false);

        addActionBar();
        initGUI();

        return view;
    }

    public int getDictionaryID() {
        return dictionaryID;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_element_menu, menu);
        return;
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                savePhrase();
                return true;
            case android.R.id.home:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initGUI() {
        mPhraseMeaning = view.findViewById(R.id.set_phrase_meaning);
        mPhraseTranslation = view.findViewById(R.id.set_phrase_translation);

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

        phrase = PhraseLab.get(getActivity()).getPhraseByID(elementID);
        mPhraseMeaning.setText(phrase.getPhraseMeaning());
        mPhraseTranslation.setText(phrase.getPhraseTranslation());
        setTitleActionBar(phrase.getPhraseMeaning());
    }

    private void savePhrase() {
        phrase.setPhraseMeaning(mPhraseMeaning.getText().toString());
        phrase.setPhraseTranslation(mPhraseTranslation.getText().toString());
        phrase.setDictionaryID(dictionaryID);
        PhraseLab.get(getActivity()).savePhraseInDateBase(phrase);
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
