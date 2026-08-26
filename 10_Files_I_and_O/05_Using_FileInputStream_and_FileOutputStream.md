# Using FileInputStream and FileOutputStream

File fileIn = new File("in.txt");

File fileOut = new File("out.txt");

try (var in = new FileInputStream(fileIn);

var out = new FileOutputStream(fileOut)) {

```java
int data;
while ((data = in.read()) != -1) {
```

out.write(data);

}

} catch (IOException e) {

e.printStackTrace();

}

The FileOutputStream class includes overloaded constructors that take a boolean append flag. When set to true, the output stream will append to the end of a file if it already exists

var out = new FileOutputStream(fileOut, true);
