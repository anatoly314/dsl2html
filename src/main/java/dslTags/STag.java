package dslTags;

import providers.ZipResourceProvider;

import java.io.IOException;

public class STag {
    public static String getExternalResource(String resourceName, String dictionaryName){
        String htmlRepresentationOfSTag = "<span>External Resource</span>";
        try {
            ZipResourceProvider.getResourceByDictionaryNameAndResourceName(dictionaryName, resourceName);
            return htmlRepresentationOfSTag;
        }catch (IOException ex){
            ex.printStackTrace();
            return htmlRepresentationOfSTag;
        }
    }
}
