package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.R;

/**
 * Created by user on 29.05.2018.
 */

public class LeftButtonFragment extends Fragment {
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
        Log.e(TAG, "initGUI()");
        mTestLeftButton = v.findViewById(R.id.test_left_button);
        mWordLeftButton = v.findViewById(R.id.word_left_button);
        mPhraseLeftButton = v.findViewById(R.id.phrase_left_button);
        setListener();
    }

    private void setListener() {
        mTestLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((CardsForStudying) getActivity()).getActiveFragmentName().equals("TestFragment")) {
                    ((CardsForStudying) getActivity()).startFragment(new TestFragment());
                }
                beginAutoTransition(testScene2, 200);
            }
        });
        mWordLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((CardsForStudying) getActivity()).getActiveFragmentName().equals("CardsFragment")) {
                    ((CardsForStudying) getActivity()).startFragment(new CardsFragment());
                }
                beginAutoTransition(wordScene2, 200);
            }
        });
        mPhraseLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((CardsForStudying) getActivity()).getActiveFragmentName().equals("PhraseFragment")) {
                    ((CardsForStudying) getActivity()).startFragment(new PhraseFragment());
                }
                beginAutoTransition(phraseScene2, 200);
            }
        });
    }

    private void beginAutoTransition(Scene scene, int durability) {
        Log.e(TAG, "beginAutoTransition()");
        TransitionSet set = new TransitionSet();
        set.addTransition(new Fade());
        set.addTransition(new ChangeBounds());
        // выполняться они будут одновременно
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        // уставим свою длительность анимации
        set.setDuration(durability);
        // и изменим Interpolator
        set.setInterpolator(new AccelerateInterpolator());

        set.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(@NonNull Transition transition) {
                Log.w(TAG, "onTransitionStart()");
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                Log.w(TAG, "onTransitionEnd()");
                beginAutoTransitionBack();
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {
                Log.w(TAG, "onTransitionCancel()");
            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {
                Log.w(TAG, "onTransitionPause()");
            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {
                Log.w(TAG, "onTransitionResume()");
            }
        });

        TransitionManager.go(scene, set);
    }


    private void beginAutoTransitionBack() {
        Log.e(TAG, "beginAutoTransitionBack() ");
        TransitionSet set = new TransitionSet();
        set.addTransition(new Fade());
        set.addTransition(new ChangeBounds());
        // выполняться они будут одновременно
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        // уставим свою длительность анимации
        set.setDuration(300);
        // и изменим Interpolator
        set.setInterpolator(new AccelerateInterpolator());
        TransitionManager.go(scene1, set);
        initGUI();
    }
}
