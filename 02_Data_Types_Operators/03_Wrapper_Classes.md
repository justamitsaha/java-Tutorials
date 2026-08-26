# Wrapper Classes

Primitive to Object Boxing

Object to primitive Unboxing

```java
int i =3;
Integer in = Integer.valueOf(i);  //Boxing
i = in.intValue();  //Unboxing

```

String conversion

```java
byte x = 100;
String s = Byte.toString(x);
Byte byte1 = Byte.parseByte(s);
System.out.println(x+ s+ byte1);

String s = "2000";
Integer i = Integer.parseInt(s);

```

java.lang.NumberFormatException: For input string: "a"

```java
String s = "a";
Integer i = Integer.parseInt(s);
System.out.println(i);

```

Wrapper classes do caching for small values to save memory.

ByteCache (-128 to 127)

ShortCache (-128 to 127)

LongCache (-128 to 127)

CharacterCache (0 to 127)

Which means when the value is within this value java will resue the values

```java
Integer i1 = 127;
Integer i2 = 127;
System.out.println(i1 == i2);   //true
i1 = 128;
i2 = 128;
System.out.println(i1 == i2);   //false

```
