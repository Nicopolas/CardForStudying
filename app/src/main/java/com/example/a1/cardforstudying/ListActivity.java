package com.example.a1.cardforstudying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.example.a1.cardforstudying.fragment.DictionariesListFragment;
import com.example.a1.cardforstudying.fragment.PhraseEditFragment;
import com.example.a1.cardforstudying.fragment.PhraseListFragment;
import com.example.a1.cardforstudying.fragment.WordEditFragment;
import com.example.a1.cardforstudying.fragment.WordsListFragment;

/**
 * Created by 1 on 08.06.2018.
 */

public class ListActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    ViewPager mViewPager;

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
        if (nameFragment.getClass().getSimpleName().equals("WordsListFragment") || nameFragment.getClass().getSimpleName().equals("PhraseListFragment")) {
            initTabs();
            return;
        } else {
            removeTabs();
        }

        fragment = nameFragment;
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void startWordEditFragmentWithParameter(Fragment nameFragment, int dictionaryID, int elementID) {
        fragment = nameFragment;
        Bundle bundleWord = new Bundle();
        bundleWord.putString(fragment.getClass().getSimpleName() + "_dictionaryID", String.valueOf(dictionaryID));
        bundleWord.putString(fragment.getClass().getSimpleName() + "_elementID", String.valueOf(elementID));
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
                case "PhraseListFragment":
                case "WordsListFragment":
                    startFragment(new DictionariesListFragment());
                    return;
                case "WordEditFragment":
                    dictionaryID = ((WordEditFragment) fragment).getDictionaryID();
                    fm.beginTransaction().remove(fragment).commit();
                    initTabs();
                    return;
                case "PhraseEditFragment":
                    dictionaryID = ((PhraseEditFragment) fragment).getDictionaryID();
                    fm.beginTransaction().remove(fragment).commit();
                    initTabs();
                default:
                    super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    private void removeTabs() {
        if (actionBar.getNavigationMode() != 0) {
            while (actionBar.getTabCount() != 0) {
                actionBar.removeTab(actionBar.getTabAt(0));
            }
            actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
            setContentView(R.layout.activity_list);
            startFragment(new DictionariesListFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void makeToast(int string_id) {
        Toast toast = Toast.makeText(this, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    WordsListFragment tab1 = new WordsListFragment();
                    return tab1;
                case 1:
                    PhraseListFragment tab2 = new PhraseListFragment();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
