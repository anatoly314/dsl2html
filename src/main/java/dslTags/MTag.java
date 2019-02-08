package dslTags;

public class MTag extends DslTag{
    public static String getHtmlOpenTagRepresentation(String dslTag){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<div");

        if(dslTag.length() > 1){ //m0, m1, etc...
            int paddingIndex = Integer.parseInt(dslTag.substring(1));
            htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "padding-left:" + paddingIndex + "em"));
        }

        htmlTag.append(">");
        return htmlTag.toString();
    }
    public static String getHtmlCloseTagRepresentation(){
        return "</div>";
    }
}
