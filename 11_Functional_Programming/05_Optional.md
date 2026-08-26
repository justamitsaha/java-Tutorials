# Optional

Java introduced a new class Optional in jdk8. It is a public final class and used to deal with NullPointerException in Java applications. It provides methods which are used to check the presence of value for a particular variable.

Suppose we have an average method which takes an array of integers and calculates average for all the marks scored by a student. Now suppose a student was sick and did not take the exam, so input array length will be zero and so will be the average. This is not correct as average should not be applicable in this scenario. This we can handle  using optional

```java
public static Optional<Double> average(int... scores) {
if (scores.length == 0)
```

return Optional.empty();*// Sets empty optional*

double sum = 0;

for (int score : scores)

sum += score;

return Optional.of( sum / scores.length); *// Sets value using of Method*

}

If no scores are passed then the method will return an empty optional, otherwise it will return the average wrapped in optional.

```java
System.out.println(average(90, 100)); // Optional[95.0]
System.out.println(average()); // Optional.empty

```

Instead of using of() and empty() separately we can use offNullable

```java
public static void main(String[] args) {
System.out.println(getMessage(1));
System.out.println(getMessage(2));

```

}

```java
public static Optional<String> getMessage(int i){
```

String message = null;

if(i%2 ==0)

message ="Hello";

return Optional.ofNullable(message);

}

As you can see above the Optional returns the result wrapped in Optional like Optional[95.0] instead of 95.0.So if we want to get the Double value then the Optional method provides a get() method to retrieve value from optional. But we must check if the value is present or not with ifPresent() method otherwise it will throw NoSuchElementException

Optional<Double> opt = average(90, 100);

```java
if (opt.isPresent())
System.out.println(opt.get()); // 95.0
```

opt = average();

```java
System.out.println(opt.get()); // java.util.NoSuchElementException
```

ifPresent() method does the same except that it Calls Consumer with value

opt.ifPresent(System.out::println);  *//95.0*

Other useful methods

Setting data from optional

- Optional.empty()// Sets null value calling get will cause

- Optional.of(9)// Sets value

- Optional.ofNullable(null)  // can be used to set value

Getting data

Optional<Double> opt2 = average();

```java
System.out.println(opt2.get()); //Gets the value, will be throws null pointer if value is null
System.out.println(opt2.orElse(Double.NaN));
System.out.println(opt2.orElseGet(() -> Math.random()));
System.out.println(opt2.orElseThrow()); // NoSuchElementException
System.out.println(opt2.orElseThrow(() -> new IllegalStateException())); // Throws exception created by calling Supplier
```
