package com.example.a1.cardforstudying.database;


/**
 * Created by User on 17.03.2018.
 */

public class DbSchema {

    public static final class WordTable{
        public static final String NAME = "word";

        public static final class Cols {

            public static final String WordID = "word_id";
            public static final String MeaningWord = "meaning_word";
            public static final String MeaningWordTranscription = "meaning_word_transcription";
            public static final String TranslationWord = "translation_word";
            public static final String RatingWord = "rating_word";
            public static final String InTest = "in_test";
        }
    }
}
