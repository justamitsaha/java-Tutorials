# Upper Bound Wildcards

Upper bound wildcards in Java are a feature of generics that allow greater flexibility when working with generic types. They are denoted by the <? extends T> syntax, where T is a specific type.

Upper bound wildcards are used when you want to specify that a parameterized type may be any subtype of a certain type. For instance, if you have a method that accepts a List of any type that is a subtype of Number, you can use an upper bound wildcard to indicate this:

Below method can accept a List<Integer>, List<Double> since these Classes extend java.lang.Number.

```java
public class Test{
    public static void main(String[] args) {
        add(1,7);
        //add(3.14f, 2.8f);


        List<Integer> list = List.of(1,2,3);
        List<Double> list2 = List.of(1d,2d,3d);
        print(list);
        print(list2);
        List<String> strings = List.of("I", "am", "Iron Man");
        // this will  not compile as Sting does not extend Numbers
        // processNumbers(strings);
        
    }

    public static <T extends Integer> void add(T a, T b ){
        System.out.println(a+b);
    }

    public static <T extends Number> void print(List<T> list){
        for (T t : list) {
            System.out.println(t);
        }
    }

    //Or we can write the same function like this but we won't have access to type hence we have to use for loop
    public static void print2(List<? extends Number> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
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
public class Test{
    public static void main(String[] args) {
        List<? extends Employee> list = new ArrayList<>();
        Employee emp = new Employee(22132, "harry");
        Developer dev = new Developer(22132, "harry");
        //❌ won't compile
        //list.add(emp);
        //list.add(dev);
        // The list could be a List<Developer>, List<SeniorDev>, or another subtype of Employee. Adding a specific subtype could violate type safety.
        // For example, if the list is a List<Developer>, adding a SeniorDev would be incorrect.


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
```
