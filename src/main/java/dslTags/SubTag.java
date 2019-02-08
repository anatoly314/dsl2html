package dslTags;

public class SubTag extends DslTag{
    public static String getHtmlOpenTagRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<sub>");
        return htmlTag.toString();
    }
}
