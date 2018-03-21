package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.CardsForStuduing;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.Word;
import com.example.a1.cardforstudying.WordLab;

import java.util.ArrayList;
import java.util.List;

public class CardsFragment extends BaseFragment {
    private static final String TAG = "CardsFragment";
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private TextView mWordTranslationView;
    public Button mExampleButton;

    public List<Word> mListWord;
    //public int index = 0;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View v = inflater.inflate(R.layout.cards_fragment, container, false);

        mMenuBetton = v.findViewById(R.id.menu_button);
        mMenuBetton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(R.string.err_inDeveloping);//в разработке
            }
        });

        mWordTextView = v.findViewById(R.id.word_text_view);
        mWordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mWordTranscriptionView = v.findViewById(R.id.word_transcription_view);
        mWordTranscriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mWordTranslationView = v.findViewById(R.id.word_translation_view);

        mExampleButton = v.findViewById(R.id.example_button);
        mExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mListWord.get(index).getExample());//в разработке
                makeToast(mListWord.get(index).getExampleTranslation());//в разработке
            }
        });

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mPreviousButton = v.findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(false);
            }
        });

        mGTWord = v.findViewById(R.id.go_to_word_button);
        if (this.getClass().getName().matches(".*CardsFragment")) {
            mGTWord.setEnabled(false);
            mGTWord.setClickable(false);
        }
        mGTWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Button click in " + TAG);
                ((CardsForStuduing) getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing) getActivity()).startFragment(new CardsFragment());
            }
        });

        mGTTest = v.findViewById(R.id.go_to_test_button);
        if (this.getClass().getName().matches(".*TestFragment")) {
            mGTTest.setEnabled(false);
            mGTTest.setClickable(false);
        }
        mGTTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Button click in " + TAG);
                ((CardsForStuduing) getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing) getActivity()).startFragment(new TestFragment());
            }
        });

        mGTPhrase = v.findViewById(R.id.go_to_phrase_button);
        if (this.getClass().getName().matches(".*PhraseFragment")) {
            mGTPhrase.setEnabled(false);
            mGTPhrase.setClickable(false);
        }
        mGTPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Button click in " + TAG);
                makeToast(R.string.err_inDeveloping);//в разработке
                ((CardsForStuduing) getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing) getActivity()).startFragment(new PhraseFragment());
            }
        });

        showWord();
        return v;
    }

    public void putDataInElements() {
        mListWord = mDataList;//переделать не приравнивать массивы
        mWordTextView.setText(mListWord.get(index).getMeaningWord());
        mWordTranscriptionView.setText(mListWord.get(index).getMeaningWordTranscription());
        mWordTranslationView.setText(mListWord.get(index).getTranslationWord().replace("сущ.:", "\nсущ.:").replace("глаг.:", "\nглаг.:").replace("прил.:", "\nприл.:"));
    }

}
