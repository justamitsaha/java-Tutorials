# Virtual Threads in Java 21: A Demonstration

### Inside This Lecture

In this lecture, we'll demonstrate the creation and performance of virtual threads in Java 21. We'll compare the performance of virtual threads with traditional platform threads.

### Creating the VirtualThreadDemo Class

- Main Method:

- Start by creating a Java class named VirtualThreadDemo.

- Define the main() method.

- Runnable Object:

- Create a Runnable object using a lambda expression.

- This Runnable will generate two random double values, multiply them, and store the result in a variable.

java

Copy code

```java
import java.util.Random;

public class VirtualThreadDemo {
public static void main(String[] args) throws InterruptedException {
```

Random random = new Random();

Runnable runnable = () -> {

double result = random.nextDouble() * 1000 * random.nextDouble() * 1000;

```java
System.out.println(result);
```

};

// Measure time for normal threads

long startTime = System.currentTimeMillis();

```java
for (int i = 0; i < 500000; i++) {
```

Thread thread = new Thread(runnable);

thread.start();

thread.join();

}

long endTime = System.currentTimeMillis();

```java
System.out.println("Total time for normal threads: " + (endTime - startTime) + " ms");

```

// Measure time for virtual threads

startTime = System.currentTimeMillis();

```java
for (int i = 0; i < 500000; i++) {
```

Thread vThread = Thread.startVirtualThread(runnable);

vThread.join();

}

endTime = System.currentTimeMillis();

```java
System.out.println("Total time for virtual threads: " + (endTime - startTime) + " ms");
```

}

}

### Explanation

- Runnable Object:

- A Runnable object is created to generate two random double values, multiply them, and print the result.

- Normal Threads:

- A loop creates and starts 500,000 normal threads.

- Each thread executes the Runnable task.

- The join() method ensures the main thread waits for each thread to complete before starting the next one.

- The time taken to execute all threads is measured and printed.

- Virtual Threads:

- The same loop creates and starts 500,000 virtual threads using Thread.startVirtualThread(runnable).

- The time taken to execute all virtual threads is measured and printed.

### Output

- **Normal Threads**: The program typically takes around 30 seconds to complete.

- **Virtual Threads**: The program completes in approximately 9 seconds, demonstrating the efficiency of virtual threads.

### Key Points

- **Memory Efficiency**: Virtual threads consume significantly less memory compared to platform threads.

- **Performance**: Virtual threads offer improved performance due to lower overhead in creation and management.

- **Static Methods**: Java 21 provides static methods like Thread.startVirtualThread() and Thread.ofVirtual() to create virtual threads easily.

### Additional Methods

- **Thread.isVirtual()**: This static method checks if a given thread is a virtual thread.

### Conclusion

Virtual threads in Java 21 provide a powerful tool for building highly concurrent applications with minimal overhead. They offer significant performance improvements and are easier to use with familiar threading constructs.
