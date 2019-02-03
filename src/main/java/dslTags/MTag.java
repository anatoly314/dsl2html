package dslTags;

public class MTag {
    public static String getHtmlRepresentation(String dslTag){
        StringBuilder htmlTag = new StringBuilder();
        htmlTag.append("<div");

        if(dslTag.length() > 1){ //m0, m1, etc...
            int paddingIndex = Integer.parseInt(dslTag.substring(1));
            htmlTag.append(" " + TagTemplates.styleTemplate.replace("CONTENT", "padding-left:" + paddingIndex + "em"));
        }

        htmlTag.append(">");
        return htmlTag.toString();
    }
}
