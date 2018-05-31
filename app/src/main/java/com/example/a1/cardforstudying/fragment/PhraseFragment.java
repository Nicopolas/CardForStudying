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
    View v;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        v = inflater.inflate(R.layout.phrase_fragment, container, false);

        initGUI();

        showPhrase();
        return v;
    }

    public void showPhrase(){
        putDataInElements();
    }

    @Override
    public void putDataInElements(){

    }

    private void initGUI() {
        mMenuBetton = v.findViewById(R.id.menu_button);
        mNextButton = v.findViewById(R.id.next_button);
        mPreviousButton = v.findViewById(R.id.previous_button);

        setListener();
    }

    private void setListener() {
        mMenuBetton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(R.string.err_inDeveloping);//в разработке
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(true);
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWord(false);
            }
        });
    }

}
