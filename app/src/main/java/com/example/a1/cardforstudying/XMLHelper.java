package com.example.a1.cardforstudying;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class XMLHelper {
    private static final String TAG = "XMLHelper";
    public static FileOutputStream outputStream;

    public static String xmlReader() {
        File file = new File("");
        return file.toString();

    }

    public static void createFirstDictionary(Context context) {
        try {
            Log.d(TAG, "createFirstDictionary() called");

            File file = new File(context.getFilesDir(), "FirstDictionary.xml");

            outputStream = context.openFileOutput("FirstDictionary.xml", Context.MODE_PRIVATE);
            //outputStream.write("</start>".getBytes());
            outputStream.close();

            File file2 = new File("//data//app//CardsForStuduing", "FirstDictionary.xml");

            Scanner scanner = new Scanner(file2);//неработает
            scanner.nextLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

