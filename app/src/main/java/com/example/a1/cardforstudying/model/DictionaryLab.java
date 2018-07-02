package com.example.a1.cardforstudying.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a1.cardforstudying.database.DbSchema;
import com.example.a1.cardforstudying.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 1 on 10.06.2018.
 */

public class DictionaryLab {
    private final String TAG = getClass().getSimpleName();
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;
    private static DictionaryLab sDictionaryLab;
    private List<Dictionary> mDictionaries = null;
    private Dictionary mActiveDictionary = null;

    public static DictionaryLab get(Context context) {
        if (sDictionaryLab == null) {
            mSQLiteHelper = new SQLiteHelper(context);
            sDictionaryLab = new DictionaryLab(context);
        }
        return sDictionaryLab; //возвращения (единственного) статичного обьекта
    }

    public List<Dictionary> getDictionaries() {
        return mDictionaries;
    }

    public void open() throws SQLException {
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    public void close() {
        mSQLiteHelper.close();
    }

    public void putFirstDictionary(Dictionary dictionary) {
        saveDictionaryInDateBase(dictionary);
    }

    public void putNewDictionary(String name){
        saveDictionaryInDateBase(new Dictionary(name));
    }

    public void setActiveDictionaryByID(int id) {
        for (Dictionary dictionary : mDictionaries) {
            if (dictionary.getDictionaryID() == id) {
                mActiveDictionary = dictionary;
                return;
            }
        }
        Log.d(TAG, "Не найден словарь с id \"" + id + "\" в DictionaryLab");
    }

    public Dictionary getActiveDictionary(){
        return mActiveDictionary;
    }

    public void removeDictionaryByID(int id, Context context) {
        mDataBase.delete(DbSchema.DictionaryTable.NAME,
                DbSchema.DictionaryTable.Cols.DictionaryID + " = ?",
                new String[]{String.valueOf(id)});
        refreshDictionaries();
        WordLab.get(context).removeWordsByDictionaryID(id);
        PhraseLab.get(context).removePhrasesByDictionaryID(id);
    }

    private DictionaryLab(Context context) { //закрытый конструктор, вызывается только из get()
        open();
        refreshDictionaries();
    }

    private List<Dictionary> getAllDictionaryFromDataBase() {
        List<Dictionary> dictionaries = new ArrayList<Dictionary>();
        Cursor cursor = mDataBase.query(DbSchema.DictionaryTable.NAME,
                DbSchema.DictionaryTable.Cols.dictionaryAllColumn, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dictionary dictionary = cursorToDictionary(cursor);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }
        return dictionaries;
    }

    private void saveDictionaryInDateBase(Dictionary dictionary) {
        ContentValues editedPhrase = new ContentValues();
        editedPhrase.put(DbSchema.DictionaryTable.Cols.DictionaryID, getNextIDDictionaryFromDataBase());
        editedPhrase.put(DbSchema.DictionaryTable.Cols.DictionaryName, dictionary.getDictionaryName());
        mDataBase.insert(DbSchema.DictionaryTable.NAME, null, editedPhrase);
        refreshDictionaries();
    }

    private void saveDictionaryInDateBase(List<Dictionary> dictionaries) {
        for (Dictionary dictionary : dictionaries) {
            saveDictionaryInDateBase(dictionary);
        }
    }

    private void refreshDictionaries(){
        mDictionaries = new ArrayList<>();
        mDictionaries.addAll(getAllDictionaryFromDataBase());
    }

    private int getNextIDDictionaryFromDataBase() {
        if (getAllDictionaryFromDataBase().isEmpty()) {
            return 0;
        }
        List<Dictionary> dictionaries = getAllDictionaryFromDataBase();
        List<Integer> dictionarySort = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            dictionarySort.add(dictionary.getDictionaryID());
        }
        Collections.reverse(dictionarySort);
        return dictionarySort.get(0) + 1;
    }

    private Dictionary cursorToDictionary(Cursor cursor) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictionaryID(cursor.getInt(0));
        dictionary.setDictionaryName(cursor.getString(1));
        return dictionary;
    }
}