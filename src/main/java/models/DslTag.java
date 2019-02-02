package models;

public class DslTag {
    private String tagContext;

    public DslTag(String tagContext){
        this.tagContext = tagContext;
    }

    public String getConvertedDsl2HtmlOpenTag(){
        //TODO temporary
        return this.tagContext;
    }

    public String getConvertedDsl2HtmlCloseTag(){
        //TODO temporary
        return "</div>";
    }


}


