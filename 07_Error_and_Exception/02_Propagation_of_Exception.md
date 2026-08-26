# Propagation of Exception

In Java when an exception happens it propagates along the execution chain and a stack trace is provided from source to main method calling it. For e.g., in below example error is propagated from method2() till main.

Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.length()" because "<local0>" is null

at IncorrectCode.method2(Test.java:17)

at IncorrectCode.method1(Test.java:11)

at Test.main(Test.java:4)

```java
public class Test {
    public static void main(String[] args) {
        IncorrectCode.method1();
        System.out.println("Main"); // Will not be executed
    }
}

class IncorrectCode {
    public static void method1() {
        method2();
        System.out.println("Method1");      // Will not be  executed
    }

    public static void method2() {
        String st = null;
        st.length();    // Will cause null pointer exception
        System.out.println();
        System.out.println("Method1");
    }
}

```

In above code when the exception happens it is not handled and the program stops abruptly and nothing after exception gets printed e.g.   sysout in method2, method1 and main is not printed. This is because exception is not handled. To complete information we need this information. We can fix these by adding try catch block.

```java
public class Test {
    public static void main(String[] args) {
        IncorrectCode.method1();
        System.out.println("Main");  // will be executed
    }
}

class IncorrectCode {
    public static void method1() {
        method2();
        System.out.println("Method1");      // will be executed
    }

    public static void method2() {
        String st = null;
        try {
            st.length(); // Will cause null pointer exception
            System.out.println("will not print");  // will not print
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Method2");
    }
}

```

Now the program will not end abruptly and will gracefully close and we will get all the information. However line after the exception in try block will not execute `System.out.println("will not print");`

```java
java.lang.NullPointerException: Cannot invoke "String.length()" because "st" is null
```

`at testing.IncorrectCode.method2(Test.java:19)`

`at testing.IncorrectCode.method1(Test.java:12)`

`at testing.Test.main(Test.java:5)`

`Method2`

`Method1`

`Main`
