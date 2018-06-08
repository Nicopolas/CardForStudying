package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.cardforstudying.R;

/**
 * Created by 1 on 08.06.2018.
 */

public class DictionariesFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        v = inflater.inflate(R.layout.dictionaries_fragment, container, false);
        initGUI();

        return v;
    }

    private void initGUI(){

    }
}
