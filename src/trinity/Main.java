package trinity;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String prayer = "Oh Lord, won't you buy me a trash Mercedes Benz\n" +
                "My friends all drive trash Porsches, I must make trash amends\n" +
                "Worked hard all my trash lifetime, no help from my trash friends\n" +
                "So Lord, won't you buy me a trash Mercedes Benz";


        String result = Arrays.stream(prayer.split("\\s+"))
                .filter( s -> !s.equals("trash"))
                .map(s -> s.toUpperCase())
                .reduce("", (acc, el) -> acc + " " + el);

        System.out.println(result);

        /*
        //Stream<String> stream = Arrays.stream(prayer.split("\\r\\n]+"))
                .filter( (s) -> s.equals("trash") )
                .forEach( s -> System.out.println(s.toString()));
        */
        //stream.forEach(list::add);
        //System.out.println(list.size());

    }


}
