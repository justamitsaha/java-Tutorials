# Custom Exception

We can define our own exception to handle our use case. Here we don’t want use to devide by zero hence we are throwing Exception with custom message. Note, since we are throwing Exception we have to handle it, if it was RuntimeException this was not mandatory.

```java
public class Test {
    public static void main(String[] args) {
        try {
            devide(4, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static double devide(int a , int b) throws Exception {
        if (b <=0) {
            throw new Exception("B can't be zero or below zero"+b);
        }
        else return a/b;
    }
}

```

We can also create our own custom Exception  class. This will help in reusability if we want to use in multiple places. Again if it extends Exception it will be checked exception if it extends RuntimeExcetion it will be unchecked exception.

```java
public class Test {
    public static void main(String[] args) {
        try {
            devide(4, 0);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    static double devide(int a , int b) throws Exception {
        if (b <=0) {
            throw new CustomException("B can't be zero or below zero --> "+b);
        }
        else return a/b;
    }
}

class CustomException extends Exception{
    CustomException(String msg){
        super(msg);
    }
}

```
