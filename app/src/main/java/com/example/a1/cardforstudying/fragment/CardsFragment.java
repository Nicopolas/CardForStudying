package com.example.a1.cardforstudying.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a1.cardforstudying.CardsForStudying;
import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.WordLab;

import java.util.Locale;

public class CardsFragment extends BaseFragment implements TextToSpeech.OnInitListener {
    private final String TAG = getClass().getSimpleName();
    private static TextToSpeech tts;
    private View v;
    private TextView mWordTextView;
    private TextView mWordTranscriptionView;
    private TextView mWordTranslationView;
    private Button mExampleButton;
    private ImageButton mSpeechButton;
    private ImageButton mInTestButton;
    private ImageButton mEditWordButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        v = inflater.inflate(R.layout.cards_fragment, container, false);
        checkActiveDictionary();
        initGUI();
        setListener();
        showWord();
        return v;
    }

    @Override
    public void onResume() {
        showWord();
        super.onResume();
    }

    private void initGUI() {
        mWordTextView = v.findViewById(R.id.word_text_view);
        mWordTranscriptionView = v.findViewById(R.id.word_transcription_view);
        mWordTranslationView = v.findViewById(R.id.word_translation_view);
        mExampleButton = v.findViewById(R.id.example_button);
        mSpeechButton = v.findViewById(R.id.speech_button);
        mInTestButton = v.findViewById(R.id.in_test_button);
        mEditWordButton = v.findViewById(R.id.edit_word_button);
        mNextButton = v.findViewById(R.id.next_button);
        mPreviousButton = v.findViewById(R.id.previous_button);
    }

    private void setListener() {
        mWordTextView.setOnClickListener(view -> showWord(true));

        mWordTranscriptionView.setOnClickListener(view -> showWord(true));

        mWordTranslationView.setOnClickListener(view -> showWord(true));

        mExampleButton.setOnClickListener(view -> {
            makeToast(R.string.err_inDeveloping);//в разработке
        });

        mSpeechButton.setOnClickListener(view -> tts = new TextToSpeech(getActivity(), this));

        mInTestButton.setOnClickListener(view -> {
            if (mDataList.get(index).isInTest()) {
                mDataList.get(index).setInTest(false);
                WordLab.get(getActivity()).saveWordInDateBase(mDataList.get(index));
                makeToast(R.string.remove_word_in_the_test);
                setInTestButtonImage(false);
                return;
            }
            mDataList.get(index).setInTest(true);
            WordLab.get(getActivity()).saveWordInDateBase(mDataList.get(index));
            makeToast(R.string.set_word_in_the_test);
            setInTestButtonImage(true);
        });

        mEditWordButton.setOnClickListener(view -> ((CardsForStudying) getActivity()).startFragmentWithParameter(new WordEditFragment(),
                DictionaryLab.get(getActivity()).getActiveDictionary().getDictionaryID(),
                mDataList.get(index).getWordId()));

        mNextButton.setOnClickListener(view -> showWord(true));

        mPreviousButton.setOnClickListener(view -> showWord(false));
    }

    private void checkActiveDictionary() {
        if (DictionaryLab.get(getActivity()).getActiveDictionary() == null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        stopTalking();
        ((CardsForStudying) getActivity()).index = index;
        super.onStop();
    }

    public void putDataInElements() {
        mWordTextView.setText(upFirsLetter(mDataList.get(index).getMeaningWord()));
        mWordTranscriptionView.setText(mDataList.get(index).getMeaningWordTranscription());
        mWordTranslationView.setText(mDataList.get(index).getTranslationWord().replace("сущ.:", "\nсущ.:").replace("глаг.:", "\nглаг.:").replace("прил.:", "\nприл.:"));
        setInTestButtonImage(mDataList.get(index).isInTest());
    }

    private void setInTestButtonImage(boolean inTest) {
        if (inTest) {
            mInTestButton.setImageResource(R.drawable.outline_close_black_48);
            return;
        }
        mInTestButton.setImageResource(R.drawable.baseline_done_black_48);
    }

    private String upFirsLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
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
            Log.e("TTS", "Initialization Failed!");
        }
    }

    public void speakOut() {
        tts.speak(mDataList.get(index).getMeaningWord(), TextToSpeech.QUEUE_FLUSH, null);
    }

    public static void stopTalking() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
