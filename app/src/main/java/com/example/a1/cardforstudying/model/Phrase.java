package com.example.a1.cardforstudying.model;

/**
 * Created by 1 on 08.06.2018.
 */

public class Phrase {
    private int PhraseID;
    private String PhraseMeaning;
    private String PhraseTranslation;
    private int mDictionaryID;

    public int getPhraseID() {
        return PhraseID;
    }

    public void setPhraseID(int phraseID) {
        PhraseID = phraseID;
    }

    public String getPhraseMeaning() {
        return PhraseMeaning;
    }

    public void setPhraseMeaning(String phraseMeaning) {
        PhraseMeaning = phraseMeaning;
    }

    public String getPhraseTranslation() {
        return PhraseTranslation;
    }

    public void setPhraseTranslation(String phraseTranslation) {
        PhraseTranslation = phraseTranslation;
    }

    public int getDictionaryID() {
        return mDictionaryID;
    }

    public void setDictionaryID(int dictionaryID) {
        mDictionaryID = dictionaryID;
    }
}
