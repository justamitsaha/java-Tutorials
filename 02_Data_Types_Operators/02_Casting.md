# Casting

When we assign a value of one primitive data type to another type, we call it as **type casting**.

In Java, there are two types of casting:

Widening Casting (automatically/implicit) - converting a smaller type to a larger type size. It is done automatically & safe because there is no chance to lose data.

byte -> short -> char -> int -> long -> float -> double

Narrowing Casting (manually/explicit) - converting a larger type to a smaller size type. It should be done explicitly by the programmer. If we do not perform casting then the compiler reports a compile-time error.

s

double -> float -> long -> int -> char -> short -> byte

The process to assigning one data type to another.

It’s of 2 types

- Implicit casting -> Smaller type to larger. Nothing needed JVM takes care of it

```java
byte i =4 ;
int j = i;

```

since char stores values as int

```java
char ch = 'A';
int i = ch;
System.out.println(i); // will return 65

```

- Explicit casting Larger to smaller. In this case explicitly cast has to be mentioned

```java
int i =4 ;
byte j = (byte) i;

```

When the cast is outside the range it goes on negative

```java
int i = 132;
byte b = (byte) i;
System.out.println(b); // -124

long l = 2147483648l;
int i = (int) l;
System.out.println(i); //-2147483648

```

And if it is still out of the range the process continues in circle till it reaches end

```java
int i = 12345;
byte b = (byte) i;
System.out.println(b); // 57

```
