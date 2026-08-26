# Exception hierarchy in catch block

Exception declaration also must maintain hierarchy with the more specific exception declared first and more generic exception (higher in inheritance hierarchy) declared later. For e.g.in below example since NullPointerException extends RunTimeException which extends Exception class ., hence NullPointerException is declared above Exception in catch block. Exception which are in same level of inheritance hierarchy can be declared before or after, like in the example below ArrayIndexOutOfBoundsException and NullPointerException

```java
public class Test {
    public static void main(String[] args) {
        String st = null;
        try {
            st.length(); // Will cause null pointer exception
            System.out.println("will not print");
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace(); // can be declared before or after NullPointerException
        } catch (NullPointerException ex) {
            ex.printStackTrace(); // can't be declared after Exception
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
