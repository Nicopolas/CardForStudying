package com.example.a1.cardforstudying.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Word {
    private String mMeaningWord;
    private String mMeaningWordTranscription;
    private String mTranslationWord;
    private Map<String, String> mExample;
    private int mWordId;
    private int mRatingWord;
    private boolean mInTest;
    private int mDictionaryID;

    public Word() { //конструкторы нечего не возврящают (даже void)
        mExample = new HashMap<>();
        mInTest = false;
        mRatingWord = 0;
    }

    public Word(int mWordId, String mMeaningWord, String mMeaningWordTranscription, String mTranslationWord, String mInTest, String mRatingWord, Map<String, String> mExample) {
        this.mMeaningWord = mMeaningWord;
        this.mMeaningWordTranscription = mMeaningWordTranscription;
        this.mTranslationWord = mTranslationWord;
        this.mExample = new HashMap<>();
        this.mExample.putAll(mExample);
        this.mInTest = Boolean.valueOf(mInTest);
        this.mRatingWord = Integer.valueOf(mRatingWord);
        this.mWordId = mWordId;
    }

    public String getMeaningWord() {
        return mMeaningWord;
    }

    public void setMeaningWord(String meaningWord) {
        mMeaningWord = meaningWord;
    }

    public String getMeaningWordTranscription() {
        return mMeaningWordTranscription;
    }

    public void setMeaningWordTranscription(String meaningWordTranscription) {
        mMeaningWordTranscription = meaningWordTranscription;
    }

    public String getTranslationWord() {
        return mTranslationWord;
    }

    public void setTranslationWord(String translationWord) {
        mTranslationWord = translationWord;
    }

    public String getExample() {// придумать как получать не только первое значение
        for (String example : mExample.keySet()) {
            return example;
        }
        return "";
    }

    public String getExampleTranslation() {// придумать как получать не только первое значение
        for (String example : mExample.values()) {
            return example;
        }
        return "";
    }

    public void setExample(String example, String exampleTranslation) {
        exampleTranslation = exampleTranslation.isEmpty() ? "" : exampleTranslation;
        mExample.put(example, exampleTranslation);
    }

    public int getWordId() {
        return mWordId;
    }

    public void setWordId(int mWordId) {
        this.mWordId = mWordId;
    }

    public int getRatingWord() {
        return mRatingWord;
    }

    public void setRatingWord(int ratingWord) {
        mRatingWord = ratingWord;
    }

    public boolean isInTest() {
        return mInTest;
    }

    public void setInTest(boolean mInTest) {
        this.mInTest = mInTest;
    }

    public int getDictionaryID() {
        return mDictionaryID;
    }

    public void setDictionaryID(int dictionaryID) {
        mDictionaryID = dictionaryID;
    }

    public String getRandomTranslationWord() {//вынести устойчивые выражения
        List<String> str = new ArrayList<String>(Arrays.asList(
                getTranslationWord()
                        .replace("сущ", "")
                        .replaceAll("глаг", "")
                        .replaceAll("прил", "")
                        .replaceAll(".:", "")
                        .replaceAll(",", "")
                        .replaceAll("\\s+", " ")
                        .split(" "))); //Array.asList()- это просто обертка над массивом с интерфейсом List.А массивы имеют фиксированный размер, поэтому добавление и удаление элементов не поддерживается.

        for (int i = 0; i < str.size(); i++) {
            if (str.get(i).equals("")) {
                str.remove(i);
            }
        }
        return str.get(new Random().nextInt(str.size()));
    }
}
