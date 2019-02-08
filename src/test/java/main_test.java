import models.Node;
import providers.DslRowParser;
import providers.FileProvider;

public class main_test {
    public static void main(String[] args){

        String htmlTemplate = FileProvider.getFileByFileName("template.html");

        String fileText = FileProvider.getFileByFileName("single-article.txt");
        String lines[] = fileText.split("\\r?\\n");
        StringBuilder parsedArticle = new StringBuilder();
        for (String line : lines) {
            if(line.indexOf("\t") >= 0){
                Node node = DslRowParser.parseArticleRowToNode(line);
                String convertedText = node.getConvertedNodeText();
                convertedText = convertedText.replace("\\", "");
                parsedArticle.append(convertedText);
                parsedArticle.append("\n");
            }

        }
        String outputArticle = parsedArticle.toString();
        htmlTemplate = htmlTemplate.replace("{{CONTENT}}", outputArticle);
        System.out.println(outputArticle);
        FileProvider.saveToFile("article-output.html", htmlTemplate);

    }
}
