package com.example.a1.cardforstudying.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.Word;
import com.example.a1.cardforstudying.model.WordLab;

import java.util.List;

/**
 * Created by 1 on 16.06.2018.
 */

public class WordsListFragment extends Fragment {
    public int dictionaryID = 0;
    private final String TAG = getClass().getSimpleName();
    View view;
    RecyclerView wordsList;
    WordAdapter adapter;


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

    private void initGUI(){
        ((ListActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.word_fragment_title));
        wordsList = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        adapter = new WordAdapter(WordLab.get(getActivity()).getAllWordByDictionaryID(dictionaryID));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        wordsList.setAdapter(adapter);
        wordsList.setLayoutManager(linearLayoutManager);
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
                addWord();
                makeToast("Открывается страница создания Word");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class WordAdapter extends RecyclerView.Adapter<WordHolder> {
        List<Word> words;

        public WordAdapter(List<Word> words) {
            this.words = words;
            notifyDataSetChanged();
        }

        @Override
        public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
            return new WordHolder(listItemView);
        }

        @Override
        public void onBindViewHolder(final WordHolder holder, int position) {
            final Word word = words.get(holder.getAdapterPosition());

            //добавить background активному словарю
            //holder.listItemCardView.setBackground(R.id.);
            holder.wordTextView.setText(word.getMeaningWord());
            holder.listItemCardView.setOnClickListener(view -> {
                makeToast("открытие словаря");
                //открытие словаря
            });
            holder.deleteMenuImageView.setOnClickListener(view -> deleteWord(word));
        }

        @Override
        public int getItemCount() {
            if (words != null && words.size() != 0) {
                return words.size();
            }
            return 0;
        }
    }

    static class WordHolder extends RecyclerView.ViewHolder {
        TextView wordTextView;
        CardView listItemCardView;
        ImageView deleteMenuImageView;

        public WordHolder(View itemView) {
            super(itemView);
            wordTextView = (TextView) itemView.findViewById(R.id.word_list_item_title_text_view);
            listItemCardView = (CardView) itemView.findViewById(R.id.word_list_item_card_view);
            deleteMenuImageView = (ImageView) itemView.findViewById(R.id.word_list_item_delete_menu);
        }
    }

    private void addWord(){

    }

    private void deleteWord(Word word){
        WordLab.get(getActivity()).removeWord(word);
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
        toast.show();
    }

}
