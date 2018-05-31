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
    Scene wordScene2;
    Scene testScene2;
    Scene phraseScene2;
    Scene scene1;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.left_button_fragment, container, false);

        sceneRoot = (ViewGroup) v.findViewById(R.id.scene_root);
        wordScene2 = Scene.getSceneForLayout(sceneRoot, R.layout.word_scene2, getActivity());
        testScene2 = Scene.getSceneForLayout(sceneRoot, R.layout.test_scene2, getActivity());
        phraseScene2 = Scene.getSceneForLayout(sceneRoot, R.layout.phrase_scene2, getActivity());
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene1, getActivity());

        initGUI();
        return v;
    }

    private void initGUI() {
        mTestLeftButton = v.findViewById(R.id.test_left_button);
        mWordLeftButton = v.findViewById(R.id.word_left_button);
        mPhraseLeftButton = v.findViewById(R.id.phrase_left_button);
/*        switch (((CardsForStuduing) getActivity()).getActiveFragmentName()){
            case "CardsFragment":
                mWordLeftButton.setEnabled(false);
                //mWordLeftButton.setClickable(false);
                break;
            case "PhraseFragment":
                mPhraseLeftButton.setEnabled(false);
                //mPhraseLeftButton.setClickable(false);
                break;
            case "TestFragment":
                mTestLeftButton.setEnabled(false);
                //mTestLeftButton.setClickable(false);
                break;
        }*/
        setListener();
    }

    private void setListener() {
        mTestLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    if (!((CardsForStuduing) getActivity()).getActiveFragmentName().equals("TestFragment")) {
                        ((CardsForStuduing) getActivity()).startFragment(new TestFragment());
                    }
                    beginAutoTransition(testScene2, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene1, 200);
                return true;
            }
        });

        mWordLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    if (!((CardsForStuduing) getActivity()).getActiveFragmentName().equals("CardsFragment")) {
                        ((CardsForStuduing) getActivity()).startFragment(new CardsFragment());
                    }
                    beginAutoTransition(wordScene2, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene1, 200);
                return true;
            }
        });

        mPhraseLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                while (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e(TAG,"onTouch()" + event.getAction());
                    if (!((CardsForStuduing) getActivity()).getActiveFragmentName().equals("PhraseFragment")) {
                        ((CardsForStuduing) getActivity()).startFragment(new PhraseFragment());
                    }
                    beginAutoTransition(phraseScene2, 200);
                    return true;
                }
                Log.e(TAG,"onTouch()" + event.getAction());
                beginAutoTransition(scene1, 200);
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
