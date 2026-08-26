# Method Reference

Method reference is used to refer to a method of functional interface. It is a compact and easy form of lambda expression. When we simply pass the parameters of Lambda expression to functional interface implementation, we can instead use Method reference instead of Lambda which looks much cleaner. You can pretend the compiler turns your method references into lambdas for you.

```java
TestClass obj = (text) -> System.out.println(text);
obj.testMethod("Dummy Text");
```

```java
TestClass obj= System.out::println;
obj.testMethod("Dummy Text");
```

There are four formats for usage of method references, depending on where we are using it.

- **CALLING STATIC METHODS :** Collection.sort(lst) becomes Collection::sort

Consumer<List<Integer>> methodRef = Collections::sort;

methodRef.accept(lst);

Consumer<List<Integer>> lambda = s-> Collections.sort(s);

lambda.accept(lst);

- CALLING INSTANCE METHODS ON OBJECT AND PASSING A PARAM: str.startsWith("a") becomes str::startsWith("a")

var str = "abc";

Predicate<String> methodRef = str::startsWith;

methodRef.test("a");

var str = "abc";

Predicate<String> lambda = s -> str.startsWith(s);

lambda.test("a");

- CALLING INSTANCE METHODS ON OBJECT : str.isEmpty()

Predicate<String> methodRef = String::isEmpty;

methodRef.test("Yo");

Predicate<String> lambda = s -> s.isEmpty();

lambda.test("Yo");

- CALLING CONSTRUCTORS

Supplier <List<String>> methodRef4 = ArrayList::new;

methodRef4.get();

Supplier<List<String>> lambda4= () -> new ArrayList();

lambda.get();
