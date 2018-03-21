package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CardsFragment extends Fragment {
    private static final String TAG = "CardsFragment";
    private ImageButton mMenuBetton;
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private TextView mWordTranslationView;
    public Button mExampleButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;

    @Deprecated
    private Button mToDelete;

    public List<Word> mListWord = new ArrayList<>();
    public int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate called");
        super.onCreate(savedInstanceState);

        index = ((CardsForStuduing)getActivity()).index;
        mListWord.addAll(WordLab.get(getActivity()).getWord());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d(TAG,"onCreateView called");
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

        mToDelete = v.findViewById(R.id.to_delete);
        mToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Button click in CardsFragment");
                //переход на новый фрагмент
                ((CardsForStuduing)getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing)getActivity()).startFragment(new TestFragment());//вызов активити из фрагмента
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
        mWordTranslationView.setText(mListWord.get(index).getTranslationWord().replace("сущ.:","\nсущ.:").replace("глаг.:","\nглаг.:").replace("прил.:","\nприл.:"));
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
