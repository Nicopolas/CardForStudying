package com.example.a1.cardforstudying;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 1 on 08.06.2018.
 */

public class ListActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    public FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (fragment == null) {
            startFragment(new DialogFragment());
        }

    }

    public void startFragment(Fragment nameFragment) {
        fragment = nameFragment;
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

}
