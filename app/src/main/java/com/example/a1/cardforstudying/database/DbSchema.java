package com.example.a1.cardforstudying.database;


/**
 * Created by User on 17.03.2018.
 */

public class DbSchema {

    public static final class WordTable {
        public static final String NAME = "word";

        public static final class Cols {
            public static final String WordID = "word_id";
            public static final String MeaningWord = "meaning_word";
            public static final String MeaningWordTranscription = "meaning_word_transcription";
            public static final String TranslationWord = "translation_word";
            public static final String RatingWord = "rating_word";
            public static final String InTest = "in_test";
            public static final String DictionaryID = "dictionary_id";

            public static final String[] wordAllColumn = {
                    WordID,
                    MeaningWord,
                    MeaningWordTranscription,
                    TranslationWord,
                    RatingWord,
                    InTest,
                    DictionaryID
            };
        }
    }

    public static final class PhraseTable {
        public static final String NAME = "phrase";

        public static final class Cols {
            public static final String PhraseID = "phrase_id";
            public static final String PhraseMeaning = "phrase_meaning";
            public static final String PhraseTranslation = "phrase_translation";
            public static final String DictionaryID = "dictionary_id";

            public static final String[] phraseAllColumn = {
                    PhraseID,
                    PhraseMeaning,
                    PhraseTranslation,
                    DictionaryID
            };
        }
    }

    public static final class WordPhraseTable {
        public static final String NAME = "word_phrase";

        public static final class Cols {
            public static final String WordPhraseID = "word_phrase_id";
            public static final String PhraseID = "phrase_id";
            public static final String WordID = "word_id";

            public static final String[] wordPhraseAllColumn = {
                    WordPhraseID,
                    PhraseID,
                    WordID
            };
        }
    }

    public static final class DictionaryTable {
        public static final String NAME = "dictionary";

        public static final class Cols {
            public static final String DictionaryID = "dictionary_id";
            public static final String DictionaryName = "dictionary_name";

            public static final String[] dictionaryAllColumn = {
                    DictionaryID,
                    DictionaryName
            };
        }
    }
}
