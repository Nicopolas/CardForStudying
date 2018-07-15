package com.example.a1.cardforstudying.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1.cardforstudying.database.DbSchema;
import com.example.a1.cardforstudying.database.DbSchema.WordTable;
import com.example.a1.cardforstudying.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WordLab {
    private static SQLiteHelper mSQLiteHelper;
    private static WordLab sWordLab; //обьект синглтона
    private static DictionaryLab sDictionaryLab;
    private SQLiteDatabase mDataBase;
    private final String TAG = getClass().getSimpleName();

    private List<Word> mWord = null;

    public static WordLab get(Context context) {
        if (sWordLab == null) {
            sDictionaryLab = DictionaryLab.get(context);
            mSQLiteHelper = new SQLiteHelper(context);
            sWordLab = new WordLab(context);
        }
        return sWordLab; //возвращения (единственного) статичного обьекта
    }

    private WordLab(Context context) { //закрытый конструктор, вызывается только из get()
        open();
        refreshWords();
    }

    public void open() throws SQLException {
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    public void close() {
        mSQLiteHelper.close();
    }

    public List<Word> getWords() {
        refreshWords();
        return mWord;
    }

    public Word getWordByID(int mWordId) {
        for (Word word : getAllWordFromDataBase()) {
            if (word.getWordId() == (mWordId)) {
                return word;
            }
        }
        return null;
    }

    public void putFirstDictionary(List<Word> words) {
        saveWordInDateBase(words);
    }

    public void removeWord(Word word) {
        mDataBase.delete(WordTable.NAME,
                WordTable.Cols.WordID + " = ?",
                new String[]{String.valueOf(word.getWordId())});
        refreshWords();
    }

    public void removeWordsByDictionaryID(int id) {
        mDataBase.delete(WordTable.NAME,
                WordTable.Cols.DictionaryID + " = ?",
                new String[]{String.valueOf(id)});
        refreshWords();
    }

    public void saveWordInDateBase(Word word) {
        if (getWordByID(word.getWordId()) != null) {
            removeWord(word);
        }

        ContentValues editedWord = new ContentValues();
        editedWord.put(WordTable.Cols.WordID, getNextIDWordFromDataBase());
        editedWord.put(WordTable.Cols.MeaningWord, word.getMeaningWord());
        editedWord.put(WordTable.Cols.MeaningWordTranscription, word.getMeaningWordTranscription());
        editedWord.put(WordTable.Cols.TranslationWord, word.getTranslationWord());
        editedWord.put(WordTable.Cols.RatingWord, word.getRatingWord());
        editedWord.put(WordTable.Cols.InTest, String.valueOf(word.isInTest()));
        editedWord.put(WordTable.Cols.DictionaryID, word.getDictionaryID());
        mDataBase.insert(WordTable.NAME, null, editedWord);
        refreshWords();
    }

    private void refreshWords() {
        mWord = new ArrayList<>();
        mWord.addAll(getAllWordFromActiveDictionary());
    }

    private void saveWordInDateBase(List<Word> words) {
        for (Word word : words) {
            saveWordInDateBase(word);
        }
    }

    private int getNextIDWordFromDataBase() {
        if (getAllWordFromDataBase().isEmpty()) {
            return 0;
        }
        List<Word> mWord = getAllWordFromDataBase();
        List<Integer> wordSort = new ArrayList<>();
        for (Word word : mWord) {
            wordSort.add(word.getWordId());
        }
        Collections.reverse(wordSort);
        return wordSort.get(0) + 1;
    }

    public List<Word> getAllWordByDictionaryID(int id) {
        String dictionaryID = String.valueOf(id);
        List<Word> mWord = new ArrayList<Word>();
        Cursor cursor = mDataBase.query(WordTable.NAME,
                WordTable.Cols.wordAllColumn,
                DbSchema.WordTable.Cols.DictionaryID + " = ?",
                new String[]{dictionaryID},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            mWord.add(word);
            cursor.moveToNext();
        }
        return mWord;
    }

    private List<Word> getAllWordFromActiveDictionary() {
        if (sDictionaryLab.getActiveDictionary() == null) {
            return new ArrayList<Word>();
        }
        String activeDictionaryID = String.valueOf(sDictionaryLab.getActiveDictionary().getDictionaryID());
        List<Word> mWord = new ArrayList<Word>();
        Cursor cursor = mDataBase.query(WordTable.NAME,
                WordTable.Cols.wordAllColumn,
                DbSchema.WordTable.Cols.DictionaryID + " = ?",
                new String[]{activeDictionaryID},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            mWord.add(word);
            cursor.moveToNext();
        }
        return mWord;
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
        word.setDictionaryID(cursor.getInt(6));
        return word;
    }
}
