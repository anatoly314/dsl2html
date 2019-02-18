package entrypoints;

import models.WordTranslation;
import org.apache.commons.codec.digest.DigestUtils;
import providers.DictionariesProvider;
import providers.FileProvider;

import java.util.*;

public class Dsl2Html {

    private static List<WordTranslation> getWordTranslations(List<String> listOfWordsToTranslate){
        List<WordTranslation> wordTranslations = new ArrayList<>();
        listOfWordsToTranslate.forEach(word -> {
            WordTranslation wordTranslation = new WordTranslation(word);
            wordTranslations.add(wordTranslation);
        });
        return wordTranslations;
    }

    private static List<WordTranslation> getWordTranslations(String[] arraysOfWordsToTranslate){
        List<String> listOfWordsToTranslate = Arrays.asList(arraysOfWordsToTranslate);
        List<WordTranslation> wordTranslations = new ArrayList<>();
        listOfWordsToTranslate.forEach(word -> {
            WordTranslation wordTranslation = new WordTranslation(word);
            wordTranslations.add(wordTranslation);
        });
        return wordTranslations;
    }

    private static void writeWordTranslationsToFile(WordTranslation wordTranslation, String outputDirectoryPath){
        String articleTitle = wordTranslation.getSourceWordLemmatized();
        String outputHtmlArticle = getOutputHtmlArticle(wordTranslation);
        FileProvider.saveToFile(outputDirectoryPath + "/" + articleTitle + ".html", outputHtmlArticle);
    }

    private static String getOutputHtmlArticle(WordTranslation wordTranslation){
        String articleContainer = FileProvider.getFileByFileName("html-template/article-container.html");
        String articleTitle = wordTranslation.getSourceWordLemmatized();
        StringBuilder multipleArticles = new StringBuilder();
        wordTranslation.getDslArticles().forEach((String dictionaryName, String dslArticle) -> {
            String articleBuilder = new String(articleContainer);
            articleBuilder = articleBuilder.replace("{{ARTICLE_HASH}}", DigestUtils.sha1Hex(dictionaryName));
            articleBuilder = articleBuilder.replace("{{DICTIONARY_NAME}}", dictionaryName);
            articleBuilder = articleBuilder.replace("{{ARTICLE_TITLE}}", articleTitle);
            String htmlArticle = wordTranslation.getHtmlArticles().get(dictionaryName);  //dictionary name same for dsl and html article
            articleBuilder = articleBuilder.replace("{{ARTICLE_CONTENT}}", htmlArticle);
            multipleArticles.append(articleBuilder);
        });
        String articlesTemplate = FileProvider.getFileByFileName("html-template/multiple-articles-template.html");
        articlesTemplate = articlesTemplate.replace("{{ARTICLE_TITLE}}", articleTitle);
        articlesTemplate = articlesTemplate.replace("{{ARTICLES_CONTENT}}", multipleArticles.toString());
        return articlesTemplate;
    }

    public static void saveTranslationsToFiles(String[] pathToDictionaries, String[] wordsToTranslate, String outputDirectoryPath){
        DictionariesProvider.initializeDictionaries(pathToDictionaries);
        List<WordTranslation> wordTranslations = getWordTranslations(wordsToTranslate);
        wordTranslations.forEach(wordTranslation -> {
            writeWordTranslationsToFile(wordTranslation, outputDirectoryPath);
        });
    }

    public static Map<String, String> getTranslationsAsDictionary(String[] pathToDictionaries, String[] wordsToTranslate){
        DictionariesProvider.initializeDictionaries(pathToDictionaries);
        List<WordTranslation> wordTranslations = getWordTranslations(wordsToTranslate);
        Map<String, String> result = new HashMap<>();
        wordTranslations.forEach(wordTranslation -> {
            String translatedWord = wordTranslation.getSourceWordLemmatized();
            String htmlArticle = getOutputHtmlArticle(wordTranslation);
            result.put(translatedWord, htmlArticle);
        });
        return result;
    }
}
