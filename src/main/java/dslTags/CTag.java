package dslTags;

public class CTag extends DslTag {
    public static String getHtmlOpenTagRepresentation(String color){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "color:" + color));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
