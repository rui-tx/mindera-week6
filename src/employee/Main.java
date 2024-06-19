package employee;


import employee.db.DB;

public class Main {
    public static void main(String[] args) {


        EmployeeAnalyzer employeeAnalyzer = new EmployeeAnalyzer();

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.countEmployees() = " + employeeAnalyzer.countEmployees(DB.getDevelopmentDepartment(), 7));

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.findEmployeeBySalary() = " + employeeAnalyzer.findEmployeeBySalary(DB.getHrDepartment(), 1200));

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.findOldestEmployees() = " + employeeAnalyzer.findOldestEmployees(DB.getMarketingDepartment(), 3));

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.findFirstEmployeeByAge() = " + employeeAnalyzer.findFirstEmployeeByAge(DB.getDevelopmentDepartment(), 20));

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.findAverageSalary() = " + employeeAnalyzer.findAverageSalary(DB.getSalesDepartment()));

        System.out.println("-".repeat(100));
        System.out.println("EmployeeAnalyzer.findCommonNames() = " + employeeAnalyzer.findCommonNames(DB.getDevelopmentDepartment(), DB.getMarketingDepartment()));
        /*
         */

    }
}
