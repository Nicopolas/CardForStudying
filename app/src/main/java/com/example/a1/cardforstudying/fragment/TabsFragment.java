package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;

/**
 * Created by 1 on 15.07.2018.
 */

public class TabsFragment extends Fragment { // не работает
    private final String TAG = getClass().getSimpleName();
    View view;
    WordsListFragment wordsListFragment;
    PhraseListFragment phraseListFragment;
    int position;
    int dictionaryID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.tabs_view_pager, container, false);

        setHasOptionsMenu(true);
        ((ListActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String _dictionaryID = getArguments().getString("_dictionaryID");
        if (_dictionaryID == null) {
            Log.e(TAG, "Не получен dictionaryID с предыдущего обьекта");
            back();
            //сюда вывод универсального врагмента с ошибкой
        }
        dictionaryID = Integer.valueOf(_dictionaryID);
        ((ListActivity) getActivity()).getSupportActionBar().setTitle(DictionaryLab.get(getActivity()).getDictionaryByID(dictionaryID).getDictionaryName());

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.word_fragment_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.phrase_fragment_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                return true;
            case android.R.id.home:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addElement() {
        switch (position) {
            case 0:
                wordsListFragment.addWord();
                break;
            case 1:
                phraseListFragment.addPhrase();
                break;
            default:
                break;
        }
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
                    wordsListFragment = tab1;
                    return tab1;
                case 1:
                    PhraseListFragment tab2 = new PhraseListFragment();
                    Bundle bundlePhrase = new Bundle();
                    bundlePhrase.putString("_dictionaryID", String.valueOf(dictionaryID));
                    tab2.setArguments(bundlePhrase);
                    phraseListFragment = tab2;
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

    private void back() {
        getActivity().onBackPressed();
    }
}

