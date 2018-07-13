package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.model.Dictionary;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;

import java.util.List;

/**
 * Created by 1 on 08.06.2018.
 */

public class DictionariesListFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    View view;
    RecyclerView dictionaryList;
    DictionaryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.list_fragment, container, false);
        setHasOptionsMenu(true);
        initGUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initGUI() {
        ((ListActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.dictionary_fragment_title));
        dictionaryList = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        adapter = new DictionaryAdapter(DictionaryLab.get(getActivity()).getDictionaries());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        dictionaryList.setAdapter(adapter);
        dictionaryList.setLayoutManager(linearLayoutManager);
    }

    //вывод PoPup меню
    private void showPopup(final DictionaryHolder holder, final View v, final int dictionaryID) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_open:
                    openDictionary(dictionaryID);
                    return true;
                case R.id.menu_delete:
                    DictionaryLab.get(getActivity()).removeDictionaryByID(dictionaryID, getActivity());
                    initGUI();
                    return true;
                case R.id.set_active:
                    DictionaryLab.get(getActivity()).setActiveDictionaryByID(dictionaryID);
                    holder.dictionaryImageView.setImageResource(R.drawable.baseline_done_black_48);
                    initGUI();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    //Добавление меню в action bar в фрагмент
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addDictionary();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class DictionaryAdapter extends RecyclerView.Adapter<DictionaryHolder> {
        List<Dictionary> dictionaries;

        public DictionaryAdapter(List<Dictionary> dictionaries) {
            this.dictionaries = dictionaries;
            notifyDataSetChanged();
        }

        @Override
        public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_list_item, parent, false);

            return new DictionaryHolder(listItemView);
        }

        @Override
        public void onBindViewHolder(final DictionaryHolder holder, int position) {
            final Dictionary dictionary = dictionaries.get(holder.getAdapterPosition());
            if (DictionaryLab.get(getActivity()).getActiveDictionary() != null && dictionary.getDictionaryID() == DictionaryLab.get(getActivity()).getActiveDictionary().getDictionaryID()) {
                holder.dictionaryImageView.setImageResource(R.drawable.baseline_done_black_48);
            }

            //добавить background активному словарю
            //holder.listItemCardView.setBackground(R.id.);
            holder.dictionaryTextView.setText(dictionary.getDictionaryName());
            holder.listItemCardView.setOnClickListener(view -> openDictionary(dictionary.getDictionaryID()));
            holder.popupMenuImageView.setOnClickListener(view -> showPopup(holder, view, dictionary.getDictionaryID()));
        }

        @Override
        public int getItemCount() {
            if (dictionaries != null && dictionaries.size() != 0) {
                return dictionaries.size();
            }
            return 0;
        }
    }

    static class DictionaryHolder extends RecyclerView.ViewHolder {
        TextView dictionaryTextView;
        CardView listItemCardView;
        ImageView popupMenuImageView;
        ImageView dictionaryImageView;

        public DictionaryHolder(View itemView) {
            super(itemView);
            dictionaryTextView = (TextView) itemView.findViewById(R.id.dictionary_list_item_title_text_view);
            listItemCardView = (CardView) itemView.findViewById(R.id.dictionary_list_item_card_view);
            popupMenuImageView = (ImageView) itemView.findViewById(R.id.dictionary_list_item_popup_menu);
            dictionaryImageView = (ImageView) itemView.findViewById(R.id.dictionary_list_item_image_view);
        }
    }

    private void addDictionary() {
        NewDictionariesDialog newDictionariesDialog = new NewDictionariesDialog();
        newDictionariesDialog.show(getActivity().getSupportFragmentManager(), "NewDictionariesDialog");
        initGUI();
    }

    private void openDictionary(int dictionaryID) {
        WordsListFragment wordsListFragment = new WordsListFragment();
        wordsListFragment.dictionaryID = dictionaryID;
        ((ListActivity) getActivity()).startFragment(wordsListFragment);
    }

    //методы для дебага
    private void makeToast(int string_id) {
        Toast toast = Toast.makeText(getActivity(), string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
