package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;

/**
 * Created by 1 on 15.07.2018.
 */

public class TabsFragment extends Fragment { // не работает
    private final String TAG = getClass().getSimpleName();
    View view;
    ViewPager mViewPager;
    int dictionaryID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.tabs_view_pager, container, false);

        String _dictionaryID = getArguments().getString( "_dictionaryID");
        if (_dictionaryID == null){
            Log.e(TAG, "Не получен dictionaryID с предыдущего обьекта");
            //сюда вывод универсального врагмента с ошибкой
        }
        dictionaryID = Integer.valueOf(_dictionaryID);

        //final ActionBar actionBar = ((ListActivity) getActivity()).getSupportActionBar();
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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
        return view;
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
                    Bundle bundleWord = new Bundle();
                    bundleWord.putString("_dictionaryID", String.valueOf(dictionaryID));
                    tab1.setArguments(bundleWord);
                    return tab1;
                case 1:
                    PhraseListFragment tab2 = new PhraseListFragment();
                    Bundle bundlePhrase = new Bundle();
                    bundlePhrase.putString("_dictionaryID", String.valueOf(dictionaryID));
                    tab2.setArguments(bundlePhrase);
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

