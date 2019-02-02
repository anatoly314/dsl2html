import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_test {

    private static final Pattern dslOpenPattern = Pattern.compile("\\[[^\\[\\/]+?\\]", Pattern.MULTILINE);
    private static final Pattern dslClosePattern = Pattern.compile("\\[\\/([^\\[])+?\\]", Pattern.MULTILINE);
    private static final Pattern dslPattern = Pattern.compile("\\[[^\\[]+?\\]", Pattern.MULTILINE);

    private static boolean beginWithDslTag(String text){
        Matcher matcher = dslPattern.matcher(text);
        if(matcher.find() && matcher.start() == 0){//we looking for first occurance only
            int start = matcher.start();
            int end = matcher.end();
            String dslTagText = text.substring(start, end);

            if(dslTagText.charAt(1) == '/'){
                System.out.println("Close tag: " + dslTagText);
            } else {
                System.out.println("Open tag: " + dslTagText);
            }

            System.out.println(text);
            String textWithoutDslTag = text.substring(end);
            System.out.println(textWithoutDslTag);

        }
        return false;
    }

    public static void main(String[] args){
        String line1 = "\t[m0][c darkslategray]{{hs}}‘Cy’{{/hs}} [/c][b] Young[/b] [c rosybrown]\\[[/c][c darkslategray][b]Cy Young[/b][/c][c rosybrown]\\][/c] [p]BrE[/p] [c darkgray] [/c][s]z__cy_young_1_gb_1.wav[/s] [p]NAmE[/p] [c darkgray] [/c][s]z__cy_young_1_us_1.wav[/s]\n";
        while (line1.length() > 0){


            Matcher matcher = dslPattern.matcher(line1);
            if(matcher.find() && matcher.start() == 0){//we looking for first occurance only
                int start = matcher.start();
                int end = matcher.end();
                String dslTagText = line1.substring(start, end);

                if(dslTagText.charAt(1) == '/'){
                    System.out.println("Close tag: " + dslTagText);
                } else {
                    System.out.println("Open tag: " + dslTagText);
                }

                System.out.println(line1);
                String textWithoutDslTag = line1.substring(end);
                System.out.println(textWithoutDslTag);

            }


            line1 = line1.substring(1);
        }

    }
}
