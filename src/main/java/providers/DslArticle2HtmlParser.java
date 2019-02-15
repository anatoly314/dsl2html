package providers;

public class DslArticle2HtmlParser {
    public static String getHtmlArticle(String dslArticle){
        String lines[] = dslArticle.split("\\r?\\n");
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
        return outputArticle;
    }
}
