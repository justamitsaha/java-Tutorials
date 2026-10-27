import java.util.ArrayList;
import java.util.List;

class Test {
    // Bounded generic method - only works on Comparable types
    public static <T extends Comparable<T>> int compare(T t1, T t2) {
        return t1.compareTo(t2);
    }

    // Generic constructor with bounded type
    public <T extends Number> Test(T value) {
        System.out.println("Generic constructor with Number: " + value);
    }

    public static void main(String[] args) {
        // ✅ Works because String implements Comparable<String>
        System.out.println("Compare Strings: " + compare("apple", "banana"));
        // ✅ Works because Integer implements Comparable<Integer>
        System.out.println("Compare Integers: " + compare(10, 20));
        Employee p1 = new Employee(50000, 28);
        Employee p2 = new Employee(60000, 30);
        // ❌ Compile-time error: Person does not implement Comparable
        //System.out.println(compare(p1, p2));
    }
}

class Employee {
    int salary;
    int age;

    public Employee(int salary, int age) {
        super();
        this.salary = salary;
        this.age = age;
    }
}


