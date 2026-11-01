import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

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




