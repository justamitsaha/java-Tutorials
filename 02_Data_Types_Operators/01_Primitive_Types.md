# Primitive Types

| 1 | boolean | 1bit |  |  |  |
| --- | --- | --- | --- | --- | --- |
| 2 | byte | 8 bits<br>1 byte | 2^8 | - 128 to 127 |  |
| 3 | char | 16 bits<br>2 bytes | 2^16 | Stores value as int 65,536 | So large to accommodate all special character, uses UNICODE |
| 4 | short | 16 bits<br>2 bytes | 2^16 | -32,768 to 32,767 |  |
| 4 | int | 32 bits<br>4 bytes | 2^32 | -2,147,483,648 to 2,147,483,647 |  |
| 6 | long | 64 bits 8 bytes | 2^64 |  |  |
| 7 | float | 32 bits<br>4 bytes | 2^32 |  |  |
| 8 | double | 64 bits 8 bytes | 2^64 |  |  |

> 💡 **Note:** Default value for primitives

Unlike objects **primitives have default values** inside a class and won’t give null pointer exception when used at class level. Default value of Boolean is false, char is blank space, and all numerical type is 0.0 or 0.

```java
public class Test {
    static boolean bool;
    static int i;
    static char b;
    static float f;

    public static void main(String[] args) {
        System.out.println(String.valueOf(bool)); // false
        System.out.println(String.valueOf(i)); // 0
        System.out.println(String.valueOf("<>" + b + "<>")); // <><>
        System.out.println(String.valueOf(f)); // 0.0
    }
}

```

Local primitives variables don’t have default values. If we use uninitialized local variable, it will not compile and throw error.

```java
public class Test {
    public static void main(String[] args) {
        boolean bool;
        int i;
        // Will not compile
        System.out.println(String.valueOf(bool));
        System.out.println(String.valueOf(i));
    }
}
```

> 💡 **Note:** Char Behaviour

Char uses Unicode, so we can represent char with \u to represent Unicode. Char can store int values also to represent char values but only non negative values.

```java
public class Test {
    public static void main(String[] args) {
        char i = '\u5632';
        System.out.println(String.valueOf(i)); // will print ?
    }
}
```

> 💡 **Note:** Integer Behaviour

Java defaults to integer when we do operation of variables smaller than int like byte, short i.e. When 2 bytes or short are added in an expression  JVM automatically assigns an int for it to avoid any type mismatch

```java
byte i =4 ;
byte j = 6;
byte k = i+j; // won't compile as it is treated as integer instead of byte

```

But it doesn’t happen inside an expression as it is calculated compile time.

```java
byte b = 3 + 4;
System.out.println(b);

```

But if we are out of range it will fail during compilation

```java
byte b = 139+4;  //won't compile

```

Octal number 0-7 starts with 0  int i = 027;  used in embedded systems

Hexadecimal number int i = 0x1B3 ; 0-9 A-F
