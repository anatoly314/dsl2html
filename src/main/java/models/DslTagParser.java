package models;

import dslTags.*;

public class DslTagParser {
    private String tagContext;
    private String tag;
    private String color;
    private String htmlOpenTag;
    private String htmlCloseTag;
    private boolean externalResourceTag;

    private void parseTagContext(){
        String[] tagAttributes = tagContext.substring(1, tagContext.length() - 1).split(" ");
        this.tag = tagAttributes[0];
        if(tagAttributes.length > 1){
            this.color = tagAttributes[1];
        }
        this.setHtmlOpenCloseTags();
    }

    private void setHtmlOpenCloseTags(){
        if(this.tag.charAt(0) == 'm'){
            this.htmlOpenTag = MTag.getHtmlOpenTagRepresentation(this.tag);
            this.htmlCloseTag = MTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.charAt(0) == 'c') {
            this.htmlOpenTag =  CTag.getHtmlOpenTagRepresentation(this.color);
            this.htmlCloseTag = CTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.charAt(0) == 'b') {
            this.htmlOpenTag =  BTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = BTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.charAt(0) == 'p') {
            this.htmlOpenTag =  PTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = PTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.charAt(0) == 'u') {
            this.htmlOpenTag =  UTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = UTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.equalsIgnoreCase("sub")){
            this.htmlOpenTag =  SubTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = SubTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.equalsIgnoreCase("*")) {
            this.htmlOpenTag = StarTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = StarTag.getHtmlCloseTagRepresentation();
        } else if(this.tag.equalsIgnoreCase("s")){  //we get [s] tag content from within the Node because we know resource name only there
            this.externalResourceTag = true;
        } else {
            this.htmlOpenTag = DslTag.getHtmlOpenTagRepresentation();
            this.htmlCloseTag = DslTag.getHtmlCloseTagRepresentation();
        }
    }

    public DslTagParser(String tagContext){
        this.externalResourceTag = false;
        this.tagContext = tagContext;
        parseTagContext();
    }

    public boolean isExternalResourceTag(){
        return this.externalResourceTag;
    }

    public String getConvertedDsl2HtmlOpenTag(){
        return this.htmlOpenTag;
    }

    public String getConvertedDsl2HtmlCloseTag(){
        return this.htmlCloseTag;
    }


}


