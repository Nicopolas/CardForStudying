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

/**
 * Created by 1 on 08.06.2018.
 */

public class PhraseLab {
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;
    private static PhraseLab sPhraseLab;
    private static DictionaryLab sDictionaryLab;
    private List<Phrase> mPhrase = null;

    public static PhraseLab get(Context context) {
        if (sPhraseLab == null) {
            sDictionaryLab = DictionaryLab.get(context);
            mSQLiteHelper = new SQLiteHelper(context);
            sPhraseLab = new PhraseLab(context);
        }
        return sPhraseLab; //возвращения (единственного) статичного обьекта
    }

    public List<Phrase> getPhrases() {
        refreshPhrases();
        return mPhrase;
    }

    public void open() throws SQLException {
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    public void close() {
        mSQLiteHelper.close();
    }

    public void putFirstDictionary(List<Phrase> phrases) {
        savePhraseInDateBase(phrases);
    }

    public void removePhrasesByDictionaryID(int id){
        mDataBase.delete(DbSchema.PhraseTable.NAME,
                DbSchema.PhraseTable.Cols.DictionaryID + " = ?",
                new String[]{String.valueOf(id)});
        refreshPhrases();
    }

    private void refreshPhrases(){
        mPhrase = new ArrayList<>();
        mPhrase.addAll(getAllPhraseFromActiveDictionary());
    }

    private PhraseLab(Context context) { //закрытый конструктор, вызывается только из get()
        open();
        refreshPhrases();
    }

    private List<Phrase> getAllPhraseFromActiveDictionary(){
        if (sDictionaryLab.getActiveDictionary() == null){
            return new ArrayList<Phrase>();
        }
        String activeDictionaryID = String.valueOf(sDictionaryLab.getActiveDictionary().getDictionaryID());
        List<Phrase> mPhrase = new ArrayList<Phrase>();
        Cursor cursor = mDataBase.query(DbSchema.PhraseTable.NAME,
                DbSchema.PhraseTable.Cols.phraseAllColumn,
                DbSchema.PhraseTable.Cols.DictionaryID + " = ?",
                new String[]{activeDictionaryID},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phrase phrase = cursorToPhrase(cursor);
            mPhrase.add(phrase);
            cursor.moveToNext();
        }
        return mPhrase;
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
        editedPhrase.put(DbSchema.PhraseTable.Cols.DictionaryID, phrase.getDictionaryID());
        mDataBase.insert(DbSchema.PhraseTable.NAME, null, editedPhrase);
        refreshPhrases();
    }

    private void savePhraseInDateBase(List<Phrase> phrases) {
        for (Phrase phrase : phrases) {
            savePhraseInDateBase(phrase);
        }
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
        phrase.setDictionaryID(cursor.getInt(3));
        return phrase;
    }
}
