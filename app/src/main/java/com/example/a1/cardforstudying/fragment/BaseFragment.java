
//доработать

package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a1.cardforstudying.CardsForStuduing;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.Word;
import com.example.a1.cardforstudying.WordLab;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public abstract class BaseFragment extends Fragment {

    protected ImageButton mMenuBetton;
    protected ImageButton mNextButton;
    protected ImageButton mPreviousButton;

    @Deprecated
    protected Button mGTWord;
    @Deprecated
    protected Button mGTTest;
    @Deprecated
    protected Button mGTPhrase;

    protected List<Word> mDataList;
    protected int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");
        super.onCreate(savedInstanceState);

        index = ((CardsForStuduing) getActivity()).index;
        fillDataList();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.phrase_fragment, container, false);

        return v;
    }


    public void showWord(boolean nextWord) {
        if (nextWord) {
            index = (index + 1) % mDataList.size();
        } else {
            index--;
            if (index == -1) {
                index = mDataList.size() - 1;
            }
        }
        showWord();
    }

    public void showWord() {
        if (mDataList.isEmpty()) {
            Log.e(TAG, getResources().getString(R.string.err_mListWordIsEmpty));
            makeToast(R.string.err_mListWordIsEmpty);
        }
        putDataInElements();
    }

    public abstract void putDataInElements();

    public void makeToast(int string_id) {
        Toast toast = Toast.makeText(getActivity()/*для вызова родительской активити*/, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void makeToast(String string) {
        Toast toast = Toast.makeText(getActivity()/*для вызова родительской активити*/, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void fillDataList(){
        mDataList = new ArrayList<>();
        mDataList.addAll(WordLab.get(getActivity()).getWord());
    }
}
