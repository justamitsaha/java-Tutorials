# Comparable Comparator

Java provides **Comparable** interface which should be implemented by any custom class if we want to use Arrays or Collections sorting methods. The Comparable interface has compareTo(T obj) method which is used by sorting methods, you can check any Wrapper, String or Date class to confirm this. We should override this method in such a way that it returns a negative integer, zero, or a positive integer if “this” object is less than, equal to, or greater than the object passed as an argument.

For eg we have an Employee object which we want to add to TreeSet. It will throw exception if we don’t implement Comparable.

```java
public class Test {
    public static void main(String[] args) {
        TreeSet<Employee> set = new TreeSet<Employee>();
        set.add(new Employee(50000, 28));
        set.add(new Employee(2333, 33));
        set.add(new Employee(92383, 33));
        set.add(new Employee(7623, 43));
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

Exception in thread "main" java.lang.ClassCastException: class testing.Employee cannot be cast to class java.lang.Comparable (testing.Employee is in unnamed module of loader 'app'; java.lang.Comparable is in module java.base of loader 'bootstrap')

at java.base/java.util.TreeMap.compare(TreeMap.java:1604)

at java.base/java.util.TreeMap.addEntryToEmptyMap(TreeMap.java:811)

at java.base/java.util.TreeMap.put(TreeMap.java:820)

at java.base/java.util.TreeMap.put(TreeMap.java:569)

at java.base/java.util.TreeSet.add(TreeSet.java:259)

at testing.Test.main(Test.java:8)

To Resolve this, we make Employee class to implement Comparable interface and provide implementation for compareTo method

```java
public class Test {
    public static void main(String[] args) {
        TreeSet<Employee> set = new TreeSet<Employee>();
        set.add(new Employee(50000, 28));
        set.add(new Employee(23533, 33));
        set.add(new Employee(92383, 33));
        set.add(new Employee(7623, 43));
        System.out.println(set);
    }

}

class Employee implements Comparable<Employee> {
    int salary;
    int age;

    public Employee(int salary, int age) {
        super();
        this.salary = salary;
        this.age = age;
    }

    @Override
    public int compareTo(Employee arg0) {
        return this.age - arg0.age;
    }

    @Override
    public String toString() {
        return "Employee [salary=" + salary + ", age=" + age + "]";
    }
}

```

Comparable.compareTo(Object o) method implementation can provide default sorting and we can’t change it dynamically. Whereas with **Comparator**, we can define multiple methods with different ways of sorting and then chose the sorting method based on our requirements.

```java
public class Test {
    public static void main(String[] args) {
        Set<Employee> set1 = new TreeSet<>(new SalaryComparator());
        set1.add(new Employee(50000, 28));
        set1.add(new Employee(23533, 33));
        set1.add(new Employee(92383, 33));
        set1.add(new Employee(7623, 43));
        System.out.println(set1);
    }
}

class SalaryComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee arg0, Employee arg1) {
        return arg0.salary - arg1.salary;
    }

}

class Employee implements Comparable<Employee> {
    int salary;
    int age;

    public Employee(int salary, int age) {
        super();
        this.salary = salary;
        this.age = age;
    }

    @Override
    public int compareTo(Employee arg0) {
        return this.age - arg0.age;
    }

    @Override
    public String toString() {
        return "Employee [salary=" + salary + ", age=" + age + "]";
    }
}

```

| Comparable make the class implement Comparable interface and provide implementation for compareTo method. | Comparator create a class which implements Comparator class with implementation for compare method. This class is passed as reference |
| --- | --- |
| Its fixed and comparison can’t be changed | Since we pass a reference for Comparator we can create multiple Comparator based on different logic |
|  |  |
