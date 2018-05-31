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
import java.util.Random;

public class TestFragment extends BaseFragment {
    private static final String TAG = "TestFragment";
    View v;
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private Button mFirstWordButton;
    private Button mSecondWordButton;
    private Button mThirdWordButton;
    private Button mFourthWordButton;

    public List<Word> mListWord;
    //public int index = 0; //проблема с наследованием от BaseFragment (изменяется индекс родительского класса)
    public int mRightButtonIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View v = inflater.inflate(R.layout.test_fragment, container, false);
        initGUI();

        showWord();
        return v;
    }

    public void putDataInElements() {
        mListWord = mDataList; //переделать (добавлть слова в тест, только есть есть признак mInTest) (не приравнивать массивы)
        mWordTextView.setText(mListWord.get(index).getMeaningWord());
        mWordTranscriptionView.setText(mListWord.get(index).getMeaningWordTranscription());

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

        mListButton.get(mRightButtonIndex).setText(mListWord.get(index).getRandomTranslationWord());

        List<Word> mCopyListWord = new ArrayList();
        mCopyListWord.addAll(mListWord);

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
        ((CardsForStuduing) getActivity()).index = index;
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
        mMenuBetton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(R.string.err_inDeveloping);//в разработке
            }
        });

        mWordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mWordTranscriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mFirstWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mFirstWordButton);
            }
        });

        mSecondWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mSecondWordButton);
            }
        });

        mThirdWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mThirdWordButton);
            }
        });

        mFourthWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mFourthWordButton);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });
    }
}
