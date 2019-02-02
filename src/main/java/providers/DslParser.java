package providers;

import models.DslTag;
import models.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DslParser {

    private static final Pattern dslOpenPattern = Pattern.compile("\\[[^\\[\\/]+?\\]", Pattern.MULTILINE);
    private static final Pattern dslClosePattern = Pattern.compile("\\[\\/([^\\[])+?\\]", Pattern.MULTILINE);
    private static final Pattern dslPattern = Pattern.compile("\\[[^\\[]+?\\]", Pattern.MULTILINE);

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
        System.out.println(row);
        Node node = new Node();
        while (row.length() > 0){


            Matcher matcher = dslPattern.matcher(row);

            if(matcher.find() && matcher.start() == 0){  //if we found DSL tag
                int start = matcher.start();
                int end = matcher.end();
                String dslTagText = row.substring(start, end);

                if(isStandAloneTag(dslTagText)){
                    node.addText(dslTagText);
                }else if(dslTagText.charAt(1) == '/'){    //we meet close tag => we finished parsing content between tags and return one level up
                    node.closeNode();
                    node = node.getParent();
                } else {        //we meet open tag => we go one level deep and begin to parse this tag
                    DslTag dslTag = new DslTag(dslTagText);
                    node = node.getChild(dslTag);
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

        System.out.println(node.getConvertedNodeText());
        return node;
    }
}
