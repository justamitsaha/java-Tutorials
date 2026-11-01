# Lower Bound Wildcards (`? super T`)

A lower-bounded wildcard specifies that the unknown type must be `T` or a **supertype** of `T`.

$$\text{Syntax: } <? \text{ super } T>$$

---

## 1. Hierarchy Example

Given the class hierarchy:

$$\text{Object} \longleftarrow \text{Human} \longleftarrow \text{Employee} \longleftarrow \text{Developer} \longleftarrow \text{SeniorDev}$$

A parameter of type `List<? super Developer>` accepts:
- `List<Developer>`
- `List<Employee>`
- `List<Human>`
- `List<Object>`

It **rejects** `List<SeniorDev>` because `SeniorDev` is a subtype, not a supertype.

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Developer> devList = new ArrayList<>();
        List<Employee> empList   = new ArrayList<>();
        List<Human> humanList    = new ArrayList<>();
        List<Object> objList     = new ArrayList<>();

        // Accepts Developer or any of its supertypes
        addDeveloper(devList, new Developer("Harry"));
        addDeveloper(empList, new Developer("Ron"));
        addDeveloper(humanList, new Developer("Hermione"));
        addDeveloper(objList, new Developer("Neville"));

        // List<SeniorDev> seniorList = new ArrayList<>();
        // addDeveloper(seniorList, new Developer("Harry")); // ❌ Compile error: SeniorDev is a subtype
    }

    public static void addDeveloper(List<? super Developer> list, Developer dev) {
        list.add(dev); // Safe: list is guaranteed to be capable of holding Developer
    }
}

class Human {
    String name;
    public Human(String name) { this.name = name; }
}

class Employee extends Human {
    public Employee(String name) { super(name); }
}

class Developer extends Employee {
    public Developer(String name) { super(name); }
}

class SeniorDev extends Developer {
    public SeniorDev(String name) { super(name); }
}
```

---

## 2. Read vs Write Rules (Consumer Rule)

- **Writing**: You can safely add `Developer` (and any subtype of `Developer`).
  - You **cannot** add an `Employee` or `Human` because the underlying list could be a `List<Developer>`.
- **Reading**: The compiler only guarantees that elements retrieved from `List<? super Developer>` are of type `Object`.

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<? super Developer> list = new ArrayList<Employee>();

        // ✅ SAFE TO WRITE: Developer or its subtypes
        list.add(new Developer("Harry"));
        list.add(new SeniorDev("Harry"));

        // ❌ CANNOT WRITE supertypes: list could be List<Developer>
        // list.add(new Employee("Ron")); // Compile error!
        // list.add(new Human("Hermione")); // Compile error!

        // ❌ CANNOT READ as Developer: list could be List<Object>
        // Developer d = list.get(0); // Compile error!

        // ✅ Reading only guarantees Object:
        Object obj = list.get(0);
        System.out.println("Read as Object: " + obj);
    }
}

class Human {
    String name;
    public Human(String name) { this.name = name; }
}

class Employee extends Human {
    public Employee(String name) { super(name); }
}

class Developer extends Employee {
    public Developer(String name) { super(name); }
}

class SeniorDev extends Developer {
    public SeniorDev(String name) { super(name); }
}
```

---

## 3. Real-World Applications

### A. Linking Producer and Consumer (`copy`)
The classic `Collections.copy` pattern demonstrates `extends` (producer) and `super` (consumer) working together:

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Developer> src = List.of(new Developer("Alice"), new Developer("Bob"));
        List<Employee> dest = new ArrayList<>();

        // Reads from Developer list, writes into Employee list
        copy(src, dest);
        System.out.println("Copied elements: " + dest.size());
    }

    // src produces T (extends), dest consumes T (super)
    public static <T> void copy(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }
}

class Employee {
    String name;
    public Employee(String name) { this.name = name; }
}

class Developer extends Employee {
    public Developer(String name) { super(name); }
}
```

### B. `Comparator<? super T>`
Sorting methods use `Comparator<? super T>` so that a comparator defined on a parent type can sort a collection of subtypes:

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1));

        // Comparator written for Number
        Comparator<Number> numberComparator = (n1, n2) -> Double.compare(n1.doubleValue(), n2.doubleValue());

        // Works because Comparator<? super Integer> accepts Comparator<Number>
        numbers.sort(numberComparator);
        System.out.println("Sorted: " + numbers);
    }
}
```

---

## 4. What is NOT Allowed with `super`

1. **`super` cannot be used in type parameter declarations**:
   ```java
   // ❌ Syntax error: super is only valid with wildcards (?)
   // public static <T super Integer> void test(T item) { }
   ```

2. **Avoid returning `List<? super T>`**:
   Returning a lower-bounded wildcard forces callers to treat all returned elements as `Object`:
   ```java
   // ❌ Bad API design
   public static List<? super Integer> badReturn() {
       return new java.util.ArrayList<Number>();
   }
   ```
