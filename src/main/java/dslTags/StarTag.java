package dslTags;

public class StarTag extends DslTag {
    public static String getHtmlOpenTagRepresentation(){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<span");
        htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "color:grey"));
        htmlTag.append(" " + TagTemplates.classTemplate.replace("CONTENT", "secondary-content"));
        htmlTag.append(">");
        return htmlTag.toString();
    }
}
