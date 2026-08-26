# Optional Primitives

We have seen Optional can be used for methods which may or may not return value.

var stream = IntStream.rangeClosed(1, 10);

OptionalDouble optional = stream.average();

The OptionalDouble is different from Optional.It is used for Primitive streams. For Wrapper calls we use Optional<Double> and for Primitive class we user OptionalDouble. Working with the primitive optional class looks similar to working with the Optional class itself.The only noticeable difference is that we called getAsDouble() rather than get()

var stream = IntStream.rangeClosed(1, 10);

OptionalDouble optional = stream.average();

optional.ifPresent(System.out::println); *// 5.5*

```java
System.out.println(optional.getAsDouble()); // 5.5

```

var stream2 = IntStream.of();

OptionalDouble optional1 = stream2.average();

```java
System.out.println(optional1.orElseGet(() -> Double.NaN));

```

What will happen below?

var cats = new ArrayList<String>();

cats.add("Annie");

cats.add("Ripley");

var stream = cats.stream();

cats.add("KC");

```java
System.out.println(stream.count());
```

We can add values to stream after creation unless its terminated
