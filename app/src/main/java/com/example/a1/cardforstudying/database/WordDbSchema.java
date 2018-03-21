package com.example.a1.cardforstudying.database;

import java.util.Map;
import java.util.UUID;

/**
 * Created by User on 17.03.2018.
 */

public class WordDbSchema {

    public static final class WordTable{
        public static final String NAME = "word";

        public static final class Cols {

            public static final String UUID = "word_id";
            public static final String mMeaningWord = "meaning_word";
            public static final String mMeaningWordTranscription = "meaning_word_transcription";
            public static final String mTranslationWord = "translation_word";
            public static final String mRatingWord = "rating_word";
            public static final String mInTest = "in_test";
        }
    }
}
