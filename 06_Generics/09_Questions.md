# Java Generics — Interview & Architecture Questions

A collection of deep-dive questions on Java Generics covering compiler mechanics, type erasure, bytecode bridge methods, variance, PECS, and advanced design patterns.

---

### Question 1: Generics Invariance vs Array Covariance
Given the following code snippet:

```java
public class Test {
    public static void main(String[] args) {
        // Snippet A
        Integer[] intArray = new Integer[2];
        Number[] numArray = intArray;
        numArray[0] = 3.14;

        // Snippet B
        // java.util.List<Integer> intList = new java.util.ArrayList<>();
        // java.util.List<Number> numList = intList;
    }
}
```

1. What happens at compile time and runtime for **Snippet A**?
2. What happens at compile time for **Snippet B**?
3. Why did the Java designers make arrays covariant while making generics invariant?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
1. **Snippet A**: Compiles without error, but throws `java.lang.ArrayStoreException` at runtime on line `numArray[0] = 3.14`.
2. **Snippet B**: Fails at compile time with `incompatible types: List<Integer> cannot be converted to List<Number>`.
3. Arrays are **reified** (they carry full runtime type metadata). When Java 1.0 was released, generics did not exist; array covariance was required to implement generic utility methods like `Arrays.sort(Object[])`. At runtime, the JVM checks every array store operation and throws `ArrayStoreException` if types mismatch. Generics, introduced in Java 5 via **type erasure**, enforce strict **invariance** at compile time to eliminate runtime casting failures entirely (heap pollution).

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Array Covariance (runtime failure)
        Number[] numArray = new Integer[2];
        try {
            numArray[0] = 3.14; // Throws ArrayStoreException
        } catch (ArrayStoreException e) {
            System.out.println("ArrayStoreException: " + e.getMessage());
        }

        // Generics Invariance (compile-time safety)
        List<Integer> intList = new ArrayList<>();
        // List<Number> numList = intList; // ❌ Compile error: incompatible types
    }
}
```
</details>

---

### Question 2: Compiler Bridge Methods
Consider the following generic class and subclass:

```java
class Node<T> {
    public void setData(T data) {
        System.out.println("Node.setData: " + data);
    }
}

class MyNode extends Node<Integer> {
    @Override
    public void setData(Integer data) {
        System.out.println("MyNode.setData: " + data);
    }
}
```

After type erasure, `Node.setData(T)` becomes `Node.setData(Object)`. However, `MyNode` declares `setData(Integer)`.
1. How does the Java compiler preserve polymorphism so that `Node node = new MyNode(); node.setData(...)` executes the subclass method?
2. What synthetic bytecode artifact is generated, and how can it cause a `ClassCastException` on a line with no visible cast?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
The compiler generates a synthetic **Bridge Method** in `MyNode`: `public void setData(Object data)`.

**Explanation:**
Because `MyNode.setData(Integer)` does not match the erased signature `Node.setData(Object)`, `MyNode` would technically overload rather than override the method. To preserve polymorphism, the compiler synthesizes a bridge method in `MyNode`:

```bytecode
// Decompiled bytecode of MyNode:
public void setData(java.lang.Object data) {
    checkcast java/lang/Integer
    invokevirtual setData:(Ljava/lang/Integer;)V
}
```

If raw types are used to bypass compile-time checks, passing an incompatible object causes the synthetic bridge method to throw a `ClassCastException`:

```java
public class Test {
    public static void main(String[] args) {
        Node node = new MyNode(); // Raw type
        try {
            node.setData("Hello"); // Invokes synthetic bridge method: setData(Object)
        } catch (ClassCastException e) {
            System.out.println("Caught synthetic bridge cast exception: " + e.getMessage());
        }
    }
}

class Node<T> {
    public void setData(T data) {
        System.out.println("Node: " + data);
    }
}

class MyNode extends Node<Integer> {
    @Override
    public void setData(Integer data) {
        System.out.println("MyNode: " + data);
    }
}
```
</details>

---

### Question 3: Wildcard Capture & Helper Methods
Why does the following method fail to compile, and how do you resolve it?

```java
public static void swapFirstTwo(java.util.List<?> list) {
    if (list.size() >= 2) {
        // list.set(0, list.get(1)); // ❌ Compile error
    }
}
```

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
The compiler captures `?` as an anonymous internal type variable (e.g., `capture of ?`). When evaluating `list.set(0, list.get(1))`, `list.get(1)` produces `capture#1 of ?`, but `list.set(...)` expects `capture#2 of ?`. The compiler cannot prove that the type read from the list is identical to the type accepted by the list at runtime.

