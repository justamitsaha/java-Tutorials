# Upper Bound Wildcards (`? extends T`)

An upper-bounded wildcard specifies that the unknown type must be `T` or a **subtype** of `T`.

$$\text{Syntax: } <? \text{ extends } T>$$

---

## 1. Hierarchy Example

Given the class hierarchy:

$$\text{Human} \longleftarrow \text{Employee} \longleftarrow \text{Developer} \longleftarrow \text{SeniorDev}$$

A parameter of type `List<? extends Employee>` accepts:
- `List<Employee>`
- `List<Developer>`
- `List<SeniorDev>`

It **rejects** `List<Human>` because `Human` is a supertype, not a subtype of `Employee`.

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Developer> devList = List.of(new Developer(70000, "Alice"), new Developer(80000, "Bob"));
        List<SeniorDev> seniorList = List.of(new SeniorDev(120000, "Charlie"));
        List<Human> humanList = List.of(new Human("Dave"));

        // ✅ Allowed: Developer and SeniorDev extend Employee
        printEmployees(devList);
        printEmployees(seniorList);

        // ❌ Compile error: Human does not extend Employee
        // printEmployees(humanList);
    }

    public static void printEmployees(List<? extends Employee> list) {
        for (Employee emp : list) {
            System.out.println(emp.name + " earns " + emp.salary);
        }
    }
}

class Human {
    String name;
    public Human(String name) { this.name = name; }
}

class Employee extends Human {
    int salary;
    public Employee(int salary, String name) {
        super(name);
        this.salary = salary;
    }
}

class Developer extends Employee {
    public Developer(int salary, String name) { super(salary, name); }
}

class SeniorDev extends Developer {
    public SeniorDev(int salary, String name) { super(salary, name); }
}
```

---

## 2. Why Upper Bounds Are Read-Only (Producer Rule)

When you have a collection typed as `List<? extends Employee>`, you can **read** from it, but you **cannot write** to it (except `null`):

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<? extends Employee> list = new ArrayList<Developer>();

        // ❌ CANNOT WRITE:
        // list.add(new Developer(50000, "Harry")); // Compile error!
        // list.add(new Employee(40000, "Ron"));     // Compile error!

        // ✅ Reading is safe: elements are guaranteed to be at least Employee
        list = List.of(new Developer(50000, "Harry"), new Employee(60000, "Ron"));
        printEmployees(list);
    }

    public static void printEmployees(List<? extends Employee> list) {
        for (Employee emp : list) {
            System.out.println(emp.name + ": " + emp.salary);
        }
    }
}

class Employee {
    int salary;
    String name;
    public Employee(int salary, String name) { this.salary = salary; this.name = name; }
}

class Developer extends Employee {
    public Developer(int salary, String name) { super(salary, name); }
}
```

### Why Adding is Prohibited:
The variable `list` could reference an `ArrayList<SeniorDev>`. If the compiler allowed adding an ordinary `Developer` or `Employee`, it would corrupt type safety for that `ArrayList<SeniorDev>`. Because the compiler cannot know the actual runtime subtype, all additions are rejected at compile time.

---

## 3. Named `<T extends Bound>` vs Wildcard `List<? extends Bound>`

Upper bound wild card be implemented in 2 ways
- Use **Wildcard (`? extends`)**: When the method is purely a consumer of data and the return type does not depend on the specific type argument.
- Use **Named `<T>`**: When you need to return elements with their exact specific type or link multiple arguments.

```java
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> intList = List.of(10, 50, 20);

        // Named T preserves return type as Integer
        Integer max = findMax(intList);
        System.out.println("Max: " + max);

        // Wildcard works cleanly for read-only iteration
        printNumbers(intList);
    }

    // Named T: return type must match the specific element type
    public static <T extends Comparable<T>> T findMax(List<T> list) {
        T max = list.get(0);
        for (T item : list) {
            if (item.compareTo(max) > 0) 
                max = item;
        }
        return max;
    }

    // Wildcard: cleaner for read-only arguments where return type does not depend on T
    public static void printNumbers(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println(n);
        }
    }
}
```

---

## 4. Nested Bounds

Wildcards can be nested to handle multi-dimensional collections:

```java
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<List<Integer>> matrix = List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6)
        );
        processMatrix(matrix);
    }

    public static void processMatrix(List<? extends List<? extends Number>> matrix) {
        for (List<? extends Number> row : matrix) {
            for (Number n : row) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }
}
```
