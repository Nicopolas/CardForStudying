package com.example.a1.cardforstudying;

import android.content.Context;
import android.speech.tts.TextToSpeech;

/**
 * Created by user on 08.05.2018.
 */

public class TextToSpeechHelper implements TextToSpeech.OnInitListener {
    private static TextToSpeech tts;

    @Override
    public void onInit(int status) {
    }

    public static void speakOut(Context context, String text) {
        tts = new TextToSpeech(context, (TextToSpeech.OnInitListener) context);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public static void stopTalking() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
