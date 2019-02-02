package models;

import java.util.ArrayList;
import java.util.List;

public class Node implements NodeInterface {
    private DslTag dslTag;
    private Node parent;
    private List<NodeInterface> children;
    private NodeText currentNodeText;
    private int level;
    private String convertedNodeText;

    public Node(){
        this.children = new ArrayList<>();
        this.level = 0;
    }

    public Node(Node parent, DslTag dslTag){
        this();
        this.parent = parent;
        this.dslTag = dslTag;
        this.level = parent.getLevel() + 1;
    }

    /**
     * Creating a nested Node and passing a current Node as parent of nested Node
     * @return
     */
    public Node getChild(DslTag tag){
        Node node = new Node(this, tag);
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

        if(this.dslTag != null){
            convertedNodeTextBuilder.insert(0, this.dslTag.getConvertedDsl2HtmlOpenTag());
            convertedNodeTextBuilder.append(this.dslTag.getConvertedDsl2HtmlCloseTag());
        }

        this.convertedNodeText = convertedNodeTextBuilder.toString();
    }

    public int getLevel(){
        return this.level;
    }

    public String getConvertedNodeText(){
        return this.convertedNodeText;
    }
}
