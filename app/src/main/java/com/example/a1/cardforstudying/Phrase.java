package com.example.a1.cardforstudying;

/**
 * Created by 1 on 08.06.2018.
 */

public class Phrase {
    public int PhraseID;
    public String PhraseMeaning;
    public String PhraseTranslation;

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
}
