# Generics Overview

Generics was added in Java 5 to provide compile-time type checking and removing risk of ClassCastException that was common while working with collection classes. The whole collection framework was re-written to use generics for type-safety. Let’s see how generics help us using collection classes safely.

```java
public class Test {
    public static void main(String[] args) {
        Integer i =5;
        List list = new ArrayList();
        list.add("abc");
        list.add(i);
        for (Object obj : list) {
            String str = (String) obj;
        }
    }
}
```

Above code compiles fine but throws ClassCastException at runtime because we are trying to cast Object in the list to String whereas one of the elements is of type Integer.

After Java 5, we use collection classes like below. At the time of list creation, we have specified that the type of elements in the list will be String. So if we try to add any other type of object in the list, the program will throw compile-time error

```java
       List<String> list = new ArrayList<>();

```

For example, we have a common Response class which will be returned to User for many scenario. Now if we want to send a response of Employee.

```java
public class Test {
    public static void main(String[] args) {
        Employee employee = new Employee(50000, 28);
        Response res = new Response();
        res.setResponse(employee);
        res.setCode("200");
    }
}

class Response {
    private Object response;
    private String code;
    public Object getResponse() {
        return response;
    }
    public void setResponse(Object response) {
        this.response = response;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
```

With generics we can do like this

```java
public class Test {
    public static void main(String[] args) {
        Employee employee = new Employee(50000, 28);
        Response<Employee> res  = new Response<>();
        res.setResponse(employee);
        res.setCode("200");
        System.out.println(res);
    }
}
class Response<T> {

    private T response;
    private String code;
    public T getResponse() {
        return response;
    }
    public void setResponse(T response) {
        this.response = response;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
```

Notice the use of GenericsType class in the main method. We don’t need to do type-casting and we can remove ClassCastException at runtime. If we don’t provide the type at the time of creation in the main method ;ike below, the compiler will produce a warning that **GenericsType is a raw type. References to generic type GenericsType<T> should be parameterized**. When we don’t provide the type, the type becomes Object and hence it can allow any data type like String and Integer. But, we should always try to avoid this because we will have to use type casting while working on raw type that can produce runtime errors.

```java
public class Test {
    public static void main(String[] args) {
        Employee employee = new Employee(50000, 28);
        Response res  = new Response<>();
        res.setResponse(employee);
        res.setCode("200");
        System.out.println(res);
    }
}
```

Java Generic Type Naming convention helps us understanding code easily and having a naming convention is one of the best practices of Java programming language. So generics also comes with its own naming conventions. Usually, type parameter names are single, uppercase letters to make it easily distinguishable from java variables. The most commonly used type parameter names are:

- E - Element (used extensively by the Java Collections Framework, for example ArrayList, Set etc.)

- K - Key (Used in Map)

- N - Number

- T - Type

- V - Value (Used in Map)

- S,U,V etc. - 2nd, 3rd, 4th types

Sometimes we don’t want the whole class to be parameterized, in that case, we can create java generics method. Since the constructor is a special kind of method, we can use generics type in constructors too. Here is a class showing an example of a java generic method.

```java
public class Test {

    // Generic method
    public <T> boolean isEqual(T a, T b) {
        return a.equals(b);
    }

    // Generic constructor
    public <T> Test(T value) {
        System.out.println("Constructor called with: " + value);
    }

    public static void main(String[] args) {
        // Generic constructor: accepts different types
        Test obj1 = new Test("Hello");
        Test obj2 = new Test(100);

        // Generic method: works with different types
        Test util = new Test("Utility");

        System.out.println(util.isEqual("Hello", "Hello")); // true
        System.out.println(util.isEqual(10, 20));           // false

        // Java can infer a common supertype for both arguments, such as Object hence this will compile and return false
        System.out.println(util.isEqual("Hello", 10));
    }
}
```
Above examples are unbound. In Java generics, unbound means a type parameter or wild card has no restrivtion on what type it can represent.eg.

```java
List<?> list;
```

or

```java
List<T> list;
```
 
Suppose we want to restrict the type of objects that can be used in the parameterized type, for example in a method that compares two objects and we want to make sure that the accepted objects are Comparables. To declare a bounded type parameter, list the type parameter’s name, followed by the extends keyword, followed by its upper bound, similar like below method.

```java
// A normal (non-generic) class with bounded generic methodspublic 
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
        // ❌ Compile-time error: Employee does not implement Comparable
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
```

The invocation of these methods is similar to unbounded method except that if we will try to use any class that is not Comparable, **it will throw compile-time error**. Bounded type parameters can be used with methods as well as classes and interfaces. Java Generics supports multiple bounds also, i.e <T extends A & B & C>. In this case, A can be an interface or class. If A is class then B and C should be an interface. We can’t have more than one class in multiple bounds. One example below where

- the getHigherPaid method of test class cam have arguments which extends Employee & Identifiable

- Logically Identifiable is not needed here and added just for the purpose of example

- But if we remove it it will not compile

```java
// Custom interface
interface Identifiable {
    String getId();
}

// Class implementing Comparable + Identifiable
class Employee implements Comparable<Employee>, Identifiable {
    private String id;
    private int salary;

    public Employee(String id, int salary) {
        this.id = id;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.salary, other.salary);
    }

    @Override
    public String toString() {
        return id + " : " + salary;
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

        System.out.println("Higher paid: " + higher);
    }
}

```

**🔑 Key Takeaways**

**Bounded type parameter (<T extends Comparable<T>>)**Restricts types so only those implementing Comparable are accepted.

**Compile-time safety**

compare(10, 20) ✅ allowed (Integer is Comparable).

compare(new Person(), new Person()) ❌ compile error if Person doesn’t implement Comparable.

**Multiple bounds (<T extends A & B & C>)**

Only **one class** allowed, must come first (Employee).

Interfaces can follow (Identifiable).

Ensures T is guaranteed to have all behaviors from the class + interfaces.
