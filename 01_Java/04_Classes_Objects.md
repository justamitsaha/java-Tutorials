# Classes Objects

Java depends on classes to run program. Anything and everything in java must be defined in a class. For e.g., if we need to calculate area of box. We need to define a class of Box, create an object, and then calculate Area.

Class is a representation of real-life entity in Java.

```java
public class Test {

public static void main(String[] args) {
Box b1 = new Box();
b1.height = 6;
b1.length = 5;
b1.width = 4;
System.out.println(b1.area());
}
}

class Box {
int height, width, length;

int area() {
return this.height * this.length * this.width;
}
}

```

A class defines a new type of data. When you create a class, you are creating a new data type. You can use this type to declare objects or instance of that type. For e.g. Person can be a class and Amit an instance or object of Person.

A class is a template for an object, and an object is an instance of a class.

Variables defined within a class are called instance variables because each instance of the class (that is, each object of the class) contains its own copy of these variables.
