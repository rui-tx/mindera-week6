package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String string;
        String result;
        // exercise 1
        System.out.println("exercise 1");
        string = "this is a new string to test exercise 1 peqq pq peeeeq psldkfjskdfp pqqq";
        regex("p(q)*", string);

        // exercise 2
        System.out.println("exercise 2");
        string = "this_is_a new string to+test_exercise+2";
        regex("([a-z_a-z])+", string);
        regex("([a-z])*_([a-z])+", string);
        regex("([a-z])+_{1}([a-z])+", string);

        // exercise 3
        System.out.println("exercise 3");
        string = "abc_" + "Ajd_d1" + "RuiT" + "This is a Test String";
        regex("([A-Z]){1}[a-z]+", string);

        //exercise 4
        System.out.println("exercise 4");
        string = "peeq\n" + "pq\n" + "qeep\n" + "psdkfjsdfq\n";
        regex("p.*q", string);

        //exercise 5
        System.out.println("exercise 5");
        string = "peeq\n" + "pq\n" + "qeep\n" + "psdkfjsdfq\n";
        regex("\\w+", string);

        //exercise 6
        System.out.println("exercise 6");
        string = "This is a new string to test exercise 6.";
        result = string.replaceAll("[aeiou]", "");
        System.out.println(result);

        //exercise 7
        System.out.println("exercise 7");
        string = "912345678\n" + "253577878" + "910345678\n";
        regex("^(\\+?351)?9[1236]\\d{7}", string);
        regex("^(\\+?351)|(00351)?9[1236]\\d{7}", string);
        regex("^((00)?(\\+?351))?9[1236]\\d{7}", string);

        //exercise 8
        System.out.println("exercise 8");
        string = "This is a new string to test exercise 8.";
        //result = string.replaceAll("[^a-zA-Z0-9]+", "").toLowerCase();
        result = string.replaceAll("[^\\p{Alnum}]+", "").toLowerCase(); //[[:alnum:]] does not work
        System.out.println(result);
    }

    public static void regex(String regex, String string) {

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
        }
    }

    public static String regexReplaceAll(String regex, String string) {
        return string.replaceAll(regex, "");
    }
}