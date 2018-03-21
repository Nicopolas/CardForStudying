package com.example.a1.cardforstudying;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WordLab {
    private static WordLab sWordLab; //обьект синглтона

    private List<Word> mWord;
    public static final String dataRu = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩИЙЭЮЯабвгдеёжзиклмнопрстуфхцчшщийэюя";
    public static final String dataEng = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    public static final ArrayList<String> dataEngTranscr = new ArrayList<String>() {{
            add("ei ");
            add("bi: ");
            add("si: ");
            add("di: ");
            add("i: ");
            add("ef ");
            add("ai ");
            add("Λ ");
            add("a: ");
            add("i ");
            add("i: ");
            add("o ");
            add("o: ");
            add("u ");
            add("u: ");
            add("e ");
            add("ε: ");
            add("əu ");
            add("au ");
            add("ei ");
            add("oi ");
            add("ai ");
            add("æ ");
            add("ə ");
            add("∫ ");
            add("r ");
            add("о ");
            add("θ ");
            add("ð ");
            add("ŋ ");
            add("w ");
        }};

    public static WordLab get(Context context) {//зачем Context????????
        if (sWordLab == null) {
            sWordLab = new WordLab(context);
        }
        return sWordLab; //возвращения (единственного) статичного обьекта
    }

    private WordLab(Context context) { //закрытый конструктор, вызывается только из get()
        mWord = new ArrayList<>();

        for (int i = 0; i < 10; i++) { //тестовые данные
            Word word = new Word();
            word.setMeaningWord(getRandomString(1, this.dataEng).toUpperCase() + getRandomString(5, this.dataEng).toLowerCase());
            word.setMeaningWordTranscription("[" + getRandomString(6, this.dataEngTranscr) + "]");
            word.setTranslationWord("сущ.: " + getRandomString(8, this.dataRu).toLowerCase() + ", " + getRandomString(6, this.dataRu).toLowerCase() +
                    "глаг.: " + getRandomString(8, this.dataRu).toLowerCase() +
                    "прил.: " + getRandomString(6, this.dataRu).toLowerCase());
            word.setExample(getRandomString(1, this.dataEng).toUpperCase() + getRandomString(4, this.dataEng).toLowerCase() + " " + getRandomString(6, this.dataEng).toLowerCase() + " " + getRandomString(6, this.dataEng).toLowerCase()
                    , getRandomString(1, this.dataRu).toUpperCase() + getRandomString(4, this.dataRu).toLowerCase() + " " + getRandomString(6, this.dataRu).toLowerCase() + " " + getRandomString(6, this.dataRu).toLowerCase());
            word.setInTest(true);
            mWord.add(word);
        }
    }

    public List<Word> getWord() {
        return mWord;
    }

    public Word getWord(UUID mWordId) {
        for (Word word : mWord) {
            if (word.getWordId().equals(mWordId)) {
                return word;
            }
        }
        return null;
    }

    public static String getRandomString(int length, String data) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(data.length() - 1);
            str.append(data.substring(index, index + 1));
        }
        return str.toString();
    }

    public static String getRandomString(int length, ArrayList<String> data) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(data.size() - 1);
            str.append(data.get(index));
        }
        return str.toString();
    }
}
