import models.Dictionary;
import models.Node;
import providers.DslRowParser;

public class main {
    public static void main(String[] args){
        String pathToDslFolder = "/Users/anatoly/Downloads/GoldenDict_Dicts/En-Ru/LingvoUniversalEnRu";
        Dictionary dictionary = new Dictionary(pathToDslFolder);
    }
}
