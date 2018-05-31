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
import com.example.a1.cardforstudying.TextToSpeechHelper;
import com.example.a1.cardforstudying.Word;
import com.example.a1.cardforstudying.WordLab;

import java.util.ArrayList;
import java.util.List;

public class CardsFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private TextView mWordTranslationView;
    public Button mExampleButton;
    public ImageButton mSpeechButton;

    public List<Word> mListWord;
    //public int index = 0;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View v = inflater.inflate(R.layout.cards_fragment, container, false);
        initGUI(v);
        showWord();
        return v;
    }

    private void initGUI(View v) {
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

        mSpeechButton = v.findViewById(R.id.speech_button);
        mSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextToSpeechHelper.speakOut(getActivity(), mListWord.get(index).getMeaningWord());
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
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop()");
        ((CardsForStuduing) getActivity()).index = index;
        super.onStop();
    }

    public void putDataInElements() {
        mListWord = mDataList;//переделать не приравнивать массивы
        mWordTextView.setText(mListWord.get(index).getMeaningWord());
        mWordTranscriptionView.setText(mListWord.get(index).getMeaningWordTranscription());
        mWordTranslationView.setText(mListWord.get(index).getTranslationWord().replace("сущ.:", "\nсущ.:").replace("глаг.:", "\nглаг.:").replace("прил.:", "\nприл.:"));
    }

}
