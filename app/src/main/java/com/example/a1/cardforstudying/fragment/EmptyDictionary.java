package com.example.a1.cardforstudying.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;

public class EmptyDictionary extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
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
                ((CardsForStudying) getActivity()).getDrawer().openDrawer(GravityCompat.START);
            }
        });

        mEmptyDictionary = v.findViewById(R.id.empty_dictionary);
        mEmptyDictionary.setText(mMessage);
        mEmptyDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListActivity.class));
            }
        });

        return v;
    }

    @Override
    public void putDataInElements(){}
}
