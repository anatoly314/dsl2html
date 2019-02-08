package dslTags;

public class SubTag extends DslTag{
    public static String getHtmlOpenTagRepresentation(){
        return "<sub>";
    }

    public static String getHtmlCloseTagRepresentation(){
        return "</sub>";
    }
}
