import entrypoints.Dsl2Html;

import java.util.Map;

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
//        Dsl2Html.saveTranslationsToFiles(dictPathsArray, wordsToTranslate, outputDirectoryPath);
        Map<String, String> articles = Dsl2Html.getTranslationsAsDictionary(dictPathsArray, wordsToTranslate);
    }
}
