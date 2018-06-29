package com.example.a1.cardforstudying;

import android.content.Context;
import android.util.Log;

import com.example.a1.cardforstudying.model.Dictionary;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.Phrase;
import com.example.a1.cardforstudying.model.PhraseLab;
import com.example.a1.cardforstudying.model.Word;
import com.example.a1.cardforstudying.model.WordLab;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class XMLHelper {
    private static final String TAG = "XMLHelper";

    public static void createFirstDictionary(Context context) {
        String tag = "";
        List<Word> words = new ArrayList<>();
        List<Phrase> phrases = new ArrayList<>();
        XmlPullParser parser = context.getResources().getXml(R.xml.first_dictionary);
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("Word")) {
                    parser.next();
                    Word word = new Word();
                    Phrase phrase = new Phrase();
                    while (!(parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("Word"))) {
                        switch (parser.getEventType()) {
                            case XmlPullParser.START_TAG:
                                tag = parser.getName();
                                Log.d(TAG, "START_TAG: для тега" + parser.getName());
                                break;
                            // конец тега
                            case XmlPullParser.END_TAG:
                                Log.d(TAG, "END_TAG: имя тега = " + parser.getName());
                                break;
                            // содержимое тега
                            case XmlPullParser.TEXT:
                                switch (tag) {
                                    case "MeaningWord":
                                        word.setMeaningWord(parser.getText());
                                        break;
                                    case "MeaningWordTranscription":
                                        word.setMeaningWordTranscription(parser.getText());
                                        break;
                                    case "TranslationWord":
                                        word.setTranslationWord(parser.getText());
                                        break;
                                    case "Example":
                                        phrase.setPhraseMeaning(parser.getText());
                                        break;
                                    case "ExampleTranslation":
                                        phrase.setPhraseTranslation(parser.getText());
                                        break;
                                    default:
                                        break;
                                }
                                Log.d(TAG, "текст = " + parser.getText());
                                break;

                            default:
                                break;
                        }
                        parser.next();
                    }
                    word.setDictionaryID(0);
                    phrase.setDictionaryID(0);
                    words.add(word);
                    phrases.add(phrase);
                }
                parser.next();
            }
            DictionaryLab.get(context).putFirstDictionary(new Dictionary( "Default Dictionary"));
            WordLab.get(context).putFirstDictionary(words);
            PhraseLab.get(context).putFirstDictionary(phrases);
            DictionaryLab.get(context).setActiveDictionaryByID(DictionaryLab.get(context).getDictionaries().get(0).getDictionaryID());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка в парсенге first_dictionary \n" + e);
            return;
        }
    }

}

