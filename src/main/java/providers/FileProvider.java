package providers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public class FileProvider {
    public static String getFileByFileName(String fileName){
        String fileText = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(fileName);
            fileText = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println(e.toString());
        } finally {
            return fileText;
        }

    }

    public static void saveToFile(String fileName, String fileText){
        try {
            FileUtils.writeStringToFile(new File(fileName), fileText, StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }


    public static String readFileBetweenLineNumbers(String filePath, Integer from, Integer to) throws IOException{
        int currentLineIndex = 0;
        StringBuilder text = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), Charset.forName("UTF-16LE"))) {
            Iterator<String> iterator = stream.iterator();
            while (currentLineIndex <= to && iterator.hasNext()){
                String line = iterator.next();
                if(currentLineIndex >= from && currentLineIndex <= to){
                    text.append(line);
                    text.append("\n");
                }
                currentLineIndex++;
            }
        }
        return text.toString();
    }
}