**Solution:**
Use a private generic helper method with a formal type parameter `<T>` that "captures" the wildcard:

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        swapFirstTwo(list);
        System.out.println("Swapped: " + list); // [B, B, C]
    }

    public static void swapFirstTwo(List<?> list) {
        if (list.size() >= 2) {
            swapHelper(list);
        }
    }

    // Helper captures the wildcard as explicit type parameter T
    private static <T> void swapHelper(List<T> list) {
        list.set(0, list.get(1)); // Type-safe: both get and set use T
    }
}
```
</details>

---

### Question 4: Recursive Type Bounds & `Comparable<? super T>`
In the standard Java library, `Collections.max` is declared as:

$$\text{public static } <T \text{ extends Comparable<? super T>> } T \text{ max(Collection<? extends T> coll)}$$

Why does it use `Comparable<? super T>` instead of the simpler `<T extends Comparable<T>>`?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
`<T extends Comparable<T>>` breaks inheritance when a subclass does not directly implement `Comparable<Subclass>`, but instead inherits `Comparable<Superclass>` from its parent.

**Explanation:**
Suppose `Animal` implements `Comparable<Animal>`. If `Dog` extends `Animal`, `Dog` implements `Comparable<Animal>`, **not** `Comparable<Dog>`.
Under `<T extends Comparable<T>>`, passing a `List<Dog>` causes a compilation failure because `Dog` does not implement `Comparable<Dog>`.
Using `Comparable<? super T>` allows `Dog` (`T`) to compare using its ancestor's comparator (`Comparable<Animal>`).

```java
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Dog> dogs = List.of(new Dog(15), new Dog(30));

        // Works because Dog inherits Comparable<Animal>, which satisfies Comparable<? super Dog>
        Dog biggest = max(dogs);
        System.out.println("Max dog weight: " + biggest.weight);
    }

    public static <T extends Comparable<? super T>> T max(List<T> list) {
        T max = list.get(0);
        for (T item : list) {
            if (item.compareTo(max) > 0) max = item;
        }
        return max;
    }
}

class Animal implements Comparable<Animal> {
    int weight;
    public Animal(int weight) { this.weight = weight; }

    @Override
    public int compareTo(Animal other) {
        return Integer.compare(this.weight, other.weight);
    }
}

class Dog extends Animal {
    public Dog(int weight) { super(weight); }
}
```
</details>

---

### Question 5: Heap Pollution & `@SafeVarargs`
1. What is **heap pollution** in Java Generics?
2. Why does combining varargs with generic types (`public static <T> void doSomething(T... args)`) cause compiler warnings?
3. What are the strict rules for using the `@SafeVarargs` annotation?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
1. **Heap pollution** occurs when a variable of a parameterized type references an object that is not of that parameterized type. It leads to unexpected runtime `ClassCastException`s on code lines without visible casts.
2. In Java, varargs `T... args` is syntactic sugar for array allocation (`new T[]`). Because generic arrays cannot be created safely due to type erasure, the compiler allocates an `Object[]`. If that array reference is exposed or modified, incompatible types can be placed into it.
3. **`@SafeVarargs` Rules**:
   - Can only be applied to methods that cannot be overridden: `static` methods, `final` methods, and (since Java 9) `private` methods.
   - The method **must not write** to the varargs array (must treat it as read-only).
   - The method **must not expose or leak** the varargs array reference to untrusted code.

```java
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            pollute(List.of("One"), List.of("Two"));
        } catch (ClassCastException e) {
            System.out.println("Heap pollution error: " + e.getMessage());
        }
    }

    // Unsafe varargs: leaks/mutates the underlying array
    static void pollute(List<String>... stringLists) {
        Object[] array = stringLists; // Array covariance
        array[0] = List.of(42);       // Heap pollution: stringLists[0] now contains List<Integer>
        String str = stringLists[0].get(0); // 💥 ClassCastException: Integer cannot be cast to String
    }
}
```
</details>

---

### Question 6: Why `new T[n]` Fails & Generic Array Workarounds
1. Why does the compiler reject `T[] array = new T[10];` and `List<String>[] arr = new List<String>[10];`?
2. What are the two standard approaches to implementing generic backing arrays (e.g., inside a custom `ArrayList<T>`)?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
1. **Reification vs Erasure**: Java arrays verify element types at runtime during store operations (`checkcast`). Generics exist only at compile time and are erased to their bounds (`Object`) at runtime. If generic array creation were allowed, `new List<String>[10]` would become `new List[10]` at runtime, allowing a `List<Integer>` to be stored via an `Object[]` alias and violating array type safety without an `ArrayStoreException`.
2. **The Two Standard Workarounds**:
   - **Internal Cast (`Object[]` cast to `T[]`)**: Used by `java.util.ArrayList`. The backing array is stored as `Object[]` and cast to `(T)` upon retrieval.
   - **Reflection Type Token (`Array.newInstance`)**: Preserves the true runtime array component type.

```java
import java.lang.reflect.Array;

