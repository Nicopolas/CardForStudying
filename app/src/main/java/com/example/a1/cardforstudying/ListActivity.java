package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a1.cardforstudying.fragment.DictionariesListFragment;
import com.example.a1.cardforstudying.fragment.TabsFragment;

/**
 * Created by 1 on 08.06.2018.
 */

public class ListActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    public FragmentManager fm = getSupportFragmentManager();
    private Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    private ActionBar actionBar;

    public int dictionaryID = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        actionBar = getSupportActionBar();
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

    public void startFragmentWithParameter(Fragment nameFragment, int dictionaryID, int elementID) {
        fragment = nameFragment;
        Bundle bundleWord = new Bundle();
        bundleWord.putString("_dictionaryID", String.valueOf(dictionaryID));
        bundleWord.putString("_elementID", String.valueOf(elementID));
        fragment.setArguments(bundleWord);

        startFragment(fragment);
    }

    public void startFragmentWithParameter(Fragment nameFragment, int dictionaryID) {
        fragment = nameFragment;
        Bundle bundleWord = new Bundle();
        bundleWord.putString("_dictionaryID", String.valueOf(dictionaryID));
        fragment.setArguments(bundleWord);

        startFragment(fragment);
    }

    public Fragment getActiveFragment() {
        return fragment;
    }

    public String getActiveFragmentName() {
        return fragment.getClass().getSimpleName();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            switch (getActiveFragmentName()) {
                case "TabsFragment":
                case "PhraseListFragment":
                case "WordsListFragment":
                    startFragment(new DictionariesListFragment());
                    return;
                case "WordEditFragment":
                case "PhraseEditFragment":
                    startFragmentWithParameter(new TabsFragment(), dictionaryID);
                    return;
                default:
                    super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                switch (getActiveFragmentName()) {
                    case "DictionariesListFragment":
                        ((DictionariesListFragment) fragment).addDictionary();
                        break;
                    case "TabsFragment":
                        ((TabsFragment) fragment).addElement();
                        break;
                    default:
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void makeToast(int string_id) {
        Toast toast = Toast.makeText(this, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

}
