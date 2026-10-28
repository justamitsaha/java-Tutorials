# Lower Bound Wildcards in Java Generics

## 1. What is a lower-bounded wildcard?

A lower-bounded wildcard uses this syntax:

``` java
<? super T>
```

It means the generic type is `T` or a **superclass of `T`**.

For example:

``` java
List<? super Developer>
```

can refer to a:

-   `List<Developer>`
-   `List<Employee>`, if `Developer extends Employee`
-   `List<Human>`, if `Employee extends Human`
-   `List<Object>`

It cannot refer to a `List<SeniorDev>` if `SeniorDev extends Developer`,
because `SeniorDev` is a *subclass* of `Developer`, not a superclass.

Lower bounds are especially useful when a method needs to **put values
of a known type into a collection**.

------------------------------------------------------------------------

## 2. A simple example: add developers to a list

``` java
import java.util.ArrayList;
import java.util.List;

class Human {
    String name;

    Human(String name) {
        this.name = name;
    }
}

class Employee extends Human {
    Employee(String name) {
        super(name);
    }
}

class Developer extends Employee {
    Developer(String name) {
        super(name);
    }
}

public class Test {
    public static void main(String[] args) {
        List<Developer> developers = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Human> humans = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        addDeveloper(developers, new Developer("Harry"));
        addDeveloper(employees, new Developer("Ron"));
        addDeveloper(humans, new Developer("Hermione"));
        addDeveloper(objects, new Developer("Neville"));

        System.out.println(developers.get(0).name);
        System.out.println(employees.get(0).name);
        System.out.println(humans.get(0).name);
        System.out.println(objects.get(0));
    }

    static void addDeveloper(List<? super Developer> list,
                             Developer developer) {
        list.add(developer);
    }
}
```

`addDeveloper` accepts all four lists because each list's element typecan safely hold a `Developer`.

The method can add a `Developer` to the list. However, when reading from `List<? super Developer>`, the compiler only guarantees that a retrieved element is an `Object` (apart from `null`). The list could be a `List<Object>`, so assigning a retrieved value directly to a `Developer` would not be safe.

------------------------------------------------------------------------

## 3. Why `? super Developer` allows adding

Consider this method:

``` java
static void addDeveloper(List<? super Developer> list) {
    list.add(new Developer("Harry")); // Compiles
}
```

The compiler knows that the list's element type is `Developer` or one of its supertypes. Every such list can store a `Developer`. By contrast, the following is not safe:

``` java
static void addEmployee(List<? super Developer> list) {
    // list.add(new Employee("Harry")); // Does not compile
}
```

The list could be a `List<Developer>`, and an ordinary `Employee` is not necessarily a `Developer`. The general rule is: with `List<? super T>`, you can safely add values
of type `T` and its subtypes.

