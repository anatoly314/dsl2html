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
            int firstCharCode = (int)line.charAt(0);
            if(firstCharCode == 32 || firstCharCode == 9){//if line begins with space
                String convertedText = DslRowParser.parseDslArticleRowToHtmlRow(line);
                parsedArticle.append(convertedText);
                parsedArticle.append("\n");
            }else if(!line.trim().isEmpty()){
                System.out.println(line);
                parsedArticle.append("<br><hr><br>\n");
            }

        }
        String outputArticle = parsedArticle.toString();
        htmlTemplate = htmlTemplate.replace("{{CONTENT}}", outputArticle);
        FileProvider.saveToFile("article-output.html", htmlTemplate);

    }
}
