package dslTags;

public class UTag extends DslTag{
    public static String getHtmlOpenTagRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "text-decoration: underline"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
