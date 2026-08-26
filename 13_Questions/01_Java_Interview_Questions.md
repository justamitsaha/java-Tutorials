# Java Interview Questions

### Q: Is Java Software platform dependent?

Yes

### Q: Is both i and J are initialized   int i,j = 8;

No only J

### Q: Why Java gives 2 bytes 2 ^16 size to char

To accommodate all special characters of various languages

### Q: How can we use characters not in key board?

Char uses Unicode so we can use Unicode

char i = '\u5632';

### Q: Can we add char

Yes

### Q: What is constructor overloading ?

### Q: Difference between methods and constructors?

Methods can be called only once constructors are called only when class is initialized

Methods can have any name Constructors only have class name

Methods can have return type Constructors don’t have

### Q: Is the constructor called when we declare a Class variable?

No Only when initialized

Alpha alpha;  or Alpha alpha = null;

Only with new key word constructor is called

alpha = new Alpha();

### Q: What is the use of Instance Initializers or code blocks.

Needed for Anonymous class which don’t have constructors to handle initialiozation logic.

Also if you have any common logic for multiple constructor you can keep in code blocks

### Q: Instance Initializers or code blocks overwrite Constructors?

No they are implemented before Constructors

### Q: Can a class implement 2 interface with same methods?

Yes it has to implement the methods

### Q: What is native method?

It marks a method, that it will be implemented in other languages, not in Java. It works together with JNI (Java Native Interface).

