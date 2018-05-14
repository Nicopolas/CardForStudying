package com.example.a1.cardforstudying.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.a1.cardforstudying.database.DbSchema.WordTable;
/*        NULL. Пустое значение в таблице базы.
          INTEGER. Целочисленное значение, хранящееся в 1, 2, 3, 4, 6 или 8 байтах, в зависимости от величины самого значения.
          REAL. Числовое значение с плавающей точкой. Хранится в формате 8-байтного числа IEEE с плавающей точкой.
          TEXT. Значение строки текста. Хранится с использованием кодировки базы данных (UTF-8, UTF-16BE или UTF-16LE).
          BLOB. Значение бинарных данных, хранящихся точно в том же виде, в каком были введены.*/


/**
 * Created by User on 17.03.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "cardFowStudying";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createWordTableQuery());
        //sqLiteDatabase.execSQL(Exsample);
        //sqLiteDatabase.execSQL(Phraze);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String createWordTableQuery() {
        return "CREATE TABLE " + WordTable.NAME + " ("
                + WordTable.Cols.WordID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WordTable.Cols.MeaningWord + " TEXT,"
                + WordTable.Cols.MeaningWordTranscription + " TEXT," + WordTable.Cols.TranslationWord + " TEXT,\"\n"
                + WordTable.Cols.RatingWord + " INTEGER," + WordTable.Cols.InTest + " TEXT" + ");";
    }

}
