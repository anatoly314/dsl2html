package models;

public class NodeText implements NodeInterface {
    private StringBuilder nodeText;

    public NodeText(){
        this.nodeText = new StringBuilder();
    }

    public void addChar(char ch){
        this.nodeText.append(ch);
    }

    public String getText(){
        return this.nodeText.toString();
    }
}
