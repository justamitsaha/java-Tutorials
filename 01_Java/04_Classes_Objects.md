# Classes and Objects

## Core Concepts

In Java, all executable logic and state must reside within a class. Java is an object-oriented language that models real-world concepts through classes and objects.

- **Class (Blueprint / Template):** A user-defined data type that defines the structure (fields) and behavior (methods) of an entity. It serves as a blueprint from which individual objects are created.
- **Object (Instance):** A concrete, runtime instance of a class allocated in memory with its own distinct state.

---

## Class vs Object Anatomy

| Aspect | Class | Object |
| :--- | :--- | :--- |
| **Nature** | Blueprint or template | Concrete instance of the blueprint |
| **Existence** | Logical construct defined in source code | Physical entity allocated in JVM Heap memory |
| **Memory Allocation** | Loaded once into the Metaspace / Method Area | Each `new` invocation creates a distinct heap allocation |
| **State** | Defines variable types and defaults | Holds unique values for its instance variables |

---

## Code Example

The following example defines a `Box` class template and instantiates a concrete `Box` object to compute its area:

```java
public class Test {
    public static void main(String[] args) {
        // Instantiate a Box object on the heap and assign its reference to b1
        Box b1 = new Box();
        
        // Assign values to instance variables
        b1.height = 6;
        b1.length = 5;
        b1.width = 4;
        
        // Invoke an instance method
        int calculatedArea = b1.area();
        System.out.println("Box Area: " + calculatedArea);
    }
}

class Box {
    // Instance variables (state)
    int height;
    int width;
    int length;

    // Instance method (behavior)
    int area() {
        return this.height * this.length * this.width;
    }
}
```

---

## Instance Variables and Encapsulation

- **Instance Variables:** Variables defined within a class body outside any method. Each object maintains its own independent copy of instance variables.
- **State Independence:** Modifying the fields of one object instance does not alter the values of other instances created from the same class.
