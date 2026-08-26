# Multiple Exception

Although catch (Exception ex) can catch all kinds of exception its better to have different exception for different senarios

```java
public class Test {
    public static void main(String[] args) {
        String input = null;
        try {
            input = "Super";
            input = input.toUpperCase();
            System.out.println(input);
            input = input.substring(1, 10);
            System.out.println(input);
        } catch (NullPointerException ex) { // Will catch only the specific exception
            System.err.println("An null pointer exception occurred. Please check your data");
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ex) {
        /*When there is a common logic we can club and the exception must not have parent child relationship
        Otherwise won't compile. Introduced in Java 7 to reduce duplicate code */
            System.err.println("IndexOutOfBoundsException exception occurred. Please check your input data");
        } catch (Exception ex) {   // Must be at the end as it handles all exception otherwise won't compile
            System.err.println("An exception occurred. Please check your program");
        }
    }
}

```

With multiple exception parent should come after child, i.e more specific exception declared first and more generic exception (higher in inheritance hierarchy) declared later

```java
public class Test {
    public static void main(String[] args) {
        String input = null;
        File myObj = new File("filename.txt");
        try {
            Scanner myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {     // Child should be above parent
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
        IOException parent
            |
            |
           \ /
        FileNotFoundException child
     */

}

```
