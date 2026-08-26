# JAVA CLI

How to run JAVA program?

Create java Class file Amit.java

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

Compile .java to .class by running    javac .\Test.java

Run the class java Test

In java11 we can run directly without compiling like *“java Test.java*”. This is called single-file source-code.

We can also pass arguments to java program. For e.g.  for below Test.java we can run as  *java Test.java 123 ABC*

```java

import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        if (args.length > 0) {
            Stream.of(args).forEach(s -> {
                System.out.println("Your Args" + s);
            });
        } else {
            System.out.println("No Args passed");
        }
    }
}

```
