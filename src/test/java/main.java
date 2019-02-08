import models.Node;
import providers.DslRowParser;

public class main {
    public static void main(String[] args){
        String line1 = "\t[m0][c darkslategray]{{hs}}‘Cy’{{/hs}} [/c][b] Young[/b] [c rosybrown]\\[[/c][c darkslategray][b]Cy Young[/b][/c][c rosybrown]\\][/c] [p]BrE[/p] [c darkgray] [/c][s]z__cy_young_1_gb_1.wav[/s] [p]NAmE[/p] [c darkgray] [/c][s]z__cy_young_1_us_1.wav[/s]Hello World!\n";
        Node node = DslRowParser.parseArticleRowToNode(line1);
        System.out.println(node.getConvertedNodeText());
    }
}
