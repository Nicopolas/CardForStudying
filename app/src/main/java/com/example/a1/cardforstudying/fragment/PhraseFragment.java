package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.Phrase;
import com.example.a1.cardforstudying.model.PhraseLab;
import com.example.a1.cardforstudying.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhraseFragment extends BaseFragment implements TextToSpeech.OnInitListener {
    private final String TAG = getClass().getSimpleName();
    private static TextToSpeech tts;
    private View v;
    private List<Phrase> mListPhrase = new ArrayList<>();
    private TextView mPhraseMeaning;
    private TextView mPhraseTranslation;
    private ImageButton mSpeechButton;
    private ImageButton mEditPhraseButton;
    private int phraseIndex;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        v = inflater.inflate(R.layout.phrase_fragment, container, false);
        phraseIndex = ((CardsForStudying) getActivity()).phraseIndex;
        checkActiveDictionary();
        initGUI();
        setListener();
        showPhrase();
        return v;
    }

    @Override
    public void putDataInElements(){
        mPhraseMeaning.setText(mListPhrase.get(phraseIndex).getPhraseMeaning());
        mPhraseTranslation.setText(mListPhrase.get(phraseIndex).getPhraseTranslation());
    }

    @Override
    public void onStart(){
        showPhrase();
        super.onStart();
    }

    @Override
    public void onStop(){
        ((CardsForStudying) getActivity()).phraseIndex = phraseIndex;
        stopTalking();
        super.onStop();
    }

    private void initGUI() {
        mNextButton = v.findViewById(R.id.next_button);
        mPreviousButton = v.findViewById(R.id.previous_button);
        mPhraseMeaning = v.findViewById(R.id.phrase_meaning_view);
        mPhraseTranslation = v.findViewById(R.id.phrase_translation_view);
        mSpeechButton = v.findViewById(R.id.speech_button);
        mEditPhraseButton = v.findViewById(R.id.edit_phrase_button);
    }

    private void setListener() {
        mNextButton.setOnClickListener(view -> showPhrase(true));

        mPreviousButton.setOnClickListener(view -> showPhrase(false));

        mPhraseMeaning.setOnClickListener(view -> showPhrase(true));

        mPhraseTranslation.setOnClickListener(view -> showPhrase(true));

        mSpeechButton.setOnClickListener(view -> tts = new TextToSpeech(getActivity(), this));

        mEditPhraseButton.setOnClickListener(view -> ((CardsForStudying) getActivity()).startFragmentWithParameter(new PhraseEditFragment(),
                DictionaryLab.get(getActivity()).getActiveDictionary().getDictionaryID(),
                mListPhrase.get(index).getPhraseID()));
    }

    private void showPhrase(boolean nextPhrase) {
        if (nextPhrase) {
            phraseIndex = (phraseIndex + 1) % mListPhrase.size();
        } else {
            phraseIndex--;
            if (phraseIndex == -1) {
                phraseIndex = mListPhrase.size() - 1;
            }
        }
        showPhrase();
    }

    private void showPhrase() {
        mListPhrase = PhraseLab.get(getActivity()).getPhrases();
        if (mListPhrase.isEmpty()) {
            Log.e(TAG, getResources().getString(R.string.err_empty_phrase_dictionary));
            return;
        }
        this.putDataInElements();
    }

    private void checkActiveDictionary() {
        if (DictionaryLab.get(getActivity()).getActiveDictionary() == null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                mSpeechButton.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void speakOut() {
        tts.speak(mListPhrase.get(phraseIndex).getPhraseMeaning(), TextToSpeech.QUEUE_FLUSH, null);
    }

    public static void stopTalking() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
