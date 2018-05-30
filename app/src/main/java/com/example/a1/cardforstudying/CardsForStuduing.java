package com.example.a1.cardforstudying;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.a1.cardforstudying.fragment.CardsFragment;
import com.example.a1.cardforstudying.fragment.EmptyDictionary;
import com.example.a1.cardforstudying.fragment.LeftButtonFragment;
import com.example.a1.cardforstudying.fragment.TestFragment;

import static com.example.a1.cardforstudying.XMLHelper.createFirstDictionary;

public class CardsForStuduing extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final String TAG = "MainActivity";

    public FragmentManager fm = getSupportFragmentManager();
    public Fragment fragment = fm.findFragmentById(R.id.fragment_container);//создание фрагмента

    GestureDetectorCompat mDetector;

    public int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_for_studuing);

        mDetector = new GestureDetectorCompat(this,this);

        createFirstDictionary(this);

        if (fragment == null) {
            startFragment();
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        this.mDetector.onTouchEvent(event);

       Log.d(TAG,"Touch!");//фиксирование касания
       Log.d(TAG,"X:"+event.getX()+" Y:"+event.getY());//получение координат касания
       return super.onTouchEvent(event);
    }


    public void startFragment(){
        Fragment fragment = checkWordLab(new CardsFragment());

        fm.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
        fm.beginTransaction()
                .replace(R.id.left_btn_fragment_container, new LeftButtonFragment())
                .commit();
    }

    public void startFragment(Fragment nameFragment){
        nameFragment = checkWordLab(nameFragment);

        fragment = nameFragment;
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public Fragment checkWordLab(Fragment nameFragment){
        int count = 0;
        if (WordLab.get(this).getWord().isEmpty()&&(nameFragment.toString().contains("CardsFragment")||nameFragment.toString().contains("TestFragment"))){
            EmptyDictionary.mMessage = R.string.empty_dictionary;
            return new EmptyDictionary();
        }

        for (Word each : WordLab.get(this).getWord()){
            if(each.isInTest()){
                count++;
            }
        }

        if(count<4 && nameFragment.toString().contains("TestFragment")){
            EmptyDictionary.mMessage = R.string.fewer_than_four_on_dictionary;
            return new EmptyDictionary();
        }
        return nameFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        TextToSpeechHelper.stopTalking();
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        WordLab.close();
        super.onDestroy();
    }


    //из GestureDetector.OnGestureListener
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown");
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress");
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp");
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG,"onScroll");
        if (fragment.toString().contains("CardsFragment")){//не работает
            startFragment(new TestFragment());
        }
        else {
            if (fragment.toString().contains("TestFragment")) {//почему-то работает
                startFragment(new CardsFragment());
            }
        }
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress");
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG,"onFling");
        return false;
    }
}
