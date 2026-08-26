# Unbounded Wildcards

Unbounded wildcards, denoted by the question mark (?), allow flexibility by accepting any type. They are particularly useful when the code doesn't rely on the specific type of objects in a generic class or method.

List<?>

When the code is agnostic about the type: If the code doesn't depend on the specific type of objects in a generic class or method, unbounded wildcards can be used to provide flexibility.

When working with collections of unknown types: When dealing with collections where the type of elements is unknown or irrelevant, unbounded wildcards can be used to ensure compatibility

You use <?> when your code does **not depend** on the actual type parameter.

You want to **read** from a collection (but not add arbitrary elements).

You just need to **treat values generically** (e.g., print them, count them).

```java
public static void printList(List<?> list) {
for (Object element : list) {  // all elements are treated as Object
System.out.println(element);
```

}

}

```java
public static void main(String[] args) {
```

List<String> strings = List.of("A", "B", "C");

List<Integer> numbers = List.of(1, 2, 3);

printList(strings);

printList(numbers);

}

```java
public static void addElement(List<?> list) {
```

// list.add("Hello");   ❌ Compile error

}

Why? Because <?> means "unknown type".

It could be List<String>, List<Integer>, or List<Box>.

Java prevents adding because it can’t guarantee type safety.

The only thing you can safely add is null.
