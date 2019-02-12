import models.Dictionary;
import models.Node;
import providers.DslRowParser;

import java.io.IOException;

public class main {
    public static void main(String[] args){
        String pathToDslFolder = "/Users/anatoly/Downloads/GoldenDict_Dicts/En-Ru/LingvoUniversalEnRu";
        Dictionary dictionary = new Dictionary(pathToDslFolder);
        try {
            String articleBody = dictionary.getArticleByWord("world");
            System.out.println(articleBody);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
