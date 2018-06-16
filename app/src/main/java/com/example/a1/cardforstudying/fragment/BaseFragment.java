
//доработать

package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.Word;
import com.example.a1.cardforstudying.WordLab;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

//переделать довести до ума или избавиться
public abstract class BaseFragment extends Fragment {
    public final String TAG = getClass().getSimpleName();

    protected ImageButton mMenuBetton;
    protected ImageButton mNextButton;
    protected ImageButton mPreviousButton;

    protected List<Word> mDataList;
    protected int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");
        super.onCreate(savedInstanceState);

        index = ((CardsForStudying) getActivity()).index;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.phrase_fragment, container, false);

        return v;
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart() called");
        showWord();
        super.onStart();
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
        fillDataList();
        if (mDataList.isEmpty()) {
            Log.e(TAG, getResources().getString(R.string.err_mListWordIsEmpty));
            return;
        }
        this.putDataInElements();
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
        mDataList.addAll(WordLab.get(getActivity()).getWords());
    }
}
