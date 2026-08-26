# Operators and control Flow

**Pre increment/decrement operator** (++  --)value change first then assignment

```java
int i = 4;
int j = ++i;  //both will have 5
System.out.println("i " + i + " j " + j);

```

**Post increment/decrement operator** first assignment then value change

```java
int i = 4;
int j = i--;  //i will 3 while j will be 4
System.out.println("i " + i + " j " + j);

```

Concatenation Operator(+)

Only operator which is overloaded for String it concats for int it adds

```java
int i = 3;
int j = 4;
String s = "Hello";
System.out.println(i + j);  //7
System.out.println(i + j + s);  // 7hello
System.out.println(s + i + j); //Hello34

```

Arithmetic operation  + - * / %

**Relational Operator** <, <=, >, >=, ==, !=

Can’t be used with boolean

```java
System.out.println(true> false); // won't compile

```

Bitwise operator

| & | Both true | Works for integer and boolean |
| --- | --- | --- |
| && | Both true but if 1st  is false won’t evaluate 2nd | Works only for boolean, better performance |
| &#124; | Any one true | Works for integer and boolean |
| &#124;&#124; | if 1st  is true won’t evaluate 2nd | Works only for boolean, better performance |
| ^ | Both should be different one true one false |  |
| ~ | Converts to bit and Flip all bits | Works only on int |
| ! |  |  |

| and & works on int also it converts the int to binary and work on each of the digit

```java
System.out.println(4 | 5);  //5
System.out.println(4 & 5);  //4

```

4 & 5100

101

100  = 4

4 |5 100

101

101 =5

Assignment operator =, =+, =-

```java
int x, y, z;
```

x = y = z = 22;

```java
int i = 3;
System.out.println(i += 40); //43
System.out.println(i += 6); //49
System.out.println(i *= 4); //196
System.out.println(i /= 4); //49
System.out.println(i %= 2); //1

```

**Ternary operator** Expression  ? vaue1 : value2

Control Flow

If

else

else if

do

while

break

continue

return
