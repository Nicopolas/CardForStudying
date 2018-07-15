package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.Phrase;
import com.example.a1.cardforstudying.model.PhraseLab;
import com.example.a1.cardforstudying.model.Word;
import com.example.a1.cardforstudying.model.WordLab;

import java.util.List;

/**
 * Created by 1 on 15.07.2018.
 */

public class PhraseListFragment extends Fragment {
    public int dictionaryID = 0;
    private final String TAG = getClass().getSimpleName();
    private View view;
    RecyclerView phraseList;
    PhraseAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        view = inflater.inflate(R.layout.list_fragment, container, false);
        initGUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initGUI() {
        //((ListActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.phrase_fragment_title));
        phraseList = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        adapter = new PhraseAdapter(PhraseLab.get(getActivity()).getAllWordByDictionaryID(dictionaryID));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        phraseList.setAdapter(adapter);
        phraseList.setLayoutManager(linearLayoutManager);
    }

    // обработка нажатий в action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addPhrase();
                makeToast("Открывается страница создания Word");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class PhraseAdapter extends RecyclerView.Adapter<PhraseHolder> {
        List<Phrase> phrases;

        public PhraseAdapter(List<Phrase> phrases) {
            this.phrases = phrases;
            notifyDataSetChanged();
        }

        @Override
        public PhraseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.phrase_list_item, parent, false);
            return new PhraseHolder(listItemView);
        }

        @Override
        public void onBindViewHolder(final PhraseHolder holder, int position) {
            final Phrase phrase = phrases.get(holder.getAdapterPosition());

            //добавить background активному словарю
            //holder.listItemCardView.setBackground(R.id.);
            holder.phraseTextView.setText(phrase.getPhraseMeaning());
            holder.listItemCardView.setOnClickListener(view -> {
                makeToast("дкталки фразы");
                //открытие словаря
            });
            holder.deleteMenuImageView.setOnClickListener(view -> deletePhrase(phrase));
        }

        @Override
        public int getItemCount() {
            if (phrases != null && phrases.size() != 0) {
                return phrases.size();
            }
            return 0;
        }
    }

    static class PhraseHolder extends RecyclerView.ViewHolder {
        TextView phraseTextView;
        CardView listItemCardView;
        ImageView deleteMenuImageView;

        public PhraseHolder(View itemView) {
            super(itemView);
            phraseTextView = (TextView) itemView.findViewById(R.id.phrase_list_item_title_text_view);
            listItemCardView = (CardView) itemView.findViewById(R.id.phrase_list_item_card_view);
            deleteMenuImageView = (ImageView) itemView.findViewById(R.id.phrase_list_item_delete_menu);
        }
    }

    private void addPhrase() {

    }

    private void deletePhrase(Phrase phrase) {
        PhraseLab.get(getActivity()).removePhrase(phrase);
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
        toast.show();
    }

}
