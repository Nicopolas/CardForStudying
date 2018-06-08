package com.example.a1.cardforstudying.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.a1.cardforstudying.database.DbSchema.PhraseTable;
import static com.example.a1.cardforstudying.database.DbSchema.WordTable;
import static com.example.a1.cardforstudying.database.DbSchema.WordPhraseTable;
/*        NULL. Пустое значение в таблице базы.
          INTEGER. Целочисленное значение, хранящееся в 1, 2, 3, 4, 6 или 8 байтах, в зависимости от величины самого значения.
          REAL. Числовое значение с плавающей точкой. Хранится в формате 8-байтного числа IEEE с плавающей точкой.
          TEXT. Значение строки текста. Хранится с использованием кодировки базы данных (UTF-8, UTF-16BE или UTF-16LE).
          BLOB. Значение бинарных данных, хранящихся точно в том же виде, в каком были введены.*/


/**
 * Created by User on 17.03.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int VERSION = 2;
    public static final String DATABASE_NAME = "cardFowStudying";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createWordTableQuery());
        sqLiteDatabase.execSQL(createPhraseTableQuery());
        sqLiteDatabase.execSQL(createWordPhraseTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WordTable.NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WordPhraseTable.NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PhraseTable.NAME);
            onCreate(sqLiteDatabase);
        }
    }

    private String createWordTableQuery() {
        return "CREATE TABLE " + WordTable.NAME + " ("
                + WordTable.Cols.WordID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordTable.Cols.MeaningWord + " TEXT, " + WordTable.Cols.MeaningWordTranscription + " TEXT, " + WordTable.Cols.TranslationWord + " TEXT, "
                + WordTable.Cols.RatingWord + " INTEGER, " + WordTable.Cols.InTest + " TEXT" + ");";
    }

    private String createWordPhraseTableQuery() {
        return "CREATE TABLE " + WordPhraseTable.NAME + " ("
                + WordPhraseTable.Cols.WordPhraseID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordPhraseTable.Cols.WordID + " INTEGER, " + WordPhraseTable.Cols.PhraseID + " INTEGER" + ");";
    }

    private String createPhraseTableQuery() {
        return "CREATE TABLE " + PhraseTable.NAME + " ("
                + PhraseTable.Cols.PhraseID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PhraseTable.Cols.PhraseMeaning + " TEXT, " + PhraseTable.Cols.PhraseTranslation + " TEXT" + ");";
    }

}
