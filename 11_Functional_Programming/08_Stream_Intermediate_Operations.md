# Stream Intermediate Operations

Unlike a terminal operation, an intermediate operation produces a stream as its result. An intermediate operation can also deal with an infinite stream simply by returning another infinite stream

1 **filter() :** The filter() method returns a Stream with elements that match a given expression. Here is the method

signature: Stream<T> filter(Predicate<? super T> predicate)

Stream<String> stream = Stream.of("monkey", "gorilla", "bonobo");

stream.filter(x -> x.startsWith("m")).forEach(System.out::print);*// monkey*

2 **distinct() :** The distinct() method returns a stream with duplicate values removed. Java calls equals() to determine whether the objects are the same

Stream<String> s = Stream.of("duck-", "duck-", "duck-", "goose");

s.distinct().forEach(System.out::print);*//duck-goose*

3 **limit() and skip() :** The limit() and skip() methods can make a Stream smaller, or they could make a finite stream out of an infinite stream

Stream<Integer> s2 = Stream.iterate(1, n -> n + 1);

s2.skip(5).limit(2).forEach(System.out::print);

4 **map() :** The  map() is used to transform one object into another by applying a business logic with a lambda expression.

<R> Stream<R> map(Function<? super T, ? extends R> mapper)

Stream<String> s3 = Stream.of("monkey", "gorilla", "bonobo");

s3.map(String::length).forEach(System.out::print);

5 **flatMap() :** The flatMap() function allows flattening of nested collections or streams within a stream, resulting in a single stream of elements. So when each element of Stream is it self a stream or collection we need to flatten the element then we can extract individual item

List<String> zero = List.of();

var one = List.of("Bonobo");

var two = List.of("Mama Gorilla", "Baby Gorilla");

Stream<List<String>> animals = Stream.of(zero, one, two);

animals.flatMap(m -> m.stream()).forEach(System.out::println);

String[] arrayOfWords = { "Eazy", "Bytes" };

Stream<String> streamOfWords = Arrays.stream(arrayOfWords);

Stream<String[]> streamOfLetters = streamOfWords.map(word -> word.split(""));

streamOfLetters.flatMap(Arrays::stream).forEach(System.out::println);

6 **sorted() :** The sorted() method returns a stream with the elements sorted. Java uses natural ordering unless we specify a comparator.

Stream<String> stream2 = Stream.of("brown-", "bear-");

stream2.sorted().forEach(System.out::print); *// bear-brown*

Stream<String> stream3 = Stream.of("brown bear-", "grizzly-");

stream3.sorted(Comparator.reverseOrder()).forEach(System.out::print); *// grizzly-brown bear*

Take a look at the method signatures again. Comparator is a functional interface. This means that we can use method references or lambdas to implement it. The Comparator interface implements one method that takes two String parameters and returns an int. However, Comparator::reverseOrder doesn't do that. It is a reference to a function that takes zero parameters and returns a Comparator. This is not compatible with the interface. This means that we have to use a method and not a method reference

stream3.sorted(Comparator::reverseOrder);

7 **peek() :** It is useful for debugging because it allows us to perform a stream operation without actually changing the stream. Signature

Stream<T> peek(Consumer<? super T> action)

. peek() operation takes the same argument as the terminal forEach() operation. Think of peek() as an intermediate version of forEach() that returns the original stream back to you.

Stream<String> stream2 = Stream.of("brown-", "bear-");

stream2.sorted().forEach(System.out::print); *// bear-brown*

Stream<String> stream3 = Stream.of("brown bear-", "grizzly-");

stream3.sorted(Comparator.reverseOrder()).forEach(System.out::print); *// grizzly-brown bear*
