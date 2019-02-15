package providers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipResourceProvider {
    public static void getResourceByDictionaryNameAndResourceName(String dictionaryName, String resourceName) throws IOException{

        String resourcePath = DictionariesProvider.getDictionaryByName(dictionaryName).getResourceFilePath();

        if(resourcePath == null){
            return;
        }

        ZipFile zipFile = new ZipFile(resourcePath);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if(entryName.equalsIgnoreCase(resourceName)){
                System.out.println(entryName);
                System.out.println("dictionary name: " + dictionaryName + " resource: " + resourceName + " path: " + DictionariesProvider.getDictionaryByName(dictionaryName).getResourceFilePath());
            }
            //InputStream stream = zipFile.getInputStream(entry);
        }

        zipFile.close();
    }
}
