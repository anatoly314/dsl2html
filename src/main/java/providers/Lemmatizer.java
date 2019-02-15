package providers;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

import java.util.ArrayList;
import java.util.List;

public class Lemmatizer {
    public static String lemmatizeWord(String word){
        Sentence sentence = new Sentence(word);
        Token token = new Token(sentence, 0);
        String lemma = token.lemma();
        return lemma;
    }


    public static List<String> lemmatizeWords(List<String> words){
        List<String> lemmatizedWords = new ArrayList<>();
        words.forEach(word -> {
            String lemmatizedWord = lemmatizeWord(word);
            lemmatizedWords.add(lemmatizedWord);
        });
        return lemmatizedWords;
    }
}