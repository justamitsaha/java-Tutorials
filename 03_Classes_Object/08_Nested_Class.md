# Nested Class

It is possible to define a class within another class; such classes are known as nested classes. The scope of a nested class is bounded by the scope of its enclosing class. Thus, if class NestedClass is defined within class Outer, then NestedClass does not exist independently of Outer.

A nested class has access to the members, including private members, of the class in which it is nested. However, the enclosing class does not have access to the members of the nested class.

So Nested Class can access private members of Main but not reverse.

There are two types of nested classes:

- **static -** A static nested class is one that has the static modifier applied. Because it is static, it must access the non-static members of its enclosing class through an object. That is, it cannot refer to non-static members of its enclosing class directly. Because of this restriction, static nested classes are seldom used.

- **non-static (or Inner Class)** - An inner class is a non-static nested class. It has access to all the variables and methods of its outer class and may refer to them directly in the same way that other non-static members of the outer class do.

| Nested class not assessable from other directly. It must be accessed via parent class like Test2.NestedClass, we can do it only because it is static | Is assessable from outer class, which can also access its private members |
| --- | --- |
| public class Test {<br>    public static void main(String[] args) {<br>        Test2.NestedClass nc = new Test2.NestedClass();<br>        System.out.println(nc.i);<br>    }<br><br>} | public class Test2{<br>    public void method(){<br>        NestedClass nc = new NestedClass();<br>        nc.i++;<br>        nc.j++;<br>    }<br>    static class NestedClass {<br>        int i;<br>        private int j;<br>    }<br>} |

Inner class can be initialized in instance methods but not in main method or any other static methods of Outer class.

```java
public class Test2 {
    private int j;

    public void outerTest(){
        NestedClass nc = new NestedClass(); //Will compile
        nc.test();
    }

    public static void main(String[] args) {
        NestedClass nc = new NestedClass(); //Won't compile
    }

    public static void test2(){
        NestedClass nc = new NestedClass(); //Won't compile
    }

    public class NestedClass {
        public void test() {
            System.out.println(j);
        }
    }
}

```
