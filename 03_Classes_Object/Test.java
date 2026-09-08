import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Convenient way to create a list from an array
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // list.add(6); // Will throw UnsupportedOperationException because
        // Arrays.asList returns fixed-size list
        System.out.println("Arrays as list "+list);

        // Sort an array of integers, default is ascending order
        int[] arr = { 5, 2, 8, 1, 3, 7 };
        Arrays.sort(arr);
        System.out.println("Sorted array "+Arrays.toString(arr));

        //Fill Empty array
        int[] emptyArr = new int[5];
        Arrays.fill(emptyArr, 5);
        System.out.println("Filled empty array" + Arrays.toString(emptyArr));

        // Sort partial array of integers in ascending order
        int[] arr1 = { 5, 2, 8, 1, 3, 7 };
        Arrays.sort(arr1, 0, 3); // sorts only first 3 elements
        System.out.println("Partial Sorting or Array"+ Arrays.toString(arr1));

        // Search for an element in an array of integers
        int[] arr2 = { 5, 2, 8, 1, 3, 7 };
        Arrays.sort(arr2); // binary search requires sorted array
        int key = 999;
        int index = Arrays.binarySearch(arr2, key); // It has log(n) time complexity
        // If item is not found, it returns -(insertion point - 1), where insertion
        // point is the index
        // at which the key would be inserted to maintain sorted order.
        System.out.println("Index of " + key + ": " + index);
        // upper bound is exclusive, lower bound is inclusive
        index = Arrays.binarySearch(arr2, 0, 3, key);

        System.out.println("Index of " + key + " in first 3 elements: " + index);

        Employee employees[] = {
                new Employee("Alice", 30),
                new Employee("Bob", 25),
                new Employee("Charlie", 35)
        };

        Arrays.sort(employees); // sorts based on age because of compareTo method in Employee class
        System.out.println(Arrays.toString(employees)); // prints the sorted array of employees

        // Sort in reverse order based on age
        Arrays.sort(employees, (e1, e2) -> e2.age - e1.age); // sorts based on age in descending order
        System.out.println(Arrays.toString(employees)); // prints the sorted array of employees based on

        // using custom comparator to sort based on name
        Arrays.sort(employees, (e1, e2) -> e1.name.compareTo(e2.name)); // sorts based on name
        System.out.println(Arrays.toString(employees)); // prints the sorted array of employees based on name
    }
}

class Employee implements Comparable<Employee> {
    int age;
    String name;

    Employee(String name, int age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee :: "+ age +" :: "+ name+ " | ";
    }

    @Override
    public int compareTo(Employee other) {
        return this.age - other.age;
    }
    
}
