# Executer Service

Normal way of starting thread had a few disadvantages like

- Code for starting new thread is cumbersome and has a lot of boilerplate code

- No control on how many threads can be started, can’t place any restriction.

- Can’t put any condition like start new thread when either of the existing thread is completed. For e.g. if we have 2 thread- Therad1 and Thread2, we can put condition to start new thread only once Thread1 and Thread2 is completed individually, but can’t put condition that when either of Thread1 or Thread2 is completed start new thread

- Can’t return value from thread.

These can be overcome using Executer service implementation below. We create an instance of ExecutorService using Executors.newFixedThreadPool(2) method and pass the Task in in the execute method of ExecutorService

Since we have mentioned newFixedThreadPool(2)  so only 2 threads will run at a time once they complete then new Threads will start

```java
public class Test {
public static void main(String[] args) throws InterruptedException {
```

ExecutorService exe = Executors.newFixedThreadPool(2);

// Once 2 threads are completed then only new Threads are started

exe.execute(new Task(1));

exe.execute(new Task(2));

exe.execute(new Task(3));

exe.execute(new Task(4));

exe.shutdown();

}

}

```java
class Task extends Thread {
```

private int count;

Task(int count) {

this.count = count;

}

```java
public void run() {
System.out.println("Task " + count + " started");
for (int i = 0; i < 5; i++) {
System.out.println("Task-" + count + " " + i);
```

}

```java
System.out.println("Task " + count + " ended");
```

}

}

Return from Thread using executor service. Instead of execute method we will use submit method , which will return a Future from where we can get the result using get() method.

Main will run only when ExecutorService thread has completed.

```java
public class Test {
public static void main(String[] args) throws InterruptedException, ExecutionException {
```

ExecutorService executorService = Executors.newFixedThreadPool(1);

Future<String> future = executorService.submit(new CallableTask("Amit"));

//Future holds a promise

```java
System.out.println(future.get());
System.out.println("Main completed");
```

executorService.shutdown();

}

}

```java
class CallableTask implements Callable<String> {
```

private String greeting;

CallableTask(String st) {

this.greeting =st;

}

@Override

```java
public String call() throws Exception {
```

Thread.sleep(1000);

return "Hello" +greeting;

}

}

Multiple tasks 🡪 Here we can initiate multiple tasks using ExecutorService. We create a list of tasks and pass to ExecutorService and invoke all using invokeAll() method and retrieve the results

```java
public class Test {
public static void main(String[] args) throws InterruptedException, ExecutionException {
```

ExecutorService executorService = Executors.newFixedThreadPool(3);

List<CallableTask> callableTasks = List.of(new CallableTask("Amit",1000),

new CallableTask("Shamit",500),

new CallableTask("Bhamit",2000));

List<Future<String>> future = executorService.invokeAll(callableTasks);

```java
for (Future<String> result : future) {
System.out.println(result.get());
```

}

```java
System.out.println("Main completed");
```

executorService.shutdown();

}

}

```java
class CallableTask implements Callable<String> {
```

private String greeting;

private int wait;

CallableTask(String st, int wait) {

this.greeting = st;

this.wait = wait;

}

@Override

```java
public String call() throws Exception {
```

Thread.sleep(this.wait);

return "Hello" + greeting;

}

}

In above example even tough different tasks are having different wait time, Still we get the results when all the ExecutorService has completed. We can overcome this by calling invokeAny method of ExecutorService. This will return as soon as the results are available but it will return the first result and discard others. It doesn’t wrap the result in Future but just returns the result.

```java
public class Test {
public static void main(String[] args) throws InterruptedException, ExecutionException {
```

ExecutorService executorService = Executors.newFixedThreadPool(3);

List<CallableTask> callableTasks = List.of(new CallableTask("Amit", 1000),

new CallableTask("Shamit", 500),

new CallableTask("Bhamit", 2000));

String future = executorService.invokeAny(callableTasks);

```java
System.out.println(future);
System.out.println("Main completed");
```

executorService.shutdown();

}

}

```java
class CallableTask implements Callable<String> {
```

private String greeting;

private int wait;

CallableTask(String st, int wait) {

this.greeting = st;

this.wait = wait;

}

@Override

```java
public String call() throws Exception {
```

Thread.sleep(this.wait);

return "Hello" + greeting;

}

}
