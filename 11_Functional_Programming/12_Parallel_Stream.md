# Parallel Stream

Parallel Stream takes help of concurrency (multiple threads) to do stream operations. Since it is done using multiple threads hence the sequence is not guaranteed.

List<Integer> ls = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

ls.parallelStream().forEach(System.out::print); //653829471  or any random order

ls.parallelStream().sequential().forEach(System.out::print); // Always 123456789

Suppose we have a complex terminal operation which takes about a second to complete for each iteration

```java
public static int transform(int i) {
try {
```

Thread.sleep(1000);

} catch (Exception e) {

}

return i * 1;

}

With normal stream it will take 1 second for each item of the stream and it will print sequentially

List<Integer> list = List.of(0,1, 2, 3, 4, 5, 6, 7, 8, 9);

list.stream().map(s -> transform(s)).forEach(System.out::print); //0123456789

But with parallel stream it will complete in 2 seconds with out of order printing

List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

list.parallelStream().map(s -> transform(s)).forEach(System.out::print); //7863914250

If we don’t have control on the source of the stream we can use a parallel method. For example if we are getting our stream from a method like below. We don’t have control if it's parallel or normal.

```java
public static Stream<Integer> getStream() {
```

List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

return list.stream();

}

We can use the parallel method to make it parallel.

getStream().parallel()

.map(s -> transform(s))

.forEach(x -> System.out.println(x.toString()));

If we add a sequential method at the end it will become sequential again. It's not like a map will run parallel and for each will run sequential. The entire operation will become sequential. The last method before terminal operation will decide how it will run. In this case it is sequential so it will be sequential.

getStream().parallel()

.map(s -> transform(s))

.sequential()

.forEach(x -> System.out.println(x.toString()));

If we put a print statement in worker and then run it parallel we can see that multiple threads from ForkJoinPool is used by parallel method

Thread[main,5,main]

Thread[ForkJoinPool.commonPool-worker-9,5,main]

Thread[ForkJoinPool.commonPool-worker-3,5,main]

Thread[ForkJoinPool.commonPool-worker-13,5,main]

Thread[ForkJoinPool.commonPool-worker-11,5,main]

Thread[ForkJoinPool.commonPool-worker-7,5,main]

Thread[ForkJoinPool.commonPool-worker-5,5,main]

Thread[ForkJoinPool.commonPool-worker-15,5,main]

Thread[main,5,main]

Thread[ForkJoinPool.commonPool-worker-7,5,main]
