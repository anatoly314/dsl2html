import entrypoints.Dsl2Html;
import models.Dictionary;
import models.Node;
import models.WordTranslation;
import org.apache.commons.codec.digest.DigestUtils;
import providers.DictionariesProvider;
import providers.DslArticle2HtmlParser;
import providers.DslRowParser;
import providers.FileProvider;

import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    public static void main(String[] args){
        String dictPathsArray[] = {
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsCobuildEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/En-En-Oald8.08-2010.dsl",
                "/Users/anatoly/Downloads/DSL Dictionaries/LingvoUniversalEnRu",
                "/Users/anatoly/Downloads/DSL Dictionaries/en-en_CALD4/en-en_CALD4/en-en_CALD4"
        };

        String[] wordsToTranslate = {"angel","peace","world","brother"};
        String outputDirectoryPath = "test-output";
        Dsl2Html.saveTranslationsToFiles(dictPathsArray, wordsToTranslate, outputDirectoryPath);
    }
}
