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
    private static Context context;
    private final String TAG = getClass().getSimpleName();
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;
    private static DictionaryLab sDictionaryLab;
    private List<Dictionary> mDictionaries = null;

    public static DictionaryLab get(Context receivedContext) {
        context = receivedContext;
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
        dictionary.setActiveDictionary(true);
        saveDictionaryInDateBase(dictionary);
    }

    public int putNewDictionary(String name) {
        return createDictionaryInDateBase(new Dictionary(name));
    }

    public void setActiveDictionaryByID(int id) {
        for (Dictionary dictionary : mDictionaries) {
            if (dictionary.getDictionaryID() == id) {
                setOllDictionaryPassive();
                dictionary.setActiveDictionary(true);
                saveDictionaryInDateBase(dictionary);
                return;
            }
        }
        Log.d(TAG, "Не найден словарь с id \"" + id + "\" в DictionaryLab");
    }

    public Dictionary getActiveDictionary() {
        refreshDictionaries();
        for (Dictionary dictionary : mDictionaries) {
            if (dictionary.isActiveDictionary() == true) {
                return dictionary;
            }
        }
        return null;
    }

    public Dictionary getDictionaryByID(int id) {
        for (Dictionary dictionary : mDictionaries) {
            if (dictionary.getDictionaryID() == id) {
                return dictionary;
            }
        }
        return null;
    }

    public void removeDictionaryByID(int id) {
        mDataBase.delete(DbSchema.DictionaryTable.NAME,
                DbSchema.DictionaryTable.Cols.DictionaryID + " = ?",
                new String[]{String.valueOf(id)});
        refreshDictionaries();
        WordLab.get(context).removeWordsByDictionaryID(id);
        PhraseLab.get(context).removePhrasesByDictionaryID(id);
    }

    private void setOllDictionaryPassive() {
        for (Dictionary dictionary : getAllDictionaryFromDataBase()){
            dictionary.setActiveDictionary(false);
            recreationDictionaryInDataBase(dictionary);
        }
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
        if (getDictionaryByID(dictionary.getDictionaryID()) != null) {
            recreationDictionaryInDataBase(dictionary);
            return;
        }
        createDictionaryInDateBase(dictionary);
    }

    private int createDictionaryInDateBase(Dictionary dictionary) {
        int dictionaryID = getNextIDDictionaryFromDataBase();
        ContentValues editedDictionary = new ContentValues();
        editedDictionary.put(DbSchema.DictionaryTable.Cols.DictionaryID, dictionaryID);
        editedDictionary.put(DbSchema.DictionaryTable.Cols.DictionaryName, dictionary.getDictionaryName());
        editedDictionary.put(DbSchema.DictionaryTable.Cols.IsActiveDictionary, String.valueOf(dictionary.isActiveDictionary()));
        mDataBase.insert(DbSchema.DictionaryTable.NAME, null, editedDictionary);
        refreshDictionaries();
        return dictionaryID;
    }

    private void saveDictionaryInDateBase(List<Dictionary> dictionaries) {
        for (Dictionary dictionary : dictionaries) {
            saveDictionaryInDateBase(dictionary);
        }
    }

    private void recreationDictionaryInDataBase(Dictionary dictionary) {
        mDataBase.delete(DbSchema.DictionaryTable.NAME,
                DbSchema.DictionaryTable.Cols.DictionaryID + " = ?",
                new String[]{String.valueOf(dictionary.getDictionaryID())});

        ContentValues editedDictionary = new ContentValues();
        editedDictionary.put(DbSchema.DictionaryTable.Cols.DictionaryID, dictionary.getDictionaryID());
        editedDictionary.put(DbSchema.DictionaryTable.Cols.DictionaryName, dictionary.getDictionaryName());
        editedDictionary.put(DbSchema.DictionaryTable.Cols.IsActiveDictionary, String.valueOf(dictionary.isActiveDictionary()));
        mDataBase.insert(DbSchema.DictionaryTable.NAME, null, editedDictionary);
        refreshDictionaries();
    }

    private void refreshDictionaries() {
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
        dictionary.setActiveDictionary(Boolean.valueOf(cursor.getString(2)));
        return dictionary;
    }
}
