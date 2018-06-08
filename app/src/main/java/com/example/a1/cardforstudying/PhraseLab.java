package com.example.a1.cardforstudying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a1.cardforstudying.database.DbSchema;
import com.example.a1.cardforstudying.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by 1 on 08.06.2018.
 */

public class PhraseLab {
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;
    private static PhraseLab sPhraseLab;

    private List<Phrase> mPhrase = null;

    public static PhraseLab get(Context context) {
        if (sPhraseLab == null) {
            mSQLiteHelper = new SQLiteHelper(context);
            sPhraseLab = new PhraseLab(context);
        }
        return sPhraseLab; //возвращения (единственного) статичного обьекта
    }

    public List<Phrase> getPhrases(){
        return mPhrase;
    }

    public void open() throws SQLException {
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    public static void close() {
        mSQLiteHelper.close();
    }

    private PhraseLab(Context context) { //закрытый конструктор, вызывается только из get()
        open();
        mPhrase = new ArrayList<>();
        if (getAllPhraseFromDataBase().isEmpty()) {
            putTestPhraseList();
        }
        mPhrase.addAll(getAllPhraseFromDataBase());
    }

    private List<Phrase> getAllPhraseFromDataBase() {
        List<Phrase> mPhrase = new ArrayList<Phrase>();
        Cursor cursor = mDataBase.query(DbSchema.PhraseTable.NAME,
                DbSchema.PhraseTable.Cols.phraseAllColumn, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phrase phrase = cursorToPhrase(cursor);
            mPhrase.add(phrase);
            cursor.moveToNext();
        }
        return mPhrase;
    }

    private void savePhraseInDateBase(Phrase phrase) {
        ContentValues editedPhrase = new ContentValues();
        editedPhrase.put(DbSchema.PhraseTable.Cols.PhraseID, getNextIDPhraseFromDataBase());
        editedPhrase.put(DbSchema.PhraseTable.Cols.PhraseMeaning, phrase.getPhraseMeaning());
        editedPhrase.put(DbSchema.PhraseTable.Cols.PhraseTranslation, phrase.getPhraseTranslation());
        mDataBase.insert(DbSchema.PhraseTable.NAME, null, editedPhrase);
    }

    private int getNextIDPhraseFromDataBase() {
        if (getAllPhraseFromDataBase().isEmpty()) {
            return 0;
        }
        List<Phrase> mPhrase = getAllPhraseFromDataBase();
        List<Integer> phraseSort = new ArrayList<>();
        for (Phrase phrase : mPhrase) {
            phraseSort.add(phrase.getPhraseID());
        }
        Collections.reverse(phraseSort);
        return phraseSort.get(0) + 1;
    }

    private Phrase cursorToPhrase(Cursor cursor) {
        Phrase phrase = new Phrase();
        phrase.setPhraseID(cursor.getInt(0));
        phrase.setPhraseMeaning(cursor.getString(1));
        phrase.setPhraseTranslation(cursor.getString(2));
        return phrase;
    }

    @Deprecated
    private void putTestPhraseList() { //тестовые данные (незабыть удалить)
        for (int i = 0; i < 10; i++) {
            Phrase phrase = new Phrase();
            phrase.setPhraseMeaning(WordLab.getRandomString(1, WordLab.dataEng).toUpperCase() + WordLab.getRandomString(4, WordLab.dataEng).toLowerCase() + " " + WordLab.getRandomString(6, WordLab.dataEng).toLowerCase() + " " + WordLab.getRandomString(6, WordLab.dataEng).toLowerCase());
            phrase.setPhraseTranslation(WordLab.getRandomString(1, WordLab.dataRu).toUpperCase() + WordLab.getRandomString(4, WordLab.dataRu).toLowerCase() + " " + WordLab.getRandomString(6, WordLab.dataRu).toLowerCase() + " " + WordLab.getRandomString(6, WordLab.dataRu).toLowerCase());
            savePhraseInDateBase(phrase);
        }
    }
}
