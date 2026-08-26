# New and Java memory allocation

Lets say we have a Square object

```java
public class Test {
    public static void main(String[] args) {
    }
}

class Box {
    int height, width, length;

    int area() {
        return this.height * this.length * this.width;
    }

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
}
```

### How are objects stored and referenced?

- In Java objects are stored in a memory location called Heap.
- When objects are declared just a reference variable is created pointing to nothing.

```java
public class Test {
    public static void main(String[] args) {
        Box b1; // Points to nothing}
    }
}

class Box {
    int height, width, length;

    int area() {
        return this.height * this.length * this.width;
    }

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
}
```

- Objects are created with new keyword. new operator dynamically allocates during runtime memory for an object and returns a reference to it.

```java
public class Test {
    public static void main(String[] args) {
        Box b1 = new Box(1, 1, 1); // Points to location in memory}
    }
}
class Box {
    int height, width, length;

    int area() {
        return this.height * this.length * this.width;
    }

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
}
```

- This reference is, essentially, the address in memory of the object allocated by new. This reference is then stored in the variable.

- So before new we can’t access its members (variables or methods)

![image2.png](../images/image2.png)

![image3.png](../images/image3.png)

Whenever we use = operator with objects it does memory assignment.

- b2 = b1 means both point to same location and values, if we change value of b1 , b2 also changes

.

- If we do b1 = b2 and after that if we do b2 = b3, it will mean b1 still points to old location and will not change

- If we do b1 = null b2 will still points to previous memory location

```java
public static void main(String[] args) {
    Box b1 = new Box(1,1,1);
    Box b2 = b1;
    System.out.println(b2.length);
    b1.length =2;
    //when b1=b2 changing value of one will change other
    System.out.println(b2.length);

    Box b3 = new Box(3,3,3);
    b2 = b3;
    //b1 still points to old object
    System.out.println(b1.length);

    //b3 becomes null b1 and b2 unaffected
    b3 = null;
    System.out.println(b2.length);
}

```

Once the object is created with new keyword(not needed for String) then only we can act on it and access its members

```java
Box box;
box.toString(); // This is not compile

Box box = null; // With this we can trick Java into compiling
box.toString(); // But will throw NullPointerException

```

- Primitives like int height; don’t need new operator as they are stored in stack instead of heap where objects are stored for better efficiency.

- new allocates memory for an object during run time.

- The advantage of this approach is that your program can create as many or as few objects as it needs during the execution of your program.

- However, since memory is finite, it is possible that new will not be able to allocate memory for an object because insufficient memory exists.

- If this happens, a run-time exception will occur.

**Java Memory management**

The JVM divides the memory into two parts: [stack](https://www.javatpoint.com/java-stack) memory and [heap](https://www.javatpoint.com/java-heap) memory. Stack is used to store the order of method execution and local variables while the heap memory stores the objects, and it uses dynamic memory allocation and deallocation.

- **Heap** Objects

- **Stack**  This is the area of memory where local variables and method calls are going to be stored. So whenever a new method is being called, a new stack frame is going to be created on the stack, which will contain all the methods, local variables and parameters. And whenever the method returns the execution or completes the execution, the stack frame is going to be removed along with the local variables and the parameters.

- **Method Area**  This is the place where we are going to store all the class definitions, method definitions and other runtime constants like static variables are going to be stored.

- **Native Heap**   This is the area of memory where native libraries, and the code are going to be loaded are stored. JIT compiler is going to identify the hotspots inside my classes and methods, which are going to be executed many times or very frequently all such hotspots bytecode is going to be converted into a native executable code by the JIT compiler, and it is going to cache it inside a memory. And that memory is native heap.

When object are not used then it is garbage collected from Heap

[https://www.javatpoint.com/stack-vs-heap-java](https://www.javatpoint.com/stack-vs-heap-java)
