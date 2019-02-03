package dslTags;

public class BTag {
    public static String getHtmlRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "font-weight: bold"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
