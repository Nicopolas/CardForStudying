package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.Word;
import com.example.a1.cardforstudying.WordLab;

import java.util.List;

public class EmptyDictionary extends BaseFragment {
    private static final String TAG = "EmptyDictionary";
    public static int mMessage;
    private TextView mEmptyDictionary;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d(TAG,"onCreateView called");
        View v = inflater.inflate(R.layout.empty_dictionary, container, false);

        mMenuBetton = v.findViewById(R.id.menu_button);
        mMenuBetton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(R.string.err_inDeveloping);//в разработке
            }
        });

        mEmptyDictionary = v.findViewById(R.id.empty_dictionary);
        mEmptyDictionary.setText(mMessage);
        mEmptyDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //добавить мепеход в меню словарей
                makeToast(R.string.err_inDeveloping);//в разработке
            }
        });

        return v;
    }

    @Override
    public void putDataInElements(){};
}
