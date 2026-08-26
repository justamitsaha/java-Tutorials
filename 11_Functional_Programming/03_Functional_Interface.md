# Functional Interface

With lambda function we still see one drawback that we must create FI ourselves. This seems unnecessary as it increase the footprint of our application. Hence java provides some common FI predefined in JDK. We can utilize these and hence eradicate the necessity of writing FI which seems like boilerplate code.  The out of the box Functional Interface provided by java will cover most of the scenarios needed by developers. For some rare cases we can still create FI, but mostly it’s not needed. List of predefined FI are mentioned below.

| FI | method | info | Usage |
| --- | --- | --- | --- |
| Predicate<T> | test(T) | Used to test condition return boolean | Predicate<String> p1 = String::isEmpty;<br>Predicate<String> p2 = x -> x.isEmpty();<br>System.out.println(p1.test(""));<br>System.out.println(p2.test("")); |
| Function<T,R> | apply(T) | Take input give result | Function<String, Integer> f1 = String::length;<br>Function<String, Integer> f2 = x -> x.length();<br>System.out.println(f1.apply("cluck"));<br>System.out.println(f2.apply("cluck")); |
| UnaryOperator<T> | apply(T,U) | Input and output parameters type is same, similar to Functions use in case both parameters are same | UnaryOperator<String> u1 = String::toUpperCase;<br>UnaryOperator<String> u2 = x -> x.toUpperCase();<br>System.out.println(u1.apply("chirp"));  <br>System.out.println(u2.apply("chirp")); |
| Consumer<T> | accept(T) | do something with a parameter but not return anything | Consumer<String> c1 = System.out::println;<br>Consumer<String> c2 = x -> System.out.println(x);<br>c1.accept("Annie");<br>c2.accept("Annie"); |
| Supplier<T> | get() | generate or supply values without taking any input, like creating objects. | Supplier<LocalDate> s1 = () -> LocalDate.now();<br>Supplier<LocalDate> s2 = LocalDate::now;<br>System.out.println(s1.get()+ s1.get()); |
| BiPredicate<T,U> | test(T,U) | Same as Predicate takes 2 input | BiPredicate<String, String> b1 = String::startsWith;<br>BiPredicate<String, String> b2 = (s, p) -> s.startsWith(p);<br>System.out.println(b1.test("chicken", "chick"));<br>System.out.println(b2.test("chicken", "chick")); |
| BiFunction<U,T,R> | apply(U,T) | Same as function accepts 2 input | BiFunction<String, String, String> b1 = String::concat;<br>BiFunction<String, String, String> b2 = (s, b) -> s.concat(b);<br>System.out.println(b1.apply("baby ", "chick"));<br>System.out.println(b2.apply("baby ", "chick")); |
| BiConsumer<T,U> | accept(T,U) | Same as consumer accepts 2 values | var map = new HashMap<String, Integer>();<br>BiConsumer<String, Integer> b1 = map::put;<br>BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);<br>b1.accept("chicken", 7);<br>b2.accept("chick", 1); |
| BinaryOperator<T> | apply(T,U) | Replacement of BiFunction when all input and return is of same type | BinaryOperator<String> b1 = String::concat;<br>BinaryOperator<String> b2 = (s, b) -> s.concat(b);<br>System.out.println(b1.apply("baby ", "chick"));<br>System.out.println(b2.apply("baby ", "chick")); |

**1Predicate<T> :** Takes an input return boolean. It can be used to test any condition hence it has an abstract method boolean test(T) . It also has static methods to chain the implementation with other predicates. and(),  or(), not(), negate(), isEqual()

Predicate<Integer> isEven = num -> num%2==0;System.out.println(isEven.test(16)); ***// true***System.out.println(isEven.test(13)); ***// false***Predicate<Integer> isGreaterThan50 = num -> num>50;System.out.println(isGreaterThan50.test(61)); ***// true***System.out.println(isGreaterThan50.test(31)); ***// false// To check if the number is even and greater than 50***System.out.println(isEven.and(isGreaterThan50).test(63)); ***// false logical and operation// To check if the number is even or greater than 50***System.out.println(isEven.or(isGreaterThan50).test(63)); ***// true logical or operation***System.out.println(isEven.negate().test(63)); ***// true  logical negate operation***Predicate<Integer> isOdd = Predicate.not(isEven);System.out.println(isOdd.test(4)); ***// false***Predicate<String> checkEquality = Predicate.isEqual("Eazy Bytes");System.out.println(checkEquality.test("Eazy Bytes")); ***// true***

2 **Function<T,R> :** Represents a function that accepts one argument T and produces a result R. Has abstract method R apply(T). It can take an input of one type and return something different.

Predicate<Integer> isEven = num -> num%2==0;System.out.println(isEven.test(16)); // trueSystem.out.println(isEven.test(13)); // falsePredicate<Integer> isGreaterThan50 = num -> num>50;Function<String, String> convertStr = input -> input.toUpperCase();System.out.println(convertStr.apply("Eazy Bytes"));Function<String, Integer> getStrLength = input -> input.length();System.out.println(getStrLength.apply("Eazy Bytes"));Function<String, String> sameValue = Function.identity();   // returns same valueSystem.out.println(sameValue.apply("Hi Madan"));Function<Integer, Integer> doubleValue = num -> num * 2;Function<Integer, Integer> addThree = num -> num + 3;Function<Integer, Integer> output1 = doubleValue.andThen(addThree); // first left side will execute then right sideFunction<Integer, Integer> output2 = doubleValue.compose(addThree); // first right side will execute then left sideSystem.out.println(output1.apply(5)); // 13System.out.println(output2.apply(5)); // 16

