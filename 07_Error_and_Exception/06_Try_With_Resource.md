# Try With Resource

For the Classes which implement Callable or AutoCallable we can use trywithResource

Can use multiple resource in try with resource

Can use finally

Resource in try with resources is final can’t be reassigned

```java
public class Test {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
        } finally {
            if (scanner != null) {
                scanner.close();
                System.out.println("scanner closed");
            }
        }
    }
}

```
