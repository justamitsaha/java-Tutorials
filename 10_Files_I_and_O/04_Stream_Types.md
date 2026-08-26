# Stream Types

### Byte vs. Character Streams

Stream can be classified as

| Byte Stream | Character streams |
| --- | --- |
| Which read/write binary data ( 0s and 1s) | Which read/write text data |
| have class names that end in InputStream or OutputStream | have class names that end in Reader or Writer |
| The byte streams are primarily used to work with binary data, such as an image or executable file | character streams are used to work with text files |
| FileInputStream | FileReader |

Even though character streams do not contain the word Stream in their class name, they are still I/O streams

Byte stream classes can write all types of binary data, including strings. So one might think that Character streams like reader are not necessary. However  Character streams specifically focused on managing character and string data so that one doesn’t  worry about the underlying character encoding of the file.

Java supports a wide variety of character encodings

Charset usAsciiCharset = Charset.forName("US-ASCII");

Charset utf8Charset = Charset.forName("UTF-8");

Charset utf16Charset = Charset.forName("UTF-16");

### Input VS Output Streams

Most InputStream stream classes have a corresponding OutputStream class, and vice versa. For example, the FileOutputStream class writes data that can be read by a FileInputStream. If you understand the features of a particular Input or Output stream class, you should naturally know what its complementary class does

It follows, then, that most Reader classes have a corresponding Writer class. For example, the FileWriter class writes data that can be read by a FileReader

Exception

**PrintWriter** no reader  **PrintStream** has no Input stream

### Low vs High level Stream

A low‐level stream connects directly with the source of the data, such as a file, an array, or a String. Low‐level streams process the raw data or resource and are accessed in a direct and unfiltered manner

A high‐level stream is built on top of another stream using wrapping. Wrapping is the process by which an instance is passed to the constructor of another class

try (var reader = new BufferedReader(new FileReader("text.txt"))) {

```java
System.out.println(reader.readLine());
```

} catch (IOException e) {

e.printStackTrace();

}

In this example, FileReader is the low‐level stream reader, whereas BufferedReader is the high‐level stream that takes a FileReader as input. Many operations on the high‐level stream pass through as operations to the underlying low‐level stream, such as read() or close(). Other operations override or add new functionality to the low‐level stream methods. The high‐level stream may add new methods, such as readLine(), as well as performance enhancements for reading and filtering the low‐level data.

High‐level streams can take other high‐level streams as input

try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("text.txt")))) {

```java
System.out.print(ois.readObject());
```

} catch (ClassNotFoundException e) {

e.printStackTrace();

} catch (IOException e) {

e.printStackTrace();

}

Buffered classes read or write data in groups, rather than a single byte or character at a time. The performance gain from using a Buffered class to access a low‐level file stream cannot be overstated. Unless you are doing something very specialized in your application, you should always wrap a file stream with a Buffered class in practice.

The java.io library defines four abstract classes that are the parents of all stream classes defined within the API: **InputStream, OutputStream, Reader, and Writer**. The constructors of high‐level streams often take a reference to the abstract class. For example, BufferedWriter takes a Writer object as input, which allows it to take any subclass of Writer.

InputStream (Abstract) --> FileInputStream --> BufferedInputStream

OutputStream(Abstract) --> FileOutputStream--> BufferedOutputStream

Reader(Abstract)--> FileReader --> BufferedReader

Writer(Abstract)-->FileWriter -->BufferedWriter

new BufferedInputStream(new FileReader("z.txt")); *// DOES NOT COMPILE Reader wrapped in Input Stream*

new BufferedWriter(new FileOutputStream("z.txt")); *// DOES NOT COMPILE Same*

new ObjectInputStream( new FileOutputStream("z.txt")); *// DOES NOT COMPILE output wrapped in input*

### Summary

| Class Name | Type | Description |
| --- | --- | --- |
| InputStream | Abstract | Abstract class for all input byte streams |
| OutputStream | Abstract | Abstract class for all output byte streams |
| Reader | Abstract | Abstract class for all input character streams |
| Writer | Abstract | Abstract class for all output character streams |
| FileInputStream | Low | Reads file data as bytes |
| FileOutputStream | Low | Writes file data as bytes |
| FileReader | Low | Read file data as character |
| FileWriter | Low | Write file data as character |
| BufferedInputStream | High | Reads byte data from an existing InputStream in a buffered manner, which improves efficiency and performance |
| BufferedOutputStream | High | Writes byte data from an existing OutputStream in a buffered manner, which improves efficiency and performance |
| BufferedReader | High | Reads character data from an existing Reader in a buffered manner, which improves efficiency and performance |
| BufferedWriter | High | Writes character data to an existing Writer in a buffered manner, which <br>improves efficiency and performance |
| ObjectInputStream | High | Deserializes primitive Java data types and graphs of Java objects from an existing InputStream |
| ObjectOutputStream | HIgh | Serializes primitive Java data types and graphs of Java objects to an existing OutputStream |
| PrintStream | High | Writes formatted representations of Java objects to a binary stream |
| PrintWriter | High | Writes formatted representations of Java objects to a character stream |
