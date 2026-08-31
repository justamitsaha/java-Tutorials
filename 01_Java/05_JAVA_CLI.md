# Java Command-Line Interface (CLI)

## Overview

The Java Development Kit (JDK) provides command-line tools to compile, package, and execute Java applications directly from a terminal or shell environment.

---

## Standard Two-Step Execution

For standard Java development, execution follows a two-step process:

### 1. Create Source Code
Save the following source code into a file named `Test.java`:

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### 2. Compile to Bytecode
Invoke the Java compiler (`javac`) to produce a `.class` bytecode file:

```bash
javac Test.java
```

This generates `Test.class` in the current working directory.

### 3. Execute Bytecode on the JVM
Launch the Java Virtual Machine by passing the class name (without the `.class` extension):

```bash
java Test
```

---

## Single-File Source-Code Execution (Java 11+)

Starting with **Java 11 (JEP 330)**, the `java` launcher can compile and run single-file source code programs directly in memory without producing an explicit `.class` file on disk:

```bash
java Test.java
```

This is particularly useful for scripting, quick prototyping, and educational demonstrations.

---

## Passing Command-Line Arguments

Command-line parameters passed after the class or file name are provided to the `main(String[] args)` method as a `String[]` array:

```java
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Arguments provided (" + args.length + "):");
            Stream.of(args).forEach(arg -> System.out.println(" - " + arg));
        } else {
            System.out.println("No command-line arguments provided.");
        }
    }
}
```

### Execution Example with Arguments

```bash
java Test.java Alpha Beta 123
```

**Output:**
```
Arguments provided (3):
 - Alpha
 - Beta
 - 123
```

---

## Useful JVM CLI Flags

| Flag | Description | Example |
| :--- | :--- | :--- |
| `-cp` / `--class-path` | Specifies classpath locations for compiled dependencies and JARs | `java -cp .;lib/* com.example.App` |
| `-version` / `--version` | Displays the installed JDK version | `java -version` |
| `-Xms<size>` | Sets the initial heap allocation size | `java -Xms512m Test` |
| `-Xmx<size>` | Sets the maximum heap allocation size | `java -Xmx2g Test` |
| `-ea` / `-enableassertions` | Enables assertion evaluation at runtime | `java -ea Test` |
