import models.Dictionary;
import models.Node;
import providers.DslRowParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args){

        String dictPathsArray[] = {
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsCobuildEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/CollinsEnEn",
                "/Users/anatoly/Downloads/DSL Dictionaries/En-En-Oald8.08-2010.dsl",
                "/Users/anatoly/Downloads/DSL Dictionaries/LingvoUniversalEnRu"
        };


        List<Dictionary> dictionaries = new ArrayList<>();
        List<String> dictPathsList = Arrays.asList(dictPathsArray);
        dictPathsList.forEach(dictPath -> {
            System.out.println(dictPath);
                    Dictionary dictionary = new Dictionary(dictPath);
                    if(!dictionary.isDictionaryInitialized()){
                        System.out.println("Failed: " + dictionary.getDictionaryDirectory());
                        System.out.println("---------------------------");
                    }else{
                        dictionaries.add(dictionary);
                        System.out.println("---------------------------");
                    }
        });

        dictionaries.forEach(dictionary -> {
            try {
                System.out.println(dictionary.getDictionaryName());
                String articleBody = dictionary.getArticleByWord("world");
                System.out.println(articleBody);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });

    }
}
