package models;

import providers.DslArticle2HtmlParser;
import providers.Lemmatizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordTranslation {
    private String sourceWord;
    private String sourceWordLemmatized;
    private Map<String, String> dslArticles;
    private Map<String, String> htmlArticles;
    private boolean translationFound;

    private void translateWord(List<Dictionary> dictionaries){
        dslArticles = new HashMap<>();
        htmlArticles = new HashMap<>();
        dictionaries.forEach(dictionary -> {
            String dictionaryName = dictionary.getDictionaryName();
            try{
                String dslArticle = dictionary.getArticleByWord(this.sourceWordLemmatized);
                if(dslArticle != null){
                    dslArticles.put(dictionaryName, dslArticle);
                    htmlArticles.put(dictionaryName, DslArticle2HtmlParser.getHtmlArticle(dslArticle));
                }
            }catch (IOException ex){
                System.err.println("Error at: " + dictionaryName + " Stacktrace: " + ex.getMessage());
            }
        });
    }

    public WordTranslation(String sourceWord, List<Dictionary> dictionaries){
        this.sourceWord = sourceWord;
        this.sourceWordLemmatized = Lemmatizer.lemmatizeWord(sourceWord);
        System.out.println("source word: " + sourceWord + " lemmatized word: " + this.sourceWordLemmatized);
        this.translateWord(dictionaries);
    }

    public String getSourceWord() {
        return sourceWord;
    }

    public String getSourceWordLemmatized() {
        return sourceWordLemmatized;
    }

    public Map<String, String> getDslArticles() {
        return dslArticles;
    }

    public Map<String, String> getHtmlArticles() {
        return htmlArticles;
    }

    public boolean isTranslationFound() {
        return translationFound;
    }

}
