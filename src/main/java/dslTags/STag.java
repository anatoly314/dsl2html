package dslTags;

import providers.ZipResourceProvider;

import java.io.IOException;

public class STag {
    public static String getExternalResource(String resourceName, String dictionaryName){
        String defaultResourceTag = "<span>" + resourceName + "</span>";
        try {
            String base64Resource = ZipResourceProvider.getResourceByDictionaryNameAndResourceName(dictionaryName, resourceName);

            if(resourceName.endsWith(".wav") && base64Resource != null){
                String audioResourceFoundHtmlTag = "<i class=\"fa fa-volume-up\" onclick=\"playAudio('{{BASE64_CONTENT}}')\"></i>";
                audioResourceFoundHtmlTag = audioResourceFoundHtmlTag.replace("{{BASE64_CONTENT}}", base64Resource);
                return audioResourceFoundHtmlTag;
            }else if(resourceName.endsWith(".wav") && base64Resource == null){
                String audioResourceNotFoundHtmlTag = "<i class=\"fa fa-volume-up resource-not-found\" \"></i><sup>not found</sup>";
                return audioResourceNotFoundHtmlTag;
            }

            return defaultResourceTag;
        }catch (IOException ex){
            ex.printStackTrace();
            return defaultResourceTag;
        }

    }
}
