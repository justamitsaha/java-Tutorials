# String

Strings are immutable, i.e. their values can’t be changed.

> 💡 **Note:** String Pool

When string is constructed without using a constructor(String s =”hi” for e.g.) then string is stored in a special area of heap called string pool whose purpose is to maintain a set of unique strings this is called interning. When two strings have the same values then they point to the same location in the string pool hence the == method on two strings will return true.

```java
String s1 = "Hello";
String s2 = "Hello";
System.out.println(s1 == s2);   //true
```

When we assign new values to a string it just points to a new location in memory (thread pool), the original string stays in the pool.

But with new new keyword each string object holds different location even if value is different. Below will not be part of string pool

```java
s1 = new String("hello");
s2 = new String("hello");
System.out.println(s1 == s2);   //false

```

| String s1 = new String("Hello").intern();<br>String s2 = new String("Hello").intern();<br>System.out.println(s1 == s2); //True | String s1 = new String();<br>String s2 = new String();<br>s1 = "hello"; s2 = "hello";<br>System.out.println(s1 == s2); //True |
| --- | --- |

Trick Here in line 2 sicne we are not assigning values s1 value is unchanged

```java
String s1 = "Hello";
s1.concat(" World");
System.out.println(s1); // Prints “Hello”

```

String can also be created from an array of bytes, char and integer. While passing array we can pass

**offset→** no of items that will be skipped from array

**count→** no elements taken from array

offset + count should be equal or less than the length of the array. Above params are optional for byte[] and char[] but mandatory for int[]. Let's say we create array of alphabets a -z and add them to string

```java
char[] ch = new char[26];
byte[] by = new byte[26];
int[] in = new int[26];
int i = 0;
for (char c = 'a'; c <= 'z'; c++, i++) {
ch[i] = c;
by[i] = (byte) c;
in[i] = c;
}
String s1 = new String(ch, 0, 26); // abcdefghijklmnopqrstuvwxyz
String s2 = new String(by, 2, 22);// cdefghijklmnopqrstuvwx ab from beginning and yz from end skipped
String s3 = new String(in, 1, 25); // count and offset is mandatory
System.out.println(s1);
System.out.println(s2);
System.out.println(s3);
```

**String concat**  String uses concat() or + operator to join two strings

- Although we can do string + 3 i.e. concat int or other types with string using “+” but `str.concat(3` will not compile.

- Similarly `str + null` will compile and give something like “Hello null” but `str.concat(null)` will give a null pointer exception. However in either case we can’t use uninitialized variables like `String str2; str + str2 ;` This will not compile`.`

- “+” operator converts int or any other literal to String using to string but we can override using ()

```java
int i = 20, j = 20;
System.out.println(i + j + " = " + i + j); // 40 = 2020
System.out.println(i + j + " = " + (i + j)); // 40 = 40

```

Like Char String also uses Unicode

```java
String m = "\u004D";
System.out.println(m);
String unicodeName = "Hello Amit \u0021";
System.out.println(unicodeName);

```

String methods

|  | Methods | Purpose | Supported by StringBuilder |  |
| --- | --- | --- | --- | --- |
|  | intern() | Will force the string to use String pool |  | String hello = "Hello";<br>        String obj = new String("Hello").intern();<br>        System.out.println(hello==obj); |
|  | length() |  | y |  |
|  | valueOf | int intValue = 42;<br>String.valueOf(intValue); |  | Convert primitives to String |
|  | charAt() |  | y |  |
| Compare | equals() |  | Y |  |
| Compare | equalsIgnoreCase() |  | N |  |
| Compare | compareTo | "java".compareTo("java");<br>Result 0<br>"java".compareTo("python")<br>Result -6 |  | Compares the Unicode char and returns the difference. For difference between j and p is 6 with j smaller so returns -6. Can be used for sorting |
| Compare | isEmpty |  |  | Check for “” |
| Compare | isBlank |  |  | Check for “    “ |
| Search operations | startsWith() |  | N |  |
| Search operations | endsWith() |  | N |  |
| Search operations | substring() | sb.substring(0, 4)<br>Hell | y | Returns the string in the index provided first one inclusive , 2nd exclusive |
| Search operations | indexOf() | sb= "Hello";<br>sb.indexOf("ll") <br>0 true -1 False | y | Returns the position where the string is found if not found returns -1 |
| Search operations | contains() | sb.contains("s"); | N | One format takes String other char sequence |
| Search operations | matches() | str.matches(".*World.*"); |  | Uses regex |
| Modification | replace() | "abc".replace('a', 'A');<br>"abc".replace("a", "A"); | N | Can use regex also like below is used to replace vowels<br>String originalString2 = "Java is fun!";<br> String replacedString4 = originalString2.replaceAll("a&#124;e&#124;i&#124;o&#124;u", "*"); |
| Modification | toLowerCase() |  | N |  |
| Modification | toUpperCase() |  | N |  |
| Modification | trim() |  |  |  |
| Modification | strip() |  |  |  |
| Modification | stripLeading() |  |  |  |
| Modification | stripTrailing() |  |  |  |

