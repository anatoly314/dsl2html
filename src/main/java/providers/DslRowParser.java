package providers;

import models.DslTagParser;
import models.Node;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DslRowParser {

    private static final Pattern dslOpenPattern = Pattern.compile("\\[[^\\[\\/]+?\\]", Pattern.MULTILINE);
    private static final Pattern dslClosePattern = Pattern.compile("\\[\\/([^\\[])+?\\]", Pattern.MULTILINE);
    private static final Pattern dslTagPattern = Pattern.compile("\\[[^\\[]+?\\]", Pattern.MULTILINE);
    private static final Pattern dslServiceTagPattern = Pattern.compile("\\{\\{.*?\\}\\}", Pattern.MULTILINE);

    private static boolean isStandAloneTag(String dslTag){
        if(dslTag.equalsIgnoreCase("[br]")){
            //TODO stand alone tag need to be converted too
            return true;
        }else {
            return false;
        }
    }

    public static Node parseArticleRowToNode(String row){
        row = row.trim();
        row = StringEscapeUtils.escapeHtml4(row);
        Node node = new Node();
        while (row.length() > 0){


            Matcher dslTagMatcher = dslTagPattern.matcher(row);
            Matcher dslServiceTagMatcher = dslServiceTagPattern.matcher(row);

            //we ignore service tag of type {{...}}
            //https://anatoly314.github.io/dsl-manual/#tag_service.html#comm
            if(dslServiceTagMatcher.find() && dslServiceTagMatcher.start() == 0){
                int end = dslServiceTagMatcher.end();
                row = row.substring(end);
                continue;
            }

            if(dslTagMatcher.find() && dslTagMatcher.start() == 0){  //if we found DSL tag
                int start = dslTagMatcher.start();
                int end = dslTagMatcher.end();
                String dslTagText = row.substring(start, end);

                if(isStandAloneTag(dslTagText)){
                    node.addText(dslTagText);
                }else if(dslTagText.charAt(1) == '/'){    //we meet close tag => we finished parsing content between tags and return one level up
                    node.closeNode();
                    node = node.getParent();
                } else {        //we meet open tag => we go one level deep and begin to parse this tag
                    DslTagParser dslTagParser = new DslTagParser(dslTagText);
                    node = node.getChild(dslTagParser);
                }

                row = row.substring(end);
            }else{  //we parse text between DSL tags
                char textChar = row.charAt(0);
                node.addChar(textChar);
                row = row.substring(1);
            }

        }

        while (node.getLevel() > 0){
            node.closeNode();
            node = node.getParent();
            node.closeNode();
        }

        return node;
    }
}
