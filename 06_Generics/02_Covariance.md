# Covariance & Invariance

Covariance and invariance describe how subtyping between more complex types relates to subtyping between their component types.

---

## 1. Method Return Type Covariance (OOP)

In method overriding, Java allows the subclass method to return a more specific subtype than the superclass method:

```java
public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();
        Animal result = animal.makeSound(); // Invokes Dog.makeSound()
        System.out.println("Returned type: " + result.getClass().getSimpleName()); // Dog
    }
}

class Animal {
    Animal makeSound() {
        System.out.println("Some animal sound");
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    Dog makeSound() { // Covariant return: Dog is a subtype of Animal
        System.out.println("Woof");
        return new Dog();
    }
}
```

---

## 2. Generics Invariance vs Array Covariance

In Java, **arrays are covariant**, but **generics are invariant**:

- **Array Covariance**: `Integer[]` is a subtype of `Number[]`.
  - Flaw: Compiles, but throws `ArrayStoreException` at runtime if an incompatible object is stored.
- **Generics Invariance**: `List<Integer>` is **NOT** a subtype of `List<Number>`.
  - Benefit: Caught at compile time. Prevents corrupted collections (heap pollution).

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 1. Array Covariance: Fails at runtime
        Number[] numArray = new Integer[3];
        Object[] objArray = numArray; // Allowed because arrays are covariant
        try {
            objArray[0] = "A String"; // Compiles, but throws ArrayStoreException at runtime!
        } catch (ArrayStoreException e) {
            System.out.println("Caught expected ArrayStoreException: " + e.getMessage());
        }

        // 2. Generics Invariance: Fails safely at compile time
        List<String> strList = new ArrayList<>();
        // List<Object> objList = strList; // ❌ Compile error: incompatible types!

        // Why invariance is required:
        // If objList = strList were allowed:
        // objList.add(100); // 100 is an Integer (valid for List<Object>)
        // String s = strList.get(0); // ClassCastException at runtime!
    }
}
```

---

## 3. Achieving Subtyping in Generics with Wildcards

**IMP** Because `List<Integer>` cannot be assigned to `List<Number>`, a method taking `List<Number>` cannot accept `List<Integer>`. To allow subtyping at call sites, Java provides wildcards:

- **Covariance (read-only)**: `List<? extends Number>` accepts `List<Integer>`, `List<Double>`, etc.
- **Contravariance (write-safe)**: `List<? super Integer>` accepts `List<Integer>`, `List<Number>`, `List<Object>`.
