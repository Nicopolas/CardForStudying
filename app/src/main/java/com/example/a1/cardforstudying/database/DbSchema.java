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

    public static final class ExampleTable {
        public static final String NAME = "example";

        public static final class Cols {
            public static final String ExampleID = "example_id";
            public static final String WordIDFK = "word_id";
            public static final String ExampleMeaning = "example_meaning";
            public static final String ExampleTranslation = "example_translation";
        }
    }

    public static final class PhraseTable {
        public static final String NAME = "phrase";

        public static final class Cols {

        }
    }
}
