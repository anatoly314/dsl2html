package models;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private String mainTextFilePath;
    private String annotationFilePath;
    private String abbreviationFilePath;

    private void createArticlesIndex(String filename) throws IOException{
        this.articlesIndex = new LinkedHashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename), Charset.forName("UTF-16LE"))) {
            Iterator<String> iterator = stream.iterator();
            for (int lineNumber = 0; iterator.hasNext(); lineNumber++) {
                String line = iterator.next();
                if (!line.isEmpty() && !Character.isWhitespace(line.charAt(0)) && line.charAt(0) != '#') {
                    this.createTitleCombinationsAndAddToIndex(line, lineNumber);
                }
            }
        }
    }

    private void createTitleCombinationsAndAddToIndex(String originalTitle, Integer lineNumber){
        this.articlesIndex.put(originalTitle, lineNumber);
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

    public Dictionary(String dictionaryDirectory){
        if(this.isDirectoryExists(dictionaryDirectory)){
            try {
                List<String> directoryFiles = this.getDirectoryFileNames(dictionaryDirectory);
                directoryFiles.forEach(filePath -> {
                    if(filePath.indexOf("_abrv.dsl") >= 0){
                        this.abbreviationFilePath = filePath;
                    }else if(filePath.indexOf(".dsl") >= 0 && filePath.indexOf("_abrv.dsl") < 0){
                        this.mainTextFilePath = filePath;
                    }else if(filePath.indexOf(".ann") >= 0 && filePath.indexOf("_abrv.ann") < 0){
                        this.annotationFilePath = filePath;
                    }
                });

                if(this.mainTextFilePath != null){
                    this.createArticlesIndex(this.mainTextFilePath);
                }

                System.out.println("Main file: " + this.mainTextFilePath);
                System.out.println("Annotation file: " + this.annotationFilePath);
                System.out.println("Abbreviation file: " + this.abbreviationFilePath);
            } catch (Exception ex){
                System.out.println(ex.toString());
            }
        }
    }
}
