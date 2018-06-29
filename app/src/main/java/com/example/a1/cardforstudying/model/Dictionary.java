package com.example.a1.cardforstudying.model;

/**
 * Created by 1 on 10.06.2018.
 */

public class Dictionary {
    private int DictionaryID;
    private String DictionaryName;

    public Dictionary(String dictionaryName){
        this.DictionaryName = dictionaryName;
    }

    public Dictionary(){}

    public int getDictionaryID() {
        return DictionaryID;
    }

    public void setDictionaryID(int dictionaryID) {
        DictionaryID = dictionaryID;
    }

    public String getDictionaryName() {
        return DictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        DictionaryName = dictionaryName;
    }
}
