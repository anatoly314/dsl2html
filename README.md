#  DSL to HTML parser

-----------------------
## How to use:
- Example how to save translation result as `*.html` files:

````
import entrypoints.Dsl2Html;
public class main {

    public static void main(String[] args){
        
        String dictPathsArray[] = { ... };                                      //paths to DSL dictionaries
        String[] wordsToTranslate = {"angel","peace","world","brother"};        //list of words to translate
        String outputDirectoryPath = "test-output";                             //output folder path
        Dsl2Html.saveTranslationsToFiles(dictPathsArray, wordsToTranslate, outputDirectoryPath);
    }
}
````


## Used resources
1. Font Awesome customized by: [https://icomoon.io](https://icomoon.io)
2. woff to base64 by: [https://www.browserling.com/tools/file-to-base64](https://www.browserling.com/tools/file-to-base64)
3. Expiration how to cause Font Awesome works in base64 mode taken from here: [https://gitlab.com/tecnos/font-awesome-base64#README](https://gitlab.com/tecnos/font-awesome-base64#README)