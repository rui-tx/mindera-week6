package employee.db;


import employee.employee.Employee;

import java.util.Arrays;
import java.util.List;

public class DB {

    private static List<Employee> hrDepartment = Arrays.asList(
            new Employee("Edna", "Heller", 1450, 34, 2014),
            new Employee("Reina", "Hopkins", 1500, 43, 2014),
            new Employee("Frank", "Hoots", 1400, 35, 2016),
            new Employee("Diana", "McCall", 1400, 30, 2016),
            new Employee("Peter", "O'Brien", 1350, 29, 2017),
            new Employee("Mary", "Singer", 1400, 32, 2017),
            new Employee("Samuel", "Rhodes", 1350, 28, 2017),
            new Employee("Rita", "Myers", 1250, 26, 2019),
            new Employee("James", "Milne", 1350, 28, 2019),
            new Employee("Petra", "Boyan", 1250, 28, 2019),
            new Employee("Fred", "Zachary", 1350, 30, 2019),
            new Employee("Diana", "Fuentes", 1350, 26, 2019),
            new Employee("Katherine", "Mills", 1250, 24, 2019),
            new Employee("Brian", "Ashby", 1450, 43, 2019),
            new Employee("Tracy", "Forge", 1200, 23, 2020),
            new Employee("Amir", "Ishaan", 1200, 24, 2020),
            new Employee("Lucas", "Wright", 1200, 23, 2020),
            new Employee("Mary", "Winston", 1200, 24, 2020)
    );

    private static List<Employee> salesDepartment = Arrays.asList(
            new Employee("Melissa", "Ashby", 1600, 45, 2014),
            new Employee("William", "Bourne", 1550, 35, 2015),
            new Employee("Emily", "Walsh", 1550, 37, 2015),
            new Employee("Luke", "Freedman", 1500, 34, 2015),
            new Employee("Grace", "Sherman", 1550, 34, 2015),
            new Employee("James", "Campbell", 1600, 43, 2016),
            new Employee("Oliver", "Wallace", 1450, 34, 2016),
            new Employee("Seth", "Geller", 1450, 32, 2017),
            new Employee("Michaela", "Mendes", 1450, 30, 2017),
            new Employee("Baron", "Margo", 1400, 31, 2018),
            new Employee("Ariana", "McLoughlin", 1450, 33, 2018),
            new Employee("Lou", "Sakamoto", 1400, 31, 2018),
            new Employee("Luke", "Tanner", 1350, 31, 2019),
            new Employee("Ursula", "Crane", 1500, 34, 2019),
            new Employee("Mary", "Biggs", 1300, 25, 2019)
    );

    private static List<Employee> marketingDepartment = Arrays.asList(
            new Employee("Patricia", "Hartley", 1500, 36, 2016),
            new Employee("John", "Nguyen", 1500, 35, 2017),
            new Employee("Janine", "Styles", 1450, 34, 2017),
            new Employee("Alexandra", "Antero", 1400, 32, 2018),
            new Employee("Holly", "Anderson", 1350, 27, 2019),
            new Employee("Fred", "Amos", 1400, 30, 2019),
            new Employee("Owen", "Brahim", 1350, 31, 2019),
            new Employee("Ross", "Greene", 1400, 31, 2019),
            new Employee("Sarah", "Bauer", 1300, 26, 2020),
            new Employee("Theresa", "Thomsen", 1250, 25, 2020)
    );

    private static List<Employee> developmentDepartment = Arrays.asList(
            new Employee("Roy", "Richardson", 1850, 53, 2014),
            new Employee("George", "Tehran", 1850, 48, 2014),
            new Employee("Ruth", "Robson", 1850, 48, 2014),
            new Employee("Alina", "Hall", 1800, 43, 2015),
            new Employee("George", "Tanner", 1800, 45, 2015),
            new Employee("Peter", "Winchester", 1750, 38, 2016),
            new Employee("Victor", "Sanchez", 1700, 35, 2016),
            new Employee("Sarah", "Lopez", 1600, 35, 2017),
            new Employee("Deirdre", "Finn", 1750, 36, 2017),
            new Employee("Emily", "Young", 1600, 34, 2017),
            new Employee("Zena", "Porter", 1600, 36, 2018),
            new Employee("Tanner", "Clark", 1600, 34, 2018),
            new Employee("Ava", "Kerr", 1550, 35, 2018),
            new Employee("William", "Joyce", 1500, 31, 2018),
            new Employee("Tristan", "Charlton", 1450, 27, 2019),
            new Employee("Kirsten", "Trotter", 1400, 28, 2019),
            new Employee("Tom", "Robertson", 1650, 31, 2019),
            new Employee("Patricia", "Rooster", 1350, 25, 2019),
            new Employee("Sarah", "Murphy", 1300, 26, 2019),
            new Employee("Grant", "Hamilton", 1300, 25, 2019),
            new Employee("Aaron", "Bell", 1400, 32, 2019),
            new Employee("Adriana", "Barilski", 1300, 27, 2019),
            new Employee("Georgina", "Ives", 1300, 24, 2019),
            new Employee("Alex", "Nunes", 1350, 26, 2019),
            new Employee("Winston", "Craig", 1500, 31, 2019),
            new Employee("Oliver", "Styles", 1250, 25, 2019),
            new Employee("John", "Heller", 1350, 27, 2020),
            new Employee("Anna", "Vernon", 1300, 26, 2020),
            new Employee("Riley", "Beischel", 1250, 25, 2020),
            new Employee("Mary", "Forbes", 1400, 29, 2020),
            new Employee("Liam", "Miller", 1250, 24, 2020),
            new Employee("Reece", "Duckstein", 1250, 26, 2020),
            new Employee("Laura", "Duckstein", 1250, 25, 2020),
            new Employee("Reuben", "Stawarski", 1250, 24, 2020),
            new Employee("Nathaniel", "Porter", 1300, 26, 2020),
            new Employee("Fred", "Nichols", 1300, 26, 2020),
            new Employee("Kevin", "Beardsley", 1450, 35, 2020),
            new Employee("Emily", "Barnacle", 1250, 24, 2020),
            new Employee("Brian", "Mills", 1250, 25, 2020)
    );

    public static List<Employee> getHrDepartment() {
        return hrDepartment;
    }

    public static List<Employee> getSalesDepartment() {
        return salesDepartment;
    }

    public static List<Employee> getMarketingDepartment() {
        return marketingDepartment;
    }

    public static List<Employee> getDevelopmentDepartment() {
        return developmentDepartment;
    }
}
