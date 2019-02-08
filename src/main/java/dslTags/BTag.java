package dslTags;

public class BTag extends DslTag{
    public static String getHtmlOpenTagRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "font-weight: bold"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
