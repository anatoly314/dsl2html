package providers;

import models.Dictionary;

import java.util.*;

public class DictionariesProvider {
    private static boolean dictionariesInitialized = false;
    private static Map<String, Dictionary> dictionaries;

    public static void initializeDictionaries(List<String> dictionariesPaths){
        dictionaries = new HashMap<>();
        dictionariesPaths.forEach(dictPath -> {
            Dictionary dictionary = new Dictionary(dictPath);
            if(!dictionary.isDictionaryInitialized()){
                System.out.println("Failed: " + dictionary.getDictionaryDirectory());
            }else{
                dictionaries.put(dictionary.getDictionaryName(), dictionary);
                dictionariesInitialized = true;
            }
            System.out.println("---------------------------");
        });
    }

    public static void initializeDictionaries(String[] dictionariesPaths){
        initializeDictionaries(Arrays.asList(dictionariesPaths));
    }

    public static List<Dictionary> getListOfDictionaries(){
        if(!dictionariesInitialized){
            System.err.println("Dictionaries have not been initialized yet");
        }
        List<Dictionary> dictionariesList = new ArrayList<Dictionary>(dictionaries.values());
        return dictionariesList;
    }

    public static Dictionary getDictionaryByName(String dictionaryName){
        Dictionary dictionary = dictionaries.get(dictionaryName);
        return dictionary;
    }
}
