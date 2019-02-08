package dslTags;

public class StarTag extends DslTag {
    public static String getHtmlOpenTagRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "color:grey"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
