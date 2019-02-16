package providers;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipResourceProvider {
    public static String getResourceByDictionaryNameAndResourceName(String dictionaryName, String resourceName) throws IOException{
        String base64encodedResource = null;
        String resourcePath = DictionariesProvider.getDictionaryByName(dictionaryName).getResourceFilePath();

        if(resourcePath == null){
            return base64encodedResource;
        }

        ZipFile zipFile = new ZipFile(resourcePath);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if(entryName.equalsIgnoreCase(resourceName)){
                System.out.println(entryName);
                System.out.println("dictionary name: " + dictionaryName + " resource: " + resourceName + " path: " + DictionariesProvider.getDictionaryByName(dictionaryName).getResourceFilePath());
                InputStream stream = zipFile.getInputStream(entry);
                byte[] bytes = IOUtils.toByteArray(stream);
                base64encodedResource = Base64.getEncoder().encodeToString(bytes);
            }
        }

        zipFile.close();
        return base64encodedResource;
    }
}
