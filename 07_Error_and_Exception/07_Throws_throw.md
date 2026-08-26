# Throws throw

Another way to handle exception is by throwing the exception instead of catching it. With try catch we gobble up the exception and do appropriate handling. But sometimes we are not sure what to do for handling in those scenarios instead of catching the exception we throw the exception and let the calling method handle it. For e.g., if we are writing a utility or a method which will be used differently by different method in those scenarios, we should not handle it but rather throw it and let calling method handle it.

For e.g., in below program in method () we use Thread. Sleep () which throws Interrupted Exception in its definition so in method () we are forced to handle it, either by try catch or by throwing. Here we are throwing it, which means we are forced to handle it in main where we catch it. If we don’t handle there also and throw it would throw the entire stack trace.

```java
public class Test {
    public static void main(String[] args) {
        try {
            method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static void method() throws InterruptedException{
        Thread.sleep(1900);
    }
}

```

But if we throw runtime exception, we will not be forced to handle exception. We will see this in exception hierarchy

```java
public class Test {
    public static void main(String[] args) {
        method();
    }

    static void method() throws RuntimeException {
        System.out.println();
    }
}

```

The throw keyword is used to explicitly throw an exception from a method or any block of code. It can throw both checked and unchecked exceptions

The throws keyword is used in the method signature to declare that a method might throw one or more exceptions. This informs the caller of the method that it needs to handle these exceptions
