package com.example.a1.cardforstudying;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";

    private ImageButton mMenuBetton;
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private Button mFirstWordButton;
    private Button mSecondWordButton;
    private Button mThirdWordButton;
    private Button mFourthWordButton;
    private ImageButton mNextButton;

    @Deprecated
    private Button mToDelete;

    public List<Word> mListWord = new ArrayList<>();
    public int index = 0;
    public int mRightButtonIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");
        super.onCreate(savedInstanceState);

        index = ((CardsForStuduing)getActivity()).index;
        mListWord.addAll(WordLab.get(getActivity()).getWord());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d(TAG,"onCreateView called");
        View v = inflater.inflate(R.layout.test_fragment, container, false);

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

        mFirstWordButton = v.findViewById(R.id.first_word_button);
        mFirstWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mFirstWordButton);
            }
        });

        mSecondWordButton = v.findViewById(R.id.second_word_button);
        mSecondWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mSecondWordButton);
            }
        });

        mThirdWordButton = v.findViewById(R.id.third_word_button);
        mThirdWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mThirdWordButton);
            }
        });

        mFourthWordButton = v.findViewById(R.id.fourth_word_button);
        mFourthWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mFourthWordButton);
            }
        });

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mToDelete = v.findViewById(R.id.to_delete);
        mToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Button click in TestFragment");
                //переход на новый фрагмент
                ((CardsForStuduing)getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing)getActivity()).startFragment(new CardsFragment());//вызов активити из фрагмента
            }
        });

        showWord();
        return v;
    }


    public void showWord(boolean nextWord){
        if(nextWord){
            index = (index + 1) % mListWord.size();
        }
        else {
            index--;
            if (index == -1){
                index = mListWord.size() - 1;
            }
        }
        showWord();
    }
    public void showWord(){
        if (mListWord.isEmpty()){
            Log.e(TAG, getResources().getString(R.string.err_mListWordIsEmpty));
            makeToast(R.string.err_mListWordIsEmpty);
        }
        mWordTextView.setText(mListWord.get(index).getMeaningWord());
        mWordTranscriptionView.setText(mListWord.get(index).getMeaningWordTranscription());

        setWordInButton();
    }

    public void setWordInButton(){
        ArrayList <Button> mListButton = new ArrayList<Button>(){{
            add(mFirstWordButton);
            add(mSecondWordButton);
            add(mThirdWordButton);
            add(mFourthWordButton);
        }};
        mRightButtonIndex = new Random().nextInt(4);

        mListButton.get(mRightButtonIndex).setText(mListWord.get(index).getRandomTranslationWord());

        List<Word>  mCopyListWord =  new ArrayList();
        mCopyListWord.addAll(mListWord);

        mCopyListWord.remove(index);
        mListButton.remove(mListButton);//спорно

        for (int i = 0;i < mListButton.size();i++){
            if (i!= mRightButtonIndex){
                int mRandomWordIndex = new Random().nextInt(mCopyListWord.size());
                mListButton.get(i).setText(mCopyListWord.get(mRandomWordIndex).getRandomTranslationWord());
                mCopyListWord.remove(mRandomWordIndex);
            }
        }
    }

    public void checkAnswer(Button button){ //переделать
       ArrayList <Button> mListButton = new ArrayList<Button>(){{
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
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void makeToast(int string_id){
        Toast toast = Toast.makeText(getActivity()/*для вызова родительской активити*/, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }
    public void makeToast(String string){
        Toast toast = Toast.makeText(getActivity()/*для вызова родительской активити*/, string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
