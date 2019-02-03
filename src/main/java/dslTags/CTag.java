package dslTags;

public class CTag {
    public static String getHtmlRepresentation(String color){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "color:" + color));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
