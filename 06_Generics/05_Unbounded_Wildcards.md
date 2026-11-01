# Unbounded Wildcards (`<?>`) & PECS

An unbounded wildcard specifies an unknown type.

$$\text{Syntax: } <?>$$

---

## 1. When to Use `List<?>`

Use `List<?>` when:
1. The method only uses operations provided by `java.lang.Object` (e.g., printing elements).
2. The method depends solely on the container itself, not the element type (e.g., `list.size()`, `list.clear()`).

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(List.of("A", "B", "C"));
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3));

        // Accepts any List type
        printList(strings);
        printList(numbers);

        // ❌ List<Object> is invariant: does NOT accept List<String>
        // printObjects(strings); // Compile error!
        printObjects(List.of(new Object(), new Object())); // Only accepts List<Object>

        // Write restriction:
        List<?> wildcardList = strings;
        // wildcardList.add("D"); // ❌ Compile error: cannot write to List<?>
        wildcardList.add(null);   // ✅ Only literal null is allowed
    }

    // Unbounded wildcard: accepts any List
    public static void printList(List<?> list) {
        for (Object item : list) { // Safe to read as Object
            System.out.println(item);
        }
    }

    // Invariant: strictly accepts List<Object>
    public static void printObjects(List<Object> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
}
```

---

## 2. Comparing `List<?>`, `List<Object>`, and `List<T>`

| Type | Acceptance | Read Guarantee | Write Allowed |
| :--- | :--- | :--- | :--- |
| **`List<?>`** | Any `List<X>` | `Object` | Only `null` |
| **`List<Object>`** | Only `List<Object>` | `Object` | Any `Object` |
| **`List<T>`** | Any specific type `T` | Type `T` | Type `T` |

---

## 3. Wildcard Capture & Helper Methods

When the compiler encounters a wildcard, it assigns an internal placeholder known as a **capture of `?`**. Because the compiler cannot verify that two captured types match, you cannot write a read value back into an unbounded wildcard collection:

```java
public static void swap(List<?> list, int i, int j) {
    // ❌ Compile error: capture of ? cannot be converted to capture of ?
    // list.set(i, list.get(j)); 
}
```

### The Solution: Private Generic Helper Method
Delegate to a private method with a formal type parameter `<T>` that "captures" the wildcard:

```java
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));
        System.out.println("Before swap: " + names);

        swap(names, 0, 2);
        System.out.println("After swap:  " + names);
    }

    // Public API uses clean wildcard
    public static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j); // Wildcard captured as type T
    }

    // Helper captures the type parameter T
    private static <T> void swapHelper(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
```

---

## 4. The PECS Principle Summary

$$\boxed{\textbf{P}\text{roducer } \textbf{E}\text{xtends, } \textbf{C}\text{onsumer } \textbf{S}\text{uper}}$$

- **Producer (`? extends T`)**: Use when your method only **reads** data from the collection.
- **Consumer (`? super T`)**: Use when your method only **writes** data into the collection.
- **Both**: If you need to **both read from and write to** the collection, use an exact type parameter (`<T>`), not a wildcard.
