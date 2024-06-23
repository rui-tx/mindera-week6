package restaurant;

public class Main {


    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();
        restaurant.open();

        restaurant.addClients(2);
        restaurant.close();


        //dishs.forEach(System.out::println);

        //Thread client = new Thread();


        /*
        dishs.stream()
                .filter(String::isEmpty)
                .forEach(e -> new Cook(dishs));

         */


    }


}
