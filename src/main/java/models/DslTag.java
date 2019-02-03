package models;

import dslTags.BTag;
import dslTags.CTag;
import dslTags.MTag;
import dslTags.PTag;

public class DslTag {
    private String tagContext;
    private String tag;
    private String color;

    private void parseTagContext(){
        String[] tagAttributes = tagContext.substring(1, tagContext.length() - 1).split(" ");
        this.tag = tagAttributes[0];
        if(tagAttributes.length > 1){
            this.color = tagAttributes[1];
        }
        System.out.println("tag: " + this.tag + " color: " + this.color);
    }

    private String getHtmlOpenTag(){
        if(this.tag.charAt(0) == 'm'){
            return MTag.getHtmlRepresentation(this.tag);
        } else if(this.tag.charAt(0) == 'c') {
            return CTag.getHtmlRepresentation(this.color);
        } else if(this.tag.charAt(0) == 'b') {
            return BTag.getHtmlRepresentation();
        } else if(this.tag.charAt(0) == 'p') {
            return PTag.getHtmlRepresentation();
        }
        return "<span>";
    }

    private String getHtmlCloseTag(){
        if(this.tag.charAt(0) == 'm'){
            return "</div>";
        }else {
            return "</span>";
        }
    }

    public DslTag(String tagContext){
        this.tagContext = tagContext;
        parseTagContext();
    }

    public String getConvertedDsl2HtmlOpenTag(){
        //TODO temporary
        return this.getHtmlOpenTag();
    }

    public String getConvertedDsl2HtmlCloseTag(){
        //TODO temporary
        return this.getHtmlCloseTag();
    }


}


