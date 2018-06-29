package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    View v;
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private Button mFirstWordButton;
    private Button mSecondWordButton;
    private Button mThirdWordButton;
    private Button mFourthWordButton;

    public int mRightButtonIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        v = inflater.inflate(R.layout.test_fragment, container, false);
        initGUI();

        showWord();
        return v;
    }

    public void putDataInElements() {
        mWordTextView.setText(mDataList.get(index).getMeaningWord());
        mWordTranscriptionView.setText(mDataList.get(index).getMeaningWordTranscription());

        setWordInButton();
    }

    public void setWordInButton() {
        ArrayList<Button> mListButton = new ArrayList<Button>() {{
            add(mFirstWordButton);
            add(mSecondWordButton);
            add(mThirdWordButton);
            add(mFourthWordButton);
        }};
        mRightButtonIndex = new Random().nextInt(4);

        mListButton.get(mRightButtonIndex).setText(mDataList.get(index).getRandomTranslationWord());

        List<Word> mCopyListWord = new ArrayList();
        mCopyListWord.addAll(mDataList);

        mCopyListWord.remove(index);
        mListButton.remove(mListButton);//спорно

        for (int i = 0; i < mListButton.size(); i++) {
            if (i != mRightButtonIndex) {
                int mRandomWordIndex = new Random().nextInt(mCopyListWord.size());
                mListButton.get(i).setText(mCopyListWord.get(mRandomWordIndex).getRandomTranslationWord());
                mCopyListWord.remove(mRandomWordIndex);
            }
        }
    }

    public void checkAnswer(Button button) { //переделать
        ArrayList<Button> mListButton = new ArrayList<Button>() {{
            add(mFirstWordButton);
            add(mSecondWordButton);
            add(mThirdWordButton);
            add(mFourthWordButton);
        }};
        makeToast(mListButton.get(mRightButtonIndex) == button ?
                R.string.correct_answer_message : R.string.incorrect_answer_message);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        ((CardsForStudying) getActivity()).index = index;
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void initGUI() {
        mMenuBetton = v.findViewById(R.id.menu_button);
        mWordTextView = v.findViewById(R.id.word_text_view);
        mWordTranscriptionView = v.findViewById(R.id.word_transcription_view);

        mFirstWordButton = v.findViewById(R.id.first_word_button);
        mSecondWordButton = v.findViewById(R.id.second_word_button);
        mThirdWordButton = v.findViewById(R.id.third_word_button);
        mFourthWordButton = v.findViewById(R.id.fourth_word_button);

        mNextButton = v.findViewById(R.id.next_button);

        setListener();
    }

    private void setListener() {
        mMenuBetton.setOnClickListener(view ->
                ((CardsForStudying) getActivity()).getDrawer().openDrawer(GravityCompat.START));

        mWordTextView.setOnClickListener(view -> showWord(true));

        mWordTranscriptionView.setOnClickListener(view -> showWord(true));

        mFirstWordButton.setOnClickListener(view -> checkAnswer(mFirstWordButton));

        mSecondWordButton.setOnClickListener(view -> checkAnswer(mSecondWordButton));

        mThirdWordButton.setOnClickListener(view -> checkAnswer(mThirdWordButton));

        mFourthWordButton.setOnClickListener(view -> checkAnswer(mFourthWordButton));

        mNextButton.setOnClickListener(view -> showWord(true));
    }
}
