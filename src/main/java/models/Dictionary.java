package models;

import providers.FileProvider;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Dictionary {
    private Map<String, Integer> articlesIndex;
    private String dictionaryName;
    private String dictionaryDirectory;
    private String indexLanguage;   //from
    private String contentLanguage; //to
    private String mainTextFilePath;
    private String annotationFilePath;
    private String abbreviationFilePath;
    private boolean dictionaryInitialized;

    public String getDictionaryName(){
        return this.dictionaryName;
    }

    public String getDictionaryDirectory(){
        return this.dictionaryDirectory;
    }

    public boolean isDictionaryInitialized(){
        return this.dictionaryInitialized;
    }

    private void createArticlesIndex(String filename) throws IOException{
        this.articlesIndex = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename), Charset.forName("UTF-16LE"))) {
            Iterator<String> iterator = stream.iterator();
            for (int lineNumber = 0; iterator.hasNext(); lineNumber++) {
                String line = iterator.next();

                if(lineNumber == 0){    //#NAME
                    this.dictionaryName = line.replace("#NAME", "").trim();
                    continue;
                } else if(lineNumber == 1) {    //#INDEX_LANGUAGE
                    this.indexLanguage = line.replace("#INDEX_LANGUAGE", "").trim();
                    continue;
                } else if(lineNumber == 2) {    //#CONTENTS_LANGUAGE
                    this.contentLanguage = line.replace("#CONTENTS_LANGUAGE", "").trim();
                    continue;
                }

                if (!line.isEmpty() && !Character.isWhitespace(line.charAt(0)) && line.charAt(0) != '#') {
                    this.createTitleCombinationsAndAddToIndex(line, lineNumber);
                }
            }
        }
        this.dictionaryInitialized = true;
    }

    private void createTitleCombinationsAndAddToIndex(String originalTitle, Integer lineNumber){
        this.articlesIndex.put(originalTitle.toLowerCase(), lineNumber);
    }

    private boolean isDirectoryExists(String dictionaryDirectory){
        Path path = Paths.get(dictionaryDirectory);
        if(Files.exists(path) && Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
            return true;
        }else {
            return false;
        }
    }

    private List<String> getDirectoryFileNames(String dictionaryDirectory) throws IOException{
        List<String> list = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(dictionaryDirectory))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(name -> {
                        list.add(name.toString());
                    });
        }
        return list;
    }

    public String getArticleByWord(String word) throws IOException{
        word = word.toLowerCase();
        if(this.articlesIndex.containsKey(word)){
            int from = this.articlesIndex.get(word);
            String articleTitleAndBody = FileProvider.readArticleBetweenFirstLineAndLastLine(this.mainTextFilePath, from);
            return articleTitleAndBody;
        }
        return null;
    }

    public Dictionary(String dictionaryDirectory){
        this.dictionaryInitialized = false;
        this.dictionaryDirectory = dictionaryDirectory;
        if(this.isDirectoryExists(dictionaryDirectory)){
            try {
                List<String> directoryFiles = this.getDirectoryFileNames(dictionaryDirectory);
                directoryFiles.forEach(filePath -> {
                    if(filePath.endsWith("_abrv.dsl")){
                        this.abbreviationFilePath = filePath;
                    }else if(filePath.endsWith(".dsl") && !filePath.endsWith("_abrv.dsl")){
                        this.mainTextFilePath = filePath;
                    }else if(filePath.endsWith(".ann") && !filePath.endsWith("_abrv.ann")){
                        this.annotationFilePath = filePath;
                    }
                });

                if(this.mainTextFilePath != null){
                    this.createArticlesIndex(this.mainTextFilePath);
                }

                System.out.println("Main file: " + this.mainTextFilePath);
                System.out.println("Annotation file: " + this.annotationFilePath);
                System.out.println("Abbreviation file: " + this.abbreviationFilePath);
                System.out.println("Dictionary name: " + this.dictionaryName);
                System.out.println("Index language: " + this.indexLanguage);
                System.out.println("Content language: " + this.contentLanguage);
            } catch (Exception ex){
                System.out.println(ex.toString());
            }
        }
    }
}
