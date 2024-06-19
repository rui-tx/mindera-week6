package employee.employee;

public class Employee {

    private static int employeeNumber;

    private final int id;
    private String firstName;
    private String lastname;
    private int salary;
    private int age;
    private int startingYear;

    public Employee(String firstName, String lastname, int salary, int age, int startingYear) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.salary = salary;
        this.age = age;
        this.startingYear = startingYear;
        id = ++employeeNumber;
    }

    public static int getEmployeeNumber() {
        return employeeNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public int getStartingYear() {
        return startingYear;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", startingYear=" + startingYear +
                '}';
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName);
    }
     */
}
