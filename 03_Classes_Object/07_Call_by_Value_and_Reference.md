# Call by Value/Reference

In general, there are two ways that a computer language can pass an argument to a subroutine.

- **Call-by-value**. This approach copies the value of an argument into the formal parameter of the subroutine. Therefore, changes made to the parameter of the subroutine have no effect on the argument.

- **Call-by-reference**. In this approach, a reference to an argument (not the value of the argument) is passed to the parameter. Inside the subroutine, this reference is used to access the actual argument specified in the call. This means that changes made to the parameter will affect the argument used to call the subroutine.

As you will see, Java uses call-by-value to pass all arguments. As we can see below that weather we pass local value, static value or instance value is not changing. Even if we make the as changeValue non-static and access it using test. changeValue still value won’t change. But this behaviour is for primitive types.

```java
public class Test {
    static int i = 1000;
    int j = 1000;

    public static void main(String[] args) {
        Test test = new Test();
        int k = 1000;
        System.out.printf("Values of i %d, j %d, k %d%n", i, test.j, k);
        changeValue(i);
        changeValue(test.j);
        changeValue(k);
        System.out.printf("Values of i %d, j %d, k %d%n", i, test.j, k);
    }

    static void changeValue(int i) {
        i++;
        System.out.println(i);  //1001
    }
}

```

But when we pass object, we see that the results are different.

```java
public class Test {

    public static void main(String[] args) {
        Box b = new Box(1,1,1);
        changeValue(b.height);
        System.out.println(b.height);
        changeValue(b);
        System.out.println(b.height);
        changeObject(b);
        System.out.println(b.height);

    }

    static void changeValue(int i) {
        i++;
    }

    static void changeValue(Box b) {
        b.height++;
    }

    static void changeObject(Box b){
        b = new Box(10,10,10);
    }
}

class Box {
    int height, breath, length;

    public Box(int height, int breath, int length) {
        this.height = height;
        this.breath = breath;
        this.length = length;
    }
}

```

Case 1 — changeValue(b.height)

b.height is a primitive value stored inside the Box object in heap.

Java copies that int value into the method’s parameter i.

i++ changes only the local copy.

The original b.height in the heap is untouched.

Call-by-value effect:→ Copy of the value is changed → caller sees no change.

Case 2 — changeValue(b)

The variable b in main holds a reference to a Box in the heap.

Java copies the value which actually is a reference into the method’s parameter b.

Both the original and the copy point to the same Box object.

b.height++ changes the object in heap → caller sees the change.

Call-by-value effect:→ Copy of the value is passed but the value itself is a reference → both references point to same heap object → changes to object’s fields are visible.

Case 3 — changeObject(b)

Again, Java copies the reference into the method parameter b.

Inside the method, you make the copied reference point to a new Box object.

The original reference in main still points to the old object.

Caller sees no change in b.height.

Call-by-value effect:→ Copy of reference is changed → caller’s reference remains unchanged.

Key Call-by-Value Takeaways

Primitive: Copy of value → modifying it changes only the local copy.

Object reference (modify fields): Copy of reference → changes to the object are seen by caller.

Object reference (reassign): Copy of reference → reassigning only changes local copy → caller unaffected.
