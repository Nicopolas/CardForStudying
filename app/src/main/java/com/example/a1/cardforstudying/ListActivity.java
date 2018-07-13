package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.example.a1.cardforstudying.fragment.DictionariesListFragment;

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
            startFragment(new DictionariesListFragment());
        }

    }

    public void startFragment(Fragment nameFragment) {
        fragment = nameFragment;
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public String getActiveFragmentName() {
        return fragment.getClass().getSimpleName();
    }

    //Добавление меню в action bar в активность
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            switch (getActiveFragmentName()) {
                case "WordsListFragment":
                    startFragment(new DictionariesListFragment());
                    return;
                default:
                    super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    private void makeToast(int string_id) {
        Toast toast = Toast.makeText(this, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
