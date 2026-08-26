# Stream Terminal Operations

1 **count() :** The count() method determines the number of elements in a finite stream. For an infinite stream, it never terminates

Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");

```java
System.out.println(s.count());
```

2 **min() and max() :** The min() and max() methods allow you to pass a custom comparator and find the smallest or largest value in a finite stream according to that sort order. Like the count() method, min() and max() hang on an infinite stream. It returns a false for empty stream

Optional<T> min(Comparator<? super T> comparator)

Optional<T> max(Comparator<? super T> comparator)

Stream<String> s = Stream.of("monkey", "donkey", "bonobo");

Optional<String> min = s.min((s1, s2) -> s1.length()-s2.length());

min.ifPresent(System.out::println);

3 **findAny() and findFirst() :** The findAny() and findFirst() methods return an element of the stream unless the stream is empty. If the stream is empty, they return an empty Optional. It can terminate an infinite stream

Stream<Double> randoms = Stream.generate(Math::random);

randoms.findAny().ifPresent(System.out::println);

Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);

oddNumbers.findFirst().ifPresent(System.out::println);

4 **allMatch(), anyMatch(), and noneMatch() :** The findAny() and findFirst() methods return an element of the stream unless the stream is empty. If the stream is empty, they return an empty Optional. It can terminate an infinite stream

anyMatch allMatch --> boolean = stream.anyMatch(Predicate <? super T> predicate)

var list = List.of("monkey", "2", "chimp");

Predicate<String> pred = x -> Character.isLetter(x.charAt(0));

```java
System.out.println(list.stream().anyMatch(pred));  // true
System.out.println(list.stream().allMatch(pred));  // false
System.out.println(list.stream().noneMatch(pred)); // false
```

5 **forEach() :** Iterates over each element in a Stream, Won't work for infinite stream

Stream<String> s1 = Stream.of("Monkey", "Gorilla", "Bonobo");

s1.forEach(System.out::print);

6 **reduce() :** The reduce() method combines a stream into a single object. It is a reduction, which means it processes all elements. Reduction stream operations allow us to produce one single result from a sequence of elements, by repeatedly applying a combining operation to the elements in the sequence. It has 3 signatures.

REDUCE example 1 ->> T reduce(T identity, BinaryOperator<T> accumulator)

```java
public class Test {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        Stream<String> newstream = Stream.of("w", "o", "l", "f");
        String word = stream
                .reduce(
                        "",
                        (x, c) -> x + c);
        String word2 = newstream.reduce(
                "",
                String::concat);
        System.out.println(word);
        System.out.println(word2);
    }
}

```

REDUCE example 2 -->> Optional<T> reduce(BinaryOperator<T> accumulator) When identity is not provided it returns an optional

```java
public class Test {
    public static void main(String[] args) {
        BinaryOperator<Integer> op = (a, b) -> a * b;
        Stream<Integer> empty = Stream.empty();
        empty
                .reduce(op)
                .ifPresent(System.out::println); // no output
        Stream<Integer> oneElement = Stream.of(3);
        oneElement
                .reduce(op)
                .ifPresent(System.out::println); // 3
        Stream<Integer> threeElements = Stream.of(3, 5, 6);
        threeElements
                .reduce(op)
                .ifPresent(System.out::println); //

    }
}
```

*90*

REDUCE example 3 -->> <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner) The third method signature is used when we are dealing with different types. It allows Java to create intermediate reductions and then combine them at the end

```java
public class Test {
    public static void main(String[] args) {
        Stream<String> stream1 = Stream.of("w", "o", "l", "f!");
        int length = stream1.reduce(
                0,
                (i, w) -> i + w.length(),
                Integer::sum);
        System.out.println(length);
    }
}
```

7 **collect**() : The collect() function is used to accumulate the elements of a stream into a collection or a single value It lets us get data out of streams and into another form

<R,A> R collect(Collector<? super T,A,R> collector);

```java
public class Test {
    public static void main(String[] args) {
        List<String> departmentList = new ArrayList<>();
        departmentList.add("Supply");
        departmentList.add("HR");
        departmentList.add("Sales");
        departmentList.add("Marketing");

        Set<String> newDepartmentList = departmentList.stream()
                .filter(word -> word.startsWith("S"))
                .collect(Collectors.toCollection(TreeSet::new));

        newDepartmentList.forEach(System.out::println);

        long totalCount = departmentList.stream()
                .filter(word -> word.startsWith("S"))
                .collect(Collectors.counting());
        System.out.println(totalCount);
    }
}
```

8 **collectingAndThen** ()The collectingAndThen() function is used to perform an additional operation on the result of the collect() function.

<R,A> R collectingAndThen(Collector<? super T,A,R> downstream, Function<R,RR> finisher);

- **downstream :**A Collector that describes how to accumulate elements into a result container.

- **finisher :**The function to be applied to the result of the collection operation.

```java
class Product {

```

String name;

Integer price;

```java
public Product(String name, Integer price) {
```

this.name = name;

this.price = price;

}

}

```java
public class Test {
public static void main(String[] args) {
```

List<Product> productList = Arrays.asList(new Product("Apple", 1200),

new Product("Samsung", 1000), new Product("Nokia", 600),

new Product("BlackBerry", 1000), new Product("Apple Pro Max", 1500),

new Product("Mi", 800), new Product("OnePlus", 1000));

String maxPriceProduct = productList.stream().collect(Collectors.collectingAndThen(

Collectors.maxBy(Comparator.comparing(product -> product.price)),

(productOptional -> productOptional.isPresent() ? productOptional.get().name : "None")));

```java
System.out.println(maxPriceProduct);
```

}

}

9 **groupingBy()** The groupingBy() function in Java Streams is used to group elements of a stream by a classification function.

groupingBy(Function<? super T, ? extends K> classifier);

The classification function used to group elements.

List<String> words = Arrays.asList("apple", "banana", "cherry", "blueberry", "avocado");

Map<Character, List<String>> groupedByFirstLetter = words.stream()

.collect(Collectors.groupingBy(word -> word.charAt(0)));

```java
System.out.println("Grouped by first letter: " + groupedByFirstLetter);
```

//Grouped by first letter: {a=[apple, avocado], b=[banana, blueberry], c=[cherry]}

10 partitioningBy() Function

The partitioningBy() function in Java Streams is used to partition elements into two groups based on a predicate.

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

Map<Boolean, List<Integer>> partitionedEvenOdd = numbers.stream()

.collect(Collectors.partitioningBy(num -> num % 2 == 0));

```java
System.out.println("Partitioned into even and odd: " + partitionedEvenOdd);
```

//Partitioned into even and odd: {false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8, 10]}
