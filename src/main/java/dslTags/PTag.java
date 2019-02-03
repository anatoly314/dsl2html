package dslTags;

public class PTag {
    public static String getHtmlRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "font-style: italic; color: green;"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
