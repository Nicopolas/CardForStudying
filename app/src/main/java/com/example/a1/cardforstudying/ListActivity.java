package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.a1.cardforstudying.fragment.DictionariesListFragment;
import com.example.a1.cardforstudying.fragment.PhraseFragment;
import com.example.a1.cardforstudying.fragment.PhraseListFragment;
import com.example.a1.cardforstudying.fragment.WordsListFragment;

import java.util.Locale;

/**
 * Created by 1 on 08.06.2018.
 */

public class ListActivity extends AppCompatActivity implements ActionBar.TabListener {
    private final String TAG = getClass().getSimpleName();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public FragmentManager fm = getSupportFragmentManager();
    private Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    private ActionBar actionBar;

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
        if (nameFragment.getClass().getSimpleName().equals("WordsListFragment")) {
            initTab();
            return;
        }

        fragment = nameFragment;
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
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
                case "WordsListFragment":
                    while (actionBar.getTabCount() != 0) {
                        actionBar.removeTab(actionBar.getTabAt(0));
                    }
                    actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
                    setContentView(R.layout.activity_list);
                    startFragment(new DictionariesListFragment());
                    return;
                default:
                    super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    private void initTab() {
        setContentView(R.layout.tabs_view_pager);
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void makeToast(int string_id) {
        Toast toast = Toast.makeText(this, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        switch (tab.getPosition()) {// не работает
            case 0:
                getSupportActionBar().setTitle(getString(R.string.word_fragment_title));
            case 1:
                getSupportActionBar().setTitle(getString(R.string.phrase_fragment_title));
        }
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = new WordsListFragment();
                    Bundle bundleWord = new Bundle();
                    //args.putInt(WordsListFragment.ARG_SECTION_NUMBER, position + 1); //сюда dictionaryID
                    fragment.setArguments(bundleWord);
                    return fragment;
                case 1:
                    fragment = new PhraseListFragment();
                    Bundle bundlePhrase = new Bundle();
                    //args.putInt(WordsListFragment.ARG_SECTION_NUMBER, position + 1); //сюда dictionaryID
                    fragment.setArguments(bundlePhrase);
                    return fragment;
            }
            return null; //спорно
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.word_fragment_title).toUpperCase(l);
                case 1:
                    return getString(R.string.phrase_fragment_title).toUpperCase(l);
            }
            return null;
        }
    }
}
