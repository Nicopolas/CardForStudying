package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EmptyDictionary extends Fragment {
    private static final String TAG = "EmptyDictionary";
    public static int mMessage;
    public List<Word> mListWord;
    private ImageButton mMenuBetton;
    private TextView mEmptyDictionary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");
        super.onCreate(savedInstanceState);

        mListWord = WordLab.get(getActivity()).getWord();
    }

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

    public void makeToast(int string_id){
        Toast toast = Toast.makeText(getActivity()/*для вызова родительской активити*/, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }
}
