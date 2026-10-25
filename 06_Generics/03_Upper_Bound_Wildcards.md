# Upper Bound Wildcards

Upper bound wildcards in Java are a feature of generics that allow greater flexibility when working with generic types. They are denoted by the <? extends T> syntax, where T is a specific type.

Upper bound wildcards are used when you want to specify that a parameterized type may be any subtype of a certain type. For instance, if you have a method that accepts a List of any type that is a subtype of Number, you can use an upper bound wildcard to indicate this:

Below method can accept a List<Integer>, List<Double> since these Classes extend java.lang.Number.

```java
public class Test {
    public static void main(String[] args) {
        List<Integer> integers = List.of(1, 2, 3);
        processNumbers(integers);
        List<Float> floats = List.of(1.1f, 2f, 4.232f);
        processNumbers(floats);
        List<String> strings = List.of("I", "am", "Iron Man");
        // processNumbers(strings);  this will  not compile as Sting does not extend Numbers

    }

    /*
    Number is an abstract class which is extended by Integer, Float, Double
     */
    public static void processNumbers(List<? extends Number> numbers) {
        numbers.forEach(System.out::println);
    }
}

```

Another example using custom class

```java
class Human {
}

class Employee extends Human {
}

class Developer extends Employee {
}

class SeniorDev extends Developer {
}

public class Test {
    public static void main(String[] args) {
        List<Developer> list = new ArrayList<>();
        print(list);

        List<SeniorDev> seniorDevs = new ArrayList<>();
        print(seniorDevs);

        List<Human> humans = new ArrayList<>();
        //print(humans); will not compile as humans don't extend Employee

    }

    static void print(List<? extends Employee> list) {
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }
}

```

When using upper bound wildcards, you can only read from the collection, not write to it. This is because the compiler cannot guarantee the type safety of adding elements to the collection.

So, when we do below it will not compile. So, to maintain type safety, Java does not allow adding elements to a list defined with an upper bounded wildcard.  Reason for this is ? extends Employee is a widening operation so the class getting allowed can have additional information. Lets see with an example. When we say ? extends Employee that means all sub class of Employee are allowed.

- Developer extends employee

- Manager also extends employee

But both can have different access. Hence to prevent ambiguity we only allow read operations and not write operations like adding to list. As by adding both developers and managers to List we can do incorrect operations like manangers trying to push code and developers giving change approvals

```java
static void addToList(List<? extends Employee> list) {
    // These will not compile because we don't know the exact type of the list.
    // list.add(new Developer());
    // list.add(new SeniorDev());

    // The list could be a List<Developer>, List<SeniorDev>,
    // or another subtype of Employee.
    // Adding a specific subtype could violate type safety.
    // For example, if the list is a List<Developer>,
    // adding a SeniorDev would be incorrect.

    for (Employee employee : list) {
        System.out.println(employee);
    }
}

```
