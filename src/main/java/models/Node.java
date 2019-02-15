package models;

import dslTags.STag;

import java.util.ArrayList;
import java.util.List;

public class Node implements NodeInterface {
    private DslTagParser dslTagParser;
    private Node parent;
    private List<NodeInterface> children;
    private NodeText currentNodeText;
    private int level;
    private String convertedNodeText;
    private boolean closedNode;
    private String dictionaryName;
    private boolean rowWithoutDslTags;  //some dictionaries have row without dsl tags and need to be treat accordingly

    public Node(String dictionaryName){
        this.rowWithoutDslTags = true;
        this.closedNode = false;
        this.children = new ArrayList<>();
        this.level = 0;
        this.dictionaryName = dictionaryName;
    }

    private Node(Node parent, DslTagParser dslTagParser, String dictionaryName){
        this(dictionaryName);
        this.parent = parent;
        this.dslTagParser = dslTagParser;
        this.level = parent.getLevel() + 1;
    }

    /**
     * Creating a nested Node and passing a current Node as parent of nested Node
     * @return
     */
    public Node getChild(DslTagParser tag){
        this.rowWithoutDslTags = false; //if we go deeper it means that we found at least one nested tag
        Node node = new Node(this, tag, this.dictionaryName);
        this.children.add(node);
        this.currentNodeText = null; //reset currentNodeText
        return node;
    }

    public void addChar(char ch){
        if(this.currentNodeText == null){
            this.currentNodeText = new NodeText();
            this.children.add(this.currentNodeText);
        }
        this.currentNodeText.addChar(ch);
    }

    public void addText(String text){
        while (text.length() > 0){
            char ch = text.charAt(0);
            addChar(ch);
            text = text.substring(1);
        }
    }

    public boolean isClosedNode(){
        return this.closedNode;
    }
    public Node getParent(){
        return this.parent;
    }

    public void closeNode(){
        StringBuilder convertedNodeTextBuilder = new StringBuilder();
        for(NodeInterface node: this.children){
            if(node instanceof NodeText){
                convertedNodeTextBuilder.append(((NodeText) node).getText());
            }else if(node instanceof Node){
                convertedNodeTextBuilder.append(((Node) node).getConvertedNodeText());
            }
        }

        if(this.dslTagParser != null && !this.dslTagParser.isExternalResourceTag()){
            convertedNodeTextBuilder.insert(0, this.dslTagParser.getConvertedDsl2HtmlOpenTag());
            convertedNodeTextBuilder.append(this.dslTagParser.getConvertedDsl2HtmlCloseTag());
        }else if(this.dslTagParser != null && this.dslTagParser.isExternalResourceTag()){   //[s] tag
            String resourceName = convertedNodeTextBuilder.toString();
            convertedNodeTextBuilder = new StringBuilder(STag.getExternalResource(resourceName, dictionaryName));
        }

        this.closedNode = true;
        this.convertedNodeText = convertedNodeTextBuilder.toString();
    }

    public int getLevel(){
        return this.level;
    }

    public String getConvertedNodeText(){
        if(this.rowWithoutDslTags && this.level == 0){  //if top Node and it hasn't DSL tag at all we manually enclose it by <div> element
            return "<div>" + this.convertedNodeText + "</div>";
        }
        return this.convertedNodeText;
    }
}
