import java.util.ArrayList;
import java.util.List;

public class Test{
    public static void main(String[] args) {
        List<? extends Developer> list = new ArrayList<>();
        Employee emp = new Employee(22132, "harry");
        Developer dev = new Developer(22132, "harry");
        list.add(emp);
        list.add(dev);
        // The list could be a List<Developer>, List<SeniorDev>, or another subtype of Employee. Adding a specific subtype could violate type safety.
        // For example, if the list is a List<Developer>, adding a SeniorDev would be incorrect.

        //One can have a confussion that if I can't add to this list what is the point of having it?
        // The point is that you can read from it. 
        // You can safely read elements from the list, and they will be of type Employee or a subtype of Employee. 
        // This is useful when you want to work with a collection of objects that share a common superclass but may have different specific types.    

        List<Employee> employeeList = List.of(new Developer(50000, "Alice"), new Developer(60000, "Bob"));
        printEmployeeDetails(employeeList);


    }

    public static void printEmployeeDetails(List<? super Developer> list) {
        for (Employee emp : list) {
            System.out.println("Name: " + emp.name + ", Salary: " + emp.salary);
        }
    }


}

class Employee {
    int salary;
    String name;

    public Employee(int salary, String name) {
        super();
        this.salary = salary;
        this.name = name;
    }
}

class Developer extends Employee{
    public Developer(int salary, String name) {
        super(salary, name);
    }
}



