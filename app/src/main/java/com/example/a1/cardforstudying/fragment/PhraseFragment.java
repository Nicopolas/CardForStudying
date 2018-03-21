package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.cardforstudying.CardsForStuduing;
import com.example.a1.cardforstudying.R;

public class PhraseFragment extends BaseFragment {
    private static final String TAG = "BaseFragment";

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View v = inflater.inflate(R.layout.phrase_fragment, container, false);

        mMenuBetton = v.findViewById(R.id.menu_button);
        mMenuBetton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(R.string.err_inDeveloping);//в разработке
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
                Log.e(TAG, "Button click in " + this.getClass().getName().replace("com.example.a1.cardforstudying.fragment.",""));
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
                Log.e(TAG, "Button click in " + this.getClass().getName().replace("com.example.a1.cardforstudying.fragment.",""));
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
                Log.e(TAG, "Button click in " + this.getClass().getName().replace("com.example.a1.cardforstudying.fragment.",""));
                makeToast(R.string.err_inDeveloping);//в разработке
                ((CardsForStuduing) getActivity()).index = index; //перенести в onDestroy!!!
                ((CardsForStuduing) getActivity()).startFragment(new PhraseFragment());
            }
        });

        showPhrase();
        return v;
    }

    public void showPhrase(){
        putDataInElements();
    }

    @Override
    public void putDataInElements(){

    }
}