[https://stackoverflow.com/questions/6101311/what-is-the-native-keyword-in-java-for](https://stackoverflow.com/questions/6101311/what-is-the-native-keyword-in-java-for)

### Q: What is clone method in Java?

It is a method in Object class to create shallow clone i.e. same values but different address

It is protected method

The call implementing but implement cloneable interface

```java
class Alpha implements  Cloneable{    public  Object clone() throws CloneNotSupportedException {        return super.clone();    }    int beta;    public Alpha(int beta) {        this.beta = beta;    }}

```

Alpha a = new Alpha(3);Alpha ab = (Alpha) a.clone();System.*out*.println(a == ab);            //falseSystem.*out*.println(a.equals(ab));   //falseSystem.*out*.println(a.beta +"  "+ab.beta);            //3  3a.beta = 7;System.*out*.println(a.beta +"  "+ab.beta);       //7  3

### Q: What are different classes and interfaces in collections?

List

Set

Queue

Blocking Queue

Map

### Q: Difference between ArrayList and Linked List

ArrayList implements array all items are indexed, List uses internal implementation where each item points to previous and next items

ArrayList is good for sequential addition, random access due to indexing . Linked list is good when we add items in between as we just have to change the pointers of previous and next item for Array list we have to shift all elements.

When we create once and do frequent reads Array list is best.

### Q: Difference between Arrray list/ Vectors and Hashtable VS Hashmaps

Vectors and Hashtables are synchronized and hence slow

### Q: Difference between Hashmap and Linked HashMap

LinkedHashmap maintains order in which they were added, Hashmap doesn’t do it

### Q: Default capacity of ArrayList

10

### Q: Default capacity of HashMap

16 , Loadfactor 75%

### Q: Comparable vs comparator

Comparable                ¦                Comparator                ¦

¦-----------------------------------------+------------------------------------------¦

¦ java.lang.Comparable                    ¦ java.util.Comparator                     ¦

¦-----------------------------------------+------------------------------------------¦

¦ int objOne.compareTo(objTwo)            ¦ int compare(objOne, objTwo)              ¦

¦-----------------------------------------+------------------------------------------¦

¦ Negative, if objOne < objTwo            ¦ Same as Comparable                       ¦

¦ Zero,  if objOne == objTwo              ¦                                          ¦

¦ Positive,  if objOne > objTwo           ¦                                          ¦

¦-----------------------------------------+------------------------------------------¦

¦ You must modify the class whose         ¦ You build a class separate from to sort. ¦

¦ instances you want to sort.             ¦ the class whose instances you want       ¦

¦-----------------------------------------+------------------------------------------¦

¦ Only one sort sequence can be created   ¦ Many sort sequences can be created       ¦

¦-----------------------------------------+------------------------------------------¦

¦ Implemented frequently in the API by:   ¦ Meant to be implemented to sort          ¦

¦ String, Wrapper classes, Date, Calendar ¦ instances of third-party classes.

**1️⃣ Increment and Decrement Confusion**

These are classics — they test your understanding of **pre** vs **post** increment.

**Example:**

```java
int i = 5;
int j = i++ + ++i;
System.out.println(j);
```

👉 **Output:** 12

**Why:**

i++ → returns 5 (then increments to 6)

++i → increments to 7, returns 7

So j = 5 + 7 = 12

**Example 2:**

```java
int a = 1;
```

a = a++ + a + ++a;

```java
System.out.println(a);
```

👉 **Output:** 6

**Why:**

a++ → 1 (then becomes 2)

a → 2

++a → 3Total = 1 + 2 + 3 = 6

**🧩 2️⃣ Assignment Inside Expressions**

**Example:**

```java
int x = 10;
int y = (x = 5) + 2;
System.out.println(y + ", " + x);
```

👉 **Output:** 7, 5

**Why:** assignment expression returns the assigned value.x = 5 → sets x to 5 **and** returns 5, so y = 5 + 2.

**Example:**

```java
int i = 0;
System.out.println(i = 3 * 5);
System.out.println(i);
```

👉 **Output:**

15

15

Assignments in Java are expressions, not statements.

**🧩 3️⃣ Boolean Short-circuit Traps**

**Example:**

```java
int x = 0;
```

boolean b = (x > 0) && (++x > 0);

```java
System.out.println(x);
```

👉 **Output:** 0

**Why:**The first condition (x > 0) is false,so the second (++x > 0) never executes because of **short-circuit AND (&&)**.

**Example:**

```java
int y = 0;
```

boolean b2 = (y > 0) & (++y > 0);

```java
System.out.println(y);
```

👉 **Output:** 1

**Why:**& (bitwise AND) **does not short-circuit**,so both sides execute, and ++y runs.

**🧩 4️⃣ Ternary Operator with Mixed Types**

**Example:**

```java
System.out.println(true ? 1 : "hello");
```

❌ Compile error — incompatible types (int vs String).

**Example 2:**

```java
System.out.println(true ? 1 : 1.5);
```

✅ **Output:** 1.0Because int and double are promoted to a common type (double).

**🧩 5️⃣ Integer Overflow and Promotion**

**Example:**

```java
int a = 1000 * 60 * 60 * 24 * 365;
System.out.println(a);
```

👉 **Output:** 1471228928 ❌ (incorrect due to overflow)

**Fix:**

long a = 1000L * 60 * 60 * 24 * 365;

```java
System.out.println(a); // ✅ 31536000000
```

**Why:**int overflow happens silently — result wraps around.

**Example (Byte arithmetic):**

byte b = 10;

b = b * 2;

❌ Compile error: *"possible lossy conversion from int to byte"*

Because all arithmetic promotes operands to int.✅ Fix:

b = (byte) (b * 2);

**🧩 6️⃣ Comparison Gotchas**

**Example:**

```java
System.out.println(10 == 10.0);
```

✅ true — int promoted to double

**Example 2:**

Integer a = 128;

Integer b = 128;

```java
System.out.println(a == b);
```

❌ **Output:** false

**Why:**Integer caching works for range [-128, 127].Outside that, different objects are created.

✅ Example:

Integer x = 100, y = 100;

```java
System.out.println(x == y); // true

```

**🧩 7️⃣ String Concatenation and + Operator**

**Example:**

```java
System.out.println(1 + 2 + "3" + 4 + 5);
```

👉 **Output:** "3345"

**Why:**

Left to right: 1+2=3,

"3" + 3 = "33",

"33" + 4 = "334", etc.

**Example:**

```java
System.out.println("Result: " + 1 + 2);
```

👉 "Result: 12"

But:

```java
System.out.println("Result: " + (1 + 2));
```

👉 "Result: 3"

**🧩 8️⃣ Modulus and Negative Numbers**

```java
System.out.println(10 % 3);   // 1
System.out.println(-10 % 3);  // -1
System.out.println(10 % -3);  // 1
System.out.println(-10 % -3); // -1
```

The sign of the result is always the same as the **dividend (first number)**.

**🧩 9️⃣ XOR and Bit Tricks**

```java
int a = 5, b = 10;
```

a = a ^ b;

b = a ^ b;

a = a ^ b;

```java
System.out.println(a + ", " + b);
```

✅ **Output:** 10, 5Classic XOR swap trick (no temp variable).

**🧩 🔟 Post-Increment with Array Indexing**

int[] arr = {1, 2, 3};

```java
int i = 0;
```

arr[i++] = arr[i++] + arr[i++];

```java
System.out.println(Arrays.toString(arr));
```

👉 **Output:** [5, 2, 3]

**Explanation:**

i=0 → arr[0] = arr[1] + arr[2] = 2+3=5

Final i = 3

**🧠 Bonus: Operator Precedence**

Always remember Java precedence order (simplified):

++, -- (postfix)

++, --, +, - (prefix)

*, /, %

+, -

<<, >>, >>>

<, <=, >, >=

==, !=

&

^

|

&&

||

?: (ternary)

=, +=, -=, etc.

Example:

```java
System.out.println(10 + 20 * 3); // 70 not 90
System.out.println((10 + 20) * 3); // 90

```

**🧾 Summary Table**

| Concept | Example | Key Lesson |
| --- | --- | --- |
| Pre/Post increment | i++ + ++i | Order of execution matters |
| Assignment return | (x=5)+2 | Assignment returns value |
| Short-circuit | (x>0)&&(++x>0) | Second part may not run |
| Integer cache | Integer a=128,b=128 | False outside -128 to 127 |
| Overflow | 1000*60*60*24*365 | int overflow wraps around |
| String concat | 1+2+"3" | Left-to-right evaluation |
| Modulus | -10%3=-1 | Follows sign of dividend |

Would you like me to make a **small “Operator Trick Quiz” class** in Java (with 10 System.out.println lines and comments to predict outputs)?It’s a fun self-test to run directly in IntelliJ.