public class Test {
    public static void main(String[] args) {
        // Approach A: ArrayList-style internal cast
        CustomList<String> list = new CustomList<>(10);
        list.set(0, "Java");
        System.out.println("Item: " + list.get(0));

        // Approach B: Reflection preserving reified array type
        String[] strings = createArray(String.class, 5);
        strings[0] = "Generics";
        System.out.println("Reflected array: " + strings[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> clazz, int size) {
        return (T[]) Array.newInstance(clazz, size);
    }
}

class CustomList<T> {
    private Object[] elements;

    public CustomList(int capacity) {
        elements = new Object[capacity];
    }

    public void set(int index, T item) { elements[index] = item; }

    @SuppressWarnings("unchecked")
    public T get(int index) { return (T) elements[index]; }
}
```
</details>

---

### Question 7: Super Type Tokens (`TypeReference`)
Because of type erasure, `List<String>.class` does not compile, and `new ArrayList<String>().getClass()` yields `ArrayList.class`.
How do libraries like Jackson (`TypeReference<T>`) and Guava (`TypeToken<T>`) recover full generic type information (e.g., deserializing JSON into `List<String>`) at runtime?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
Through **Super Type Tokens**. While generic type arguments on *instances* are erased, generic type arguments declared on a class declaration or subclass definition are preserved in the classfile metadata (`Signature` attribute) and accessible via reflection.

**Explanation:**
By creating an **anonymous inner subclass** (`new TypeReference<List<String>>() {}`), the class inherits from `TypeReference<List<String>>`. The constructor can then invoke `getClass().getGenericSuperclass()`, which returns a `ParameterizedType` containing the actual type argument (`java.util.List<java.lang.String>`).

```java
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Anonymous subclass captures generic parameter at runtime
        TypeReference<List<String>> token = new TypeReference<>() {};
        System.out.println("Captured Type: " + token.getType());
    }
}

abstract class TypeReference<T> {
    private final Type type;

    protected TypeReference() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType parameterized) {
            this.type = parameterized.getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Subclass must specify a generic type argument");
        }
    }

    public Type getType() { return type; }
}
```
</details>

---

### Question 8: Multiple Bounds Ordering Rules
Which of the following method signatures are valid, and why?

```java
// Option A
public static <T extends Number & Comparable<T>> void processA(T val) {}

// Option B
public static <T extends Comparable<T> & Number> void processB(T val) {}

// Option C
public static <T extends Number & java.io.Serializable & Cloneable> void processC(T val) {}

// Option D
public static <T extends Number & Integer> void processD(T val) {}
```

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
- **Option A**: **Valid**.
- **Option B**: **Invalid (Compile Error)**.
- **Option C**: **Valid**.
- **Option D**: **Invalid (Compile Error)**.

**Rules:**
1. A type parameter can have multiple bounds using `&`, but **at most one class** can be specified.
2. If a class is present in the bounds list, it **must be the first bound** (Option B is invalid because interface `Comparable` precedes class `Number`).
3. Option D is invalid because it specifies multiple classes (`Number` and `Integer`). In Java, multiple inheritance of classes is prohibited.

```java
public class Test {
    public static <T extends Number & Comparable<T>> void validMethod(T val) {
        System.out.println("Double: " + val.doubleValue());
    }

    public static void main(String[] args) {
        validMethod(100); // Integer extends Number and implements Comparable<Integer>
    }
}
```
</details>

---

### Question 9: Why `<T super Bound>` is Prohibited
Why does the compiler permit `List<? super Integer>`, but rejects `<T super Integer>` as a formal type parameter declaration?

```java
// Valid:
public static void add(java.util.List<? super Integer> list) {}

