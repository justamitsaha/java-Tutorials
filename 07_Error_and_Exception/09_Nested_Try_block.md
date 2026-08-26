# Nested Try block

Try catch can also be nested in that case the exception in inner block will not impact outer block

```java
public class Test {
    public static void main(String[] args) {
        try {
            System.out.println("Outer try block: Start");

            try {
                System.out.println("Inner try block: Start");
                // Simulating an arithmetic exception
                int result = 10 / 0;
                System.out.println("Inner try block: End");
            } catch (ArithmeticException e) {
                System.out.println("ArithmeticException caught in inner try block: " + e.getMessage());
            } finally {
                System.out.println("Inner try block: Finally block");
            }
            System.out.println("Outer try block: End");
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException caught in outer try block: " + e.getMessage());
        } finally {
            System.out.println("Outer try block: Finally block");
        }
    }
}

```
