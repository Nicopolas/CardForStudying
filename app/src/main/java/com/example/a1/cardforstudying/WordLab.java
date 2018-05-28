package com.example.a1.cardforstudying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1.cardforstudying.database.DbSchema.WordTable;
import com.example.a1.cardforstudying.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordLab {
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;
    private static WordLab sWordLab; //обьект синглтона

    private List<Word> mWord = null;
    public static final String dataRu = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩИЙЭЮЯабвгдеёжзиклмнопрстуфхцчшщийэюя";
    public static final String dataEng = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    public static final ArrayList<String> dataEngTranscr = new ArrayList<String>() {{
        add("ei ");
        add("bi: ");
        add("si: ");
        add("di: ");
        add("i: ");
        add("ef ");
        add("ai ");
        add("Λ ");
        add("a: ");
        add("i ");
        add("i: ");
        add("o ");
        add("o: ");
        add("u ");
        add("u: ");
        add("e ");
        add("ε: ");
        add("əu ");
        add("au ");
        add("ei ");
        add("oi ");
        add("ai ");
        add("æ ");
        add("ə ");
        add("∫ ");
        add("r ");
        add("о ");
        add("θ ");
        add("ð ");
        add("ŋ ");
        add("w ");
    }};

    public static WordLab get(Context context) {
        if (sWordLab == null) {
            mSQLiteHelper = new SQLiteHelper(context);
            sWordLab = new WordLab(context);
        }
        return sWordLab; //возвращения (единственного) статичного обьекта
    }

    private WordLab(Context context) { //закрытый конструктор, вызывается только из get()
        open();
        if (mWord == null) {
            mWord = new ArrayList<>();
            putTestWordsList();
            return;
        }
        mWord = getAllWordFromDataBase();

    }

    public void open() throws SQLException {
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    public static void close() {
        mSQLiteHelper.close();
    }

    public List<Word> getWord() {
        return mWord;
    }

    public Word getWord(int mWordId) {
        for (Word word : mWord) {
            if (word.getWordId() == (mWordId)) {
                return word;
            }
        }
        return null;
    }

    public static String getRandomString(int length, String data) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(data.length() - 1);
            str.append(data.substring(index, index + 1));
        }
        return str.toString();
    }

    public static String getRandomString(int length, ArrayList<String> data) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(data.size() - 1);
            str.append(data.get(index));
        }
        return str.toString();
    }

    private void saveWordInDateBase(Word word) {
        ContentValues editedWord = new ContentValues();
        editedWord.put(WordTable.Cols.WordID, getLastIDWordFromDataBase() + 1);
        editedWord.put(WordTable.Cols.MeaningWord, word.getMeaningWord());
        editedWord.put(WordTable.Cols.MeaningWordTranscription, word.getMeaningWordTranscription());
        editedWord.put(WordTable.Cols.TranslationWord, word.getTranslationWord());
        editedWord.put(WordTable.Cols.RatingWord, word.getRatingWord());
        editedWord.put(WordTable.Cols.InTest, word.isInTest());
        mDataBase.insert(WordTable.NAME, null, editedWord);
    }

    private int getLastIDWordFromDataBase() {
        List<Word> mWord = getAllWordFromDataBase();

        return 0;
    }

    private List<Word> getAllWordFromDataBase() {
        List<Word> mWord = new ArrayList<Word>();
        Cursor cursor = mDataBase.query(WordTable.NAME,
                WordTable.Cols.wordAllColumn, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            mWord.add(word);
            cursor.moveToNext();
        }
        return mWord;
    }

    private Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setWordId(cursor.getInt(0));
        word.setMeaningWord(cursor.getString(1));
        word.setMeaningWordTranscription(cursor.getString(2));
        word.setTranslationWord(cursor.getString(3));
        word.setRatingWord(cursor.getInt(4));
        word.setInTest(Boolean.valueOf(cursor.getString(5)));
        return word;
    }

    @Deprecated
    private void putTestWordsList() { //тестовые данные (незабыть удалить)

        for (int i = 0; i < 10; i++) {
            Word word = new Word();
            word.setMeaningWord(getRandomString(1, this.dataEng).toUpperCase() + getRandomString(5, this.dataEng).toLowerCase());
            word.setMeaningWordTranscription("[" + getRandomString(6, this.dataEngTranscr) + "]");
            word.setTranslationWord("сущ.: " + getRandomString(8, this.dataRu).toLowerCase() + ", " + getRandomString(6, this.dataRu).toLowerCase() +
                    "глаг.: " + getRandomString(8, this.dataRu).toLowerCase() +
                    "прил.: " + getRandomString(6, this.dataRu).toLowerCase());
            word.setExample(getRandomString(1, this.dataEng).toUpperCase() + getRandomString(4, this.dataEng).toLowerCase() + " " + getRandomString(6, this.dataEng).toLowerCase() + " " + getRandomString(6, this.dataEng).toLowerCase()
                    , getRandomString(1, this.dataRu).toUpperCase() + getRandomString(4, this.dataRu).toLowerCase() + " " + getRandomString(6, this.dataRu).toLowerCase() + " " + getRandomString(6, this.dataRu).toLowerCase());
            word.setInTest(true);
            mWord.add(word);
            saveWordInDateBase(word);
        }
    }
}
