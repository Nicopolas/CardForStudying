package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStuduing;
import com.example.a1.cardforstudying.R;

/**
 * Created by user on 29.05.2018.
 */

public class LeftButtonFragment extends Fragment{
    String TAG = getClass().getSimpleName();
    TextView mTestLeftButton;
    TextView mWordLeftButton;
    TextView mPhraseLeftButton;
    static boolean isOpen = false;

    ViewGroup sceneRoot;
    Scene scene1;
    Scene scene2;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.left_button_fragment, container, false);

        sceneRoot = (ViewGroup) v.findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene1, getActivity());
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene2, getActivity());

        initGUI();
        return v;
    }

    private void initGUI() {
        mTestLeftButton = v.findViewById(R.id.test_left_button);
        mWordLeftButton = v.findViewById(R.id.word_left_button);
        mPhraseLeftButton = v.findViewById(R.id.phrase_left_button);
        setListener();
    }

    private void setListener() {
        mTestLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    ((CardsForStuduing) getActivity()).startFragment(new TestFragment());
                    beginAutoTransition(scene1, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene2, 200);
                return true;
            }
        });

        mWordLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    ((CardsForStuduing) getActivity()).startFragment(new CardsFragment());
                    beginAutoTransition(scene1, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene2, 200);
                return true;
            }
        });

        mPhraseLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    ((CardsForStuduing) getActivity()).startFragment(new PhraseFragment());
                    beginAutoTransition(scene1, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene2, 200);
                return true;
            }
        });
    }

    private void beginAutoTransition(Scene scene, int durability) {
        TransitionSet set = new TransitionSet();
        set.addTransition(new Fade());
        set.addTransition(new ChangeBounds());
        // выполняться они будут одновременно
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        // уставим свою длительность анимации
        set.setDuration(durability);
        // и изменим Interpolator
        set.setInterpolator(new AccelerateInterpolator());
        TransitionManager.go(scene, set);
        initGUI();
    }
}
