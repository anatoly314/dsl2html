package dslTags;

import providers.ZipResourceProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class STag {
    private static final List<String> supportedImageExtensions = Arrays.asList(new String[]{
            "bmp","jpg","jpeg","tif","tiff","png","gif","wmf","emf"
    });
    private static final String audioResourceFoundHtmlTagTemplate = "<i class=\"fa fa-volume-up\" onclick=\"playAudio('{{BASE64_CONTENT}}')\"></i>";
    private static final String audioResourceNotFoundHtmlTag = "<i class=\"fa fa-volume-up resource-not-found\"></i><sup>not found</sup>";
    private static final String imageTagTemplate = "<img src=\"data:image/{{IMAGE_TYPE}};base64, {{BASE64_CONTENT}} \" alt=\"{{ALT_TEXT}}\" />";
    private static final String imageNotFoundHtmlTag = "<img src=\"not found.jpg\"><sup class=\"resource-not-found\">image not found</sup>";


    private static String getResourceExtension(String resourceName){
        List<String> resourceNameParts = Arrays.asList(resourceName.split("\\."));
        if(resourceNameParts.size() > 0){
            String extension = resourceNameParts.get(resourceNameParts.size() - 1);
            return extension;
        }else {
            return null;
        }
    }

    public static String getExternalResource(String resourceName, String dictionaryName){
        String defaultResourceTag = "<span>" + resourceName + "</span>";
        try {
            String base64Resource = ZipResourceProvider.getResourceByDictionaryNameAndResourceName(dictionaryName, resourceName);
            String resourceExtension = getResourceExtension(resourceName);
            if(resourceExtension == null){
                return defaultResourceTag;
            }

            //Audio files
            if(resourceExtension.equalsIgnoreCase("wav") && base64Resource != null){
                String audioResourceTag = audioResourceFoundHtmlTagTemplate.replace("{{BASE64_CONTENT}}", base64Resource);
                return audioResourceTag;
            }else if(resourceExtension.equalsIgnoreCase("wav") && base64Resource == null){
                return audioResourceNotFoundHtmlTag;
            }

            //Images
            if(supportedImageExtensions.indexOf(resourceExtension) >= 0 && base64Resource != null){
                String imageHtmlTag = imageTagTemplate
                        .replace("{{IMAGE_TYPE}}", resourceExtension)
                        .replace("{{BASE64_CONTENT}}", base64Resource)
                        .replace("{{ALT_TEXT}}", resourceName);
                return imageHtmlTag;
            }else if(supportedImageExtensions.indexOf(resourceExtension) >= 0 && base64Resource == null){
                return imageNotFoundHtmlTag;
            }

            return defaultResourceTag;
        }catch (IOException ex){
            ex.printStackTrace();
            return defaultResourceTag;
        }

    }
}
