# Immutable class and Record

Immutable Objects are those whose state can’t be changed once it is created. Mutable is opposite of this. In Java immutable object are created by keeping the member variables as private final only keeping setter and not keeping any getters. We can mark the class as final also which will prevent any child class from extending it and set the values using constructors

```java
class Box implements Cloneable {
    private final int height, breath, length;
    private final String colour;

    Box(int height, int breath, int length, String colour) {
        this.height = height;
        this.breath = breath;
        this.length = length;
        this.colour = colour;
    }
}

```

Java 16 gave us an alternative for this by using record class which does the same thing with less boiler plate code

```java
record Employee(String employeeId, int salary) {

}

```

Record can have static variables and constructors

```java
record Employee(String employeeId, int salary) {

    public static String company = "ABC";

    Employee(String employeeId, int salary) {
        this.employeeId = employeeId;
        this.salary = salary;
    }
}

```

or

```java
record Employee(String employeeId, int salary) {

    public static String company = "ABC";

    Employee {
    }
}

```

Record can have static variables and method, instance methods instance variables are in definition. They are final by default. They can implement interface. But they can’t extend any class as all record class by default extend java.lang.Record
