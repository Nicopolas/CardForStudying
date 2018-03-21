package com.example.a1.cardforstudying.database;

/**
 * Created by User on 17.03.2018.
 */

public class ExampleDbSchema {

    public static final class ExampleTable {
        public static final String NAME = "example";

        public static final class Cols {
            public static final String ExampleID = "example_id";
            public static final String WordIDFK = "word_id";
            public static final String ExampleMeaning = "example_meaning";
            public static final String ExampleTranslation = "example_translation";
        }
    }
}
