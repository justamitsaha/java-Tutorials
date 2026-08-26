# Primitive Stream

There are 3 type of Primitive streams

1 **IntStream**: : Used for the primitive types int, short, byte, and char.

2 **LongStream** : : Used for the primitive type long

3 **DoubleStream** : Used for the primitive types double and float

Common primitive stream methods

- **OptionalDouble average()** : The arithmetic mean of the elements. Supported by all primitive Stream type

- **Stream boxed()** : A Stream where T is the wrapper class associated with the primitive value. Supported by all primitive Stream types.

- **OptionalInt max()** : The maximum element of the stream. Supported by integer primitive Stream type

- **OptionalLong max()** : The maximum element of the stream. Supported by long primitive Stream type

- **OptionalDouble max()** : The maximum element of the stream. Supported by double primitive Stream type

- **OptionalInt min()** : The minimum element of the stream. Supported by integer primitive Stream type

- **OptionalLong min()** : The minimum element of the stream. Supported by long primitive Stream type

- **OptionalDouble min()** : The minimum element of the stream. Supported by double primitive Stream type

- **IntStream range(int a, int b)** : Returns a primitive stream from a(inclusive) to b (exclusive)

- **LongStream range(long a, long b)** : Returns a primitive stream from a(inclusive) to b (exclusive)

- **IntStream rangeClosed(int a, int b)** : Returns a primitive stream from a(inclusive) to b (inclusive)

- **LongStream rangeClosed(long a, long b)**: Returns a primitive stream from a(inclusive) to b (inclusive)

```java
int sum() : Returns the sum of the elements in the stream
```

- **long sum()** : Returns the sum of the elements in the stream

- **double sum()** : Returns the sum of the elements in the stream

- **IntSummaryStatistics summaryStatistics() :** Returns an object containing numerous stream statistics such as the average, min, max, etc.

- **LongSummaryStatistics summaryStatistics()** : Returns an object containing numerous stream statistics such as the average, min, max, etc.

- **DoubleSummaryStatistics summaryStatistics() :** Returns an object containing numerous stream statistics such as the average, min, max, etc.

Create Primitive stream

var random = DoubleStream.generate(Math::random);

var fractions = DoubleStream.iterate(.5, d -> d / 2);

random.limit(3).forEach(System.out::println);

fractions.limit(3).forEach(System.out::println);

FYI Random class provides methods which generate Primitive streams.

Random random = new Random();

random.ints().limit(6).forEach(System.out::println);

Primitive Stream can help in reducing the line of code and complexity drastically.

IntStream count = IntStream.iterate(1, n -> n + 1).limit(5);

count.forEach(System.out::println); *// 12345*

We have to pass 6 as 2nd param is exclusive of last item

IntStream rangeClosed = IntStream.rangeClosed(1, 5);

rangeClosed.forEach(System.out::println); *// 12345*

Since we are using rangeClosed() we can pass 5