Different ways of String empty check

**boolean** isEmpty = myString.isEmpty();// can give null pointer if string is null

**boolean** isLengthZero = myString.length()==0;  // can give null pointer if string is null

**boolean** isEqualEmpty = "".equals(myString);   // no issue with null String

For blank check

String blankString = "       ";

**boolean** isBlankEmpty = blankString.isEmpty();

Format method is used to format the String by replacing the placeholder

```java
String message = "Hello, %s ! You have %d messages."; // %s for replacing String , %d for int
String msgForAmit = String.format(message, "Amit", 3);
String msgForShamit = String.format(message, "Shamit", 6);
System.out.println(msgForAmit);
System.out.println(msgForShamit);

String message1 = "The price is $%.2f"; // %.f to insert double or float 2 represent the no of decimal needed
String price1 = String.format(message1, 19.99);
System.out.println(price1);
String price2 = String.format(message1, 9.99);
System.out.println(price2);

String message2 = "The number is %5d"; // %5d 5 for 5 spaces and d for int
String number1 = String.format(message2, 5);
String number2 = String.format(message2, 15);
System.out.println(number1);
System.out.println(number2);

String message3 = "My name is %3$s, I am %2$d years old, I live in %1$s.";  //%2$ reresent 2nd argument will be inserted here
String result = String.format(message3, "NewYork", 25, "Nitin");
System.out.println(result);

```

**Text Block**  This will print like with new line tab etc. and we don’t need to format it. It uses 3 quotes “””   “””

```java
String textBlock = """
```

`<html>`

`<body>`

`<p> Hello World. </p>`

`</body>`

`</html>`

```java
""";
System.out.println(textBlock);

```

**String vs String buffer vs Builder**

String is a final class. All the fields as final except “private int hash”. This field contains the hashCode() function value. The hashcode value is calculated only when the hashCode() method is called for the first time and then cached in this field. Furthermore, the hash is generated using the final fields of String class with some calculations. So every time the hashCode() method is called, it will result in the same output. For the caller, it seems like calculations are happening every time but internally it’s cached in the hash field.

| String | String buffer | StringBuilder |
| --- | --- | --- |
| Immutable | Mutable | Mutable |
| Slow for concat operations | Fast for concat operations | Fastest for concat |
| String constant pool. | Uses heap |  |
| Thread safe | synchronized i.e. thread safe | non-synchronized i.e. not thread safe |

```java
int count = 10_000;

String st = "Hello";
StringBuilder sb = new StringBuilder("Hello");
StringBuffer sf = new StringBuffer("Hello");

Long l1 = System.currentTimeMillis();
for (int i = 0; i < count; i++) {
st = st + i;
}
Long l2 = System.currentTimeMillis();
System.out.println("Time taken " + (l2 - l1));

l1 = System.currentTimeMillis();
for (int i = 0; i < count; i++) {
sb = sb.append(i);
}
l2 = System.currentTimeMillis();
System.out.println("Time taken " + (l2 - l1));

l1 = System.currentTimeMillis();
for (int i = 0; i < count; i++) {
sf = sf.append(i);
}
l2 = System.currentTimeMillis();
System.out.println("Time taken " + (l2 - l1));

```