3 **UnaryOperator<T> :** Represents a function that accepts an argument and produces a result of the same type. Has abstract method T apply(T). This extends Function but the difference is that the return type and arguments are of the same type as compared to Function. Its convenient to use when both input and out put are of same type

UnaryOperator<String> convertStr = input -> input.toUpperCase();System.out.println(convertStr.apply("Eazy Bytes"));UnaryOperator<String> sameValue = UnaryOperator.identity();System.out.println(sameValue.apply("Hi Madan"));UnaryOperator<Integer> doubleValue = num -> num * 2;UnaryOperator<Integer> addThree = num -> num + 3;Function<Integer, Integer> output1 = doubleValue.andThen(addThree);Function<Integer, Integer> output2 = doubleValue.compose(addThree);System.out.println(output1.apply(5)); // 13System.out.println(output2.apply(5)); // 16

4 **Consumer<T> :** Returns nothing and has 1 input. Has abstract method accept(T), You use a Consumer when you want to do something with a parameter but not return anything

Consumer<String> convertAndDisplay = input -> System.out.println(input.toUpperCase());convertAndDisplay.accept("Eazy Bytes");Consumer<Integer> squareOf = num -> System.out.println(num * num);List<Integer> numbersList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);numbersList.forEach(squareOf);Consumer<String> appendInput = input -> System.out.println("New value after appending is : HELLO " + input);appendInput.andThen(convertAndDisplay).accept("Lambda Expression"); // One lambda will be executed after another

5 **Supplier<T>** : Returns an object and has no input. Has abstract method <T> get(). A Supplier is used when you want to generate or supply values without taking any input, like creating objects.

Supplier<Integer> getCurrentMonth = () -> LocalDate.now().getMonthValue();System.out.println(getCurrentMonth.get());Supplier<Integer> getCurrentDayOfMonth = () -> LocalDate.now().getDayOfMonth();System.out.println(getCurrentDayOfMonth.get());Supplier<String> getCurrentDayName = () -> LocalDate.now().getDayOfWeek().name();System.out.println(getCurrentDayName.get());

6 **BiPredicate<T,U>** : Takes 2 inputs and returns a boolean. Has an abstract method boolean test(T,U) .

BiPredicate<String, String> b1 = String::startsWith;

BiPredicate<String, String> b2 =  (string, prefix) -> string.startsWith(prefix);

```java
System.out.println(b1.test("chicken", "chick"));
System.out.println(b2.test("chicken", "chick"));

```

BiPredicate<Integer, Integer> isSumEven = (num1, num2) -> (num1+num2) % 2 == 0;System.out.println(isSumEven.test(4,9)); // false

7 **BiFunction<U,T,R> :** Represents a function that accepts two arguments and produces a result. Has abstract method R apply(U,T) .

BiFunction<String, String, String> b1 = String::concat;

BiFunction<String, String, String> b2 = (string, toAdd) -> string.concat(toAdd);

```java
System.out.println(b1.apply("baby ", "chick"));
System.out.println(b2.apply("baby ", "chick"));

```

BiFunction<Double,Double,Double> calculatePower = (num1, num2) -> Math.pow(num1, num2);System.out.println(calculatePower.apply(2.0,3.0));

8 **BiConsumer<T,U>** : Returns nothing and has 2 input. Has abstract method accept(T,U)

var map = new HashMap<String, Integer>();

BiConsumer<String, Integer> b1 = map::put;

BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);

b1.accept("chicken", 7);

b2.accept("chick", 1);

```java
System.out.println(map);

```

9 **BinaryOperator<T> :** It is a counter part of Bi function where all input and output are of same type. Just like Function has a Urnary operator similarly BiFunction has BinaryOperator where input and output type is same Has abstract method T apply(T) .

BinaryOperator<String> b1 = String::concat;

BinaryOperator<String> b2 = (firstString, secondString) -> firstString.concat(secondString);

```java
System.out.println(b1.apply("baby ", "chick"));
System.out.println(b2.apply("baby ", "chick"));

```

Scope of Lambda Expression

Lambda expression is not going to create its own scope. Instead, it is going to work within the scope of the enclosing context. This characteristic is referred as lexical scoping inside lambda expressions concept. As a result, below code will not compile as input is already defined outside Lambda

String input = "Hello World";Consumer<String> consumer = input -> { //This will not compile name of input needs to be changed    System.out.println(input+input);};consumer.accept(input);

But below will compile

Consumer<String> consumer = input -> {    System.out.println(input);};String input = "Hello World"; // This will compile as variable input in Lambda is not available hereconsumer.accept(input);

Below will not compile. Because Variable used in lambda expression should be final or effectively final. This is enforced because inside the multithreading environment, we will get unexpected results, because each execution of your lambda expression is trying to have different,

different values.

String input = "Hello World";Consumer<String> consumer = input1 -> {    input = "Hi World";     //This will not compile    System.out.println(input1+input);};consumer.accept(input);

Even if we change the value outside lambda it will not compile like below

String input = "Hello World";input = "Hi World";     Consumer<String> consumer = input1 -> {    System.out.println(input1+input);   //This will not compile};consumer.accept(input);

Can create and modify variables inside Lambda

String input = "Hello World";Consumer<String> consumer = input1 -> {    String msg = "Hi";    msg = "wow";    // Will compile    System.out.println(input1+input + msg);};consumer.accept(input);
