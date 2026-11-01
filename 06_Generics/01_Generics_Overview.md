# Generics Overview

Generics provide compile-time type safety and eliminate runtime `ClassCastException`s. The compiler checks types during compilation and inserts casts automatically through type erasure.

---

## 1. Generic Class

A class can define one or more type parameters.

```java
public class Test {
    public static void main(String[] args) {
        Employee employee = new Employee(50000, 28, "Harry");

        // Type-safe: compiler guarantees response is an Employee
        Response<Employee> res = new Response<>();
        res.setResponse(employee);
        res.setCode("200");
        System.out.println("Status: " + res.getCode() + ", Employee: " + res.getResponse().name);

        // Raw type: Avoid this! Compiler issues a warning and treats T as Object
        Response rawRes = new Response();
        rawRes.setResponse("Some String"); // Allowed at compile-time, risks runtime ClassCastException
    }
}

class Response<T> {
    private T response;
    private String code;

    public T getResponse() { return response; }
    public void setResponse(T response) { this.response = response; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}

class Employee {
    int salary;
    int age;
    String name;

    public Employee(int salary, int age, String name) {
        this.salary = salary;
        this.age = age;
        this.name = name;
    }
}
```

---

## 2. Generic Interface

An interface can declare type parameters. Implementing classes can either fix the type parameter to a concrete type or keep it generic:

```java
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        // Generic implementation
        Repository<String, Integer> stringRepo = new InMemoryRepository<>();
        stringRepo.save(1, "Item A");
        System.out.println("Found: " + stringRepo.findById(1));

        // Concrete implementation
        EmployeeRepository empRepo = new EmployeeRepository();
        empRepo.save("EMP-1", new Employee("Alice"));
        System.out.println("Found: " + empRepo.findById("EMP-1").name);
    }
}

interface Repository<T, ID> {
    void save(ID id, T entity);
    T findById(ID id);
}

// 1. Concrete implementation: binds types to Employee and String
class EmployeeRepository implements Repository<Employee, String> {
    private Map<String, Employee> db = new HashMap<>();

    @Override
    public void save(String id, Employee entity) { db.put(id, entity); }

    @Override
    public Employee findById(String id) { return db.get(id); }
}

// 2. Generic implementation: remains parameterized
class InMemoryRepository<T, ID> implements Repository<T, ID> {
    private Map<ID, T> storage = new HashMap<>();

    @Override
    public void save(ID id, T entity) { storage.put(id, entity); }

    @Override
    public T findById(ID id) { return storage.get(id); }
}

class Employee {
    String name;
    public Employee(String name) { this.name = name; }
}
```

---

## 3. Generic Methods & Generic Constructors

Methods and constructors can declare their own type parameter `<T>` before the return type, even inside a non-generic class:

```java
public class Test {

    // Generic constructor
    public <T> Test(T value) {
        System.out.println("Constructor called with: " + value);
    }

    // Generic method: compares two objects of type T
    public <T> boolean isEqual(T a, T b) {
        return a.equals(b);
    }

    public static void main(String[] args) {
        Test t = new Test("Utility");
        new Test(100);

        System.out.println(t.isEqual("Hello", "Hello")); // true
        System.out.println(t.isEqual(10, 20));           // false

        // Common supertype Object is inferred when passing mixed types
        System.out.println(t.isEqual("Hello", 10));      // false
    }
}
```

---

## 4. Type Parameter Naming Conventions

Standard conventions for type parameters:
- `T` - Type (general purpose)
- `E` - Element (Collections framework)
- `K`, `V` - Key and Value (Maps)
- `N` - Number
- `S`, `U`, `V` - 2nd, 3rd, 4th types

---

## 5. Bounded Type Parameters (`<T extends Bound>`)

Restricts the type parameter to a specific class or interface. This enables calling methods declared on the bound directly on `T`:

```java
public class Test {

    // Restricts T to types implementing Comparable<T>
    public static <T extends Comparable<T>> int compare(T t1, T t2) {
        return t1.compareTo(t2);
    }

    public static void main(String[] args) {
        System.out.println("Strings: " + compare("apple", "banana")); // Works: String implements Comparable<String>
        System.out.println("Integers: " + compare(10, 20));           // Works: Integer implements Comparable<Integer>

        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        // compare(p1, p2); // ❌ Compile error: Person does not implement Comparable
    }
}

class Person {
    String name;
    public Person(String name) { this.name = name; }
}
```

---

## 6. Multiple Bounds (Intersection Types)

Syntax: `<T extends Class & Interface1 & Interface2>`

**Rules:**
1. Only **one class** is allowed.
2. If a class is present, it **must be listed first**.
3. Interfaces follow using `&`.

```java
interface Identifiable {
    String getId();
}

class Employee implements Comparable<Employee>, Identifiable {
    private String id;
    private int salary;

    public Employee(String id, int salary) {
        this.id = id;
        this.salary = salary;
    }

    @Override
    public String getId() { return id; }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.salary, other.salary);
    }
}

public class Test {

    // T must extend Employee AND implement Identifiable
    public static <T extends Employee & Identifiable> T getHigherPaid(T e1, T e2) {
        return (e1.compareTo(e2) > 0) ? e1 : e2;
    }

    public static void main(String[] args) {
        Employee emp1 = new Employee("E1", 5000);
        Employee emp2 = new Employee("E2", 8000);

        Employee higher = getHigherPaid(emp1, emp2);
        System.out.println("Higher paid: " + higher.getId());
    }
}
```

---

## 7. What is NOT Allowed in Generics (Type Erasure Restrictions)

Type parameters are erased to their bounds (or `Object`) at compile time. Because of this, certain operations cannot be performed:

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 1. Primitive types are not allowed
        // List<int> list = new ArrayList<>(); // ❌ Use wrapper classes like List<Integer>

        // 2. Cannot check generic type with instanceof
        List<String> list = new ArrayList<>();
        // if (list instanceof List<String>) {} // ❌ Erased at runtime. Use instanceof List<?>

        // 3. Cannot instantiate type parameter
        // T obj = new T(); // ❌ Cannot do new T()

        // 4. Cannot create generic array
        // T[] arr = new T[10]; // ❌ Cannot create array of type parameter
    }
}

class GenericClass<T> {
    // 5. Static fields cannot have type T (shared across all instances of GenericClass)
    // static T item; // ❌ Compile error
}
```
