import models.Dictionary;
import models.Node;
import models.WordTranslation;
import org.apache.commons.codec.digest.DigestUtils;
import providers.DslArticle2HtmlParser;
import providers.DslRowParser;
import providers.FileProvider;

import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    private static List<Dictionary> initializeDictionaries(String[] dictPaths){
        List<Dictionary> dictionaries = new ArrayList<>();
        List<String> dictPathsList = Arrays.asList(dictPaths);
        dictPathsList.forEach(dictPath -> {
            Dictionary dictionary = new Dictionary(dictPath);
            if(!dictionary.isDictionaryInitialized()){
                System.out.println("Failed: " + dictionary.getDictionaryDirectory());
                System.out.println("---------------------------");
            }else{
                dictionaries.add(dictionary);
            }
        });
        return dictionaries;
    }

    public static void main(String[] args){

        String dictPathsArray[] = {
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsCobuildEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/En-En-Oald8.08-2010.dsl",
                "/Users/anatoly/Downloads/DSL Dictionaries/LingvoUniversalEnRu"
        };

        List<String> wordsToTranslate = Arrays.asList(new String[]{"worlds", "peace", "against"});
        List<Dictionary> dictionaries = initializeDictionaries(dictPathsArray);

        List<WordTranslation> wordTranslations = new ArrayList<>();
        wordsToTranslate.forEach(word -> {
            WordTranslation wordTranslation = new WordTranslation(word, dictionaries);
            wordTranslations.add(wordTranslation);
        });


        wordTranslations.forEach(wordTranslation -> {
            writeWordTranslationsToFile(wordTranslation);
        });



    }

    private static void writeWordTranslationsToFile(WordTranslation wordTranslation){
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
        FileProvider.saveToFile("test-output/" + articleTitle + ".html", articlesTemplate);
    }
}
