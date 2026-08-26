# Finally

Sometimes we need to make sure that some housekeeping logic must always execute. Like closing DB connection, closing scanner or file reader otherwise resources are wasted. These things must be placed in finally block so that it executes.

For e.g. In below code even tough the exception is thrown by some other code finally makes sure that scanner is closed.

```java
public class Test {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            int arr[] = { 1, 2, 3 };
            int i = arr[6];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}

```

Even with a return statement finally gets executed. It is executed before return.

> 💡 **Note:** Trick how to execute something after return

```java
public class Test {
    public static void main(String[] args) {
        System.out.println(method1());
    }

    static String method1 (){
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            int arr[] = { 1, 2, 3 };
            int i = arr[1];
            return "HI";  // method returns here and ends
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
                System.out.println("scanner closed"); // even with return finally will execute
            }
        }
        return "bye";  //Will or execute
    }
}

```

A try will need at least a catch or a finally. If we don’t put either it will not compile
