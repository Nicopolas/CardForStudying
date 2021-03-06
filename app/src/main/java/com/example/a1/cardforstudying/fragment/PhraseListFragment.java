package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.cardforstudying.ListActivity;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.Phrase;
import com.example.a1.cardforstudying.model.PhraseLab;

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
        view = inflater.inflate(R.layout.list_fragment_for_tabbed_pages, container, false);
        initGUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initGUI() {
        String _dictionaryID = getArguments().getString("_dictionaryID");
        if (_dictionaryID == null) {
            Log.e(TAG, "Не получен dictionaryID с предыдущего обьекта");
            //сюда вывод универсального врагмента с ошибкой
        }
        dictionaryID = Integer.valueOf(_dictionaryID);

        phraseList = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        adapter = new PhraseAdapter(PhraseLab.get(getActivity()).getAllWordByDictionaryID(dictionaryID));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        phraseList.setAdapter(adapter);
        phraseList.setLayoutManager(linearLayoutManager);
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

            holder.phraseTextView.setText(phrase.getPhraseMeaning());
            holder.listItemCardView.setOnClickListener(view -> {
                editPhrase(phrase);
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

    private void editPhrase(Phrase phrase) {
        ((ListActivity) getActivity()).startFragmentWithParameter(new PhraseEditFragment(), dictionaryID, phrase.getPhraseID());
    }

    public void addPhrase() {
        ((ListActivity) getActivity()).startFragmentWithParameter(new PhraseEditFragment(), dictionaryID);
    }

    private void deletePhrase(Phrase phrase) {
        PhraseLab.get(getActivity()).removePhrase(phrase);
        initGUI();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
