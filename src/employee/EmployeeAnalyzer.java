package employee;

import employee.employee.Employee;

import java.util.*;

public class EmployeeAnalyzer {

    public long countEmployees(List<Employee> employees, int years) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return employees.stream()
                .filter(e -> (currentYear - e.getStartingYear()) > years)
                .count();
    }

    public List<String> findEmployeeBySalary(List<Employee> employees, int salary) {
        return employees.stream()
                .filter(e -> e.getSalary() > salary)
                .map(Employee::getFirstName)
                .toList();
    }

    public List<Employee> findOldestEmployees(List<Employee> employees, int numberOfEmployees) {
        return employees.stream()
                .sorted((e1, e2) -> e2.getAge() - e1.getAge())
                .limit(numberOfEmployees)
                .toList();
    }

    public Optional<Employee> findFirstEmployeeByAge(List<Employee> employees, int age) {
        return employees.stream()
                .filter(e -> e.getAge() > age)
                .findFirst();
    }

    public Double findAverageSalary(List<Employee> employees) {
        int sum = employees.stream()
                .map(Employee::getSalary)
                .reduce(0, (acc, el) -> acc + el);

        return (double) sum / employees.size();
    }

    public List<String> findCommonNames(List<Employee> firstDepartment, List<Employee> secondDepartment) {
        Set<String> set = new HashSet<>(firstDepartment.stream()
                .map(Employee::getFirstName)
                .toList());

        return secondDepartment.stream()
                .map(Employee::getFirstName)
                .filter(set::contains)
                .toList();

        // equal and hash override on employee needed, only first name
        /*
        Set<Employee> set = new HashSet<>(firstDepartment);
        return secondDepartment.stream()
                .filter(set::contains)
                .map(Employee::getFirstName)
                .toList();

         */
    }
}