// ❌ COMPILE ERROR:
// public static <T super Integer> void test(T item) {}
```

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
A lower bound restricts `T` to `Integer` or any superclass of `Integer` (`Number`, `Object`).
If `<T super Integer>` were allowed, what methods could you safely invoke on `item` inside the method body?
Because `item` could be an arbitrary `Object`, the compiler would only be able to guarantee methods from `java.lang.Object`.
Declaring `<T super Integer>` provides no practical typing constraints on `T` inside the method body—it would behave identically to an unbounded type parameter `<T>`, while adding confusing syntax.
Lower bounds are only meaningful on **wildcards (`? super T`)** to establish use-site contravariance for write-safe collections (Consumers).
</details>

---

### Question 10: Self-Bounding Generics & Fluent Builder Inheritance
How do you implement a builder hierarchy in Java where a subclass builder inherits methods from a parent builder while maintaining fluent method chaining without broken return types?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
Use **Self-Bounding Generics** (also known as the Curiously Recurring Template Pattern / CRTP):
`abstract class PersonBuilder<SELF extends PersonBuilder<SELF>>`

**Explanation:**
If `PersonBuilder.withName(String)` simply returned `PersonBuilder`, calling it on an `EmployeeBuilder` instance would downcast the return type to `PersonBuilder`, breaking the chain and preventing calls to `withSalary(int)`.
By parameterizing the builder with `SELF`, parent methods return `self()`, which resolves to the subclass builder type.

```java
public class Test {
    public static void main(String[] args) {
        Employee emp = new EmployeeBuilder()
                .withName("Alice")   // Returns EmployeeBuilder, not PersonBuilder!
                .withSalary(120000)  // Chaining remains intact
                .build();

        System.out.println("Built: " + emp.name + ", " + emp.salary);
    }
}

class Employee {
    String name;
    int salary;
    Employee(String name, int salary) { this.name = name; this.salary = salary; }
}

abstract class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    String name;

    public SELF withName(String name) {
        this.name = name;
        return self();
    }

    protected abstract SELF self();
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    int salary;

    public EmployeeBuilder withSalary(int salary) {
        this.salary = salary;
        return this;
    }

    @Override
    protected EmployeeBuilder self() { return this; }

    public Employee build() { return new Employee(name, salary); }
}
```
</details>

---

### Question 11: Generics and Exceptions (`Throwable`)
1. Why does the compiler reject `class MyException<T> extends Exception {}`?
2. How does type erasure enable the "Sneaky Throws" pattern using `<T extends Throwable>`?

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
1. **Generic Exception Restriction**: Exception handling is a runtime mechanism managed by the JVM's exception table (`catch (E ex)`). If generic exceptions were allowed, `catch (MyException<String> e)` and `catch (MyException<Integer> e)` would erase to the identical runtime type `catch (MyException e)`, making type-specific catching impossible.
2. **Sneaky Throws Pattern**: While a class cannot extend `Throwable`, a generic method can declare a type variable in its `throws` clause (`<T extends Throwable> void sneaky(Throwable t) throws T`). When casting `(T) t`, the compiler erases `(T)` to `(Throwable)`. Because the compiler believes `T` is an unchecked exception (if `T` infers to `RuntimeException`), it bypasses compile-time checked exception validation while throwing the checked exception at runtime.

```java
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            // Throws checked IOException without declaring 'throws IOException' or try/catch!
            sneakyThrow(new IOException("Simulated disk error"));
        } catch (Exception e) {
            System.out.println("Caught sneakily thrown exception: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void sneakyThrow(Throwable t) throws T {
        throw (T) t; // Erased to: throw (Throwable) t;
    }
}
```
</details>

---

### Question 12: PECS in Real-World API Signatures
Consider `java.util.Collections.copy`:

```java
public static <T> void copy(List<? super T> dest, List<? extends T> src)
```

1. Explain how **PECS** applies here.
2. Given the hierarchy: `Object <- Number <- Integer`. Will the following calls compile?
   - `Collections.copy(new ArrayList<Number>(), new ArrayList<Integer>());`
   - `Collections.copy(new ArrayList<Integer>(), new ArrayList<Number>());`

<details>
<summary><b>Answer & Explanation</b></summary>

**Answer:**
1. **PECS (Producer Extends, Consumer Super)**:
   - `src` is a **Producer**: the method only reads elements from `src`. Using `? extends T` allows passing any collection of `T` or any subclass of `T`.
   - `dest` is a **Consumer**: the method only writes elements into `dest`. Using `? super T` allows passing any collection capable of storing `T` (i.e., `T` or any superclass of `T`).
2. **Compilation Results**:
   - `copy(List<Number> dest, List<Integer> src)`: **Compiles successfully**. `T` is inferred as `Number` (or `Integer`), where `Integer extends Number` and `Number super Integer`.
   - `copy(List<Integer> dest, List<Number> src)`: **Fails to compile**. `dest` cannot consume `Number` because `Integer` is a subtype, not a supertype of `Number`.

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Number> numDest = new ArrayList<>(List.of(0, 0));
        List<Integer> intSrc = List.of(10, 20);

        // Valid: Producer (Integer) into Consumer (Number)
        copy(numDest, intSrc);
        System.out.println("Destination: " + numDest);

        // Invalid:
        // List<Integer> intDest = new ArrayList<>();
        // List<Number> numSrc = List.of(1.5, 2.5);
        // copy(intDest, numSrc); // ❌ Compile error
    }

    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i = 0; i < src.size(); i++) {
            dest.set(i, src.get(i));
        }
    }
}
```
</details>
