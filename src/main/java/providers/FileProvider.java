package providers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
}
