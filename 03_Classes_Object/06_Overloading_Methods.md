# Overloading Methods

it is possible to define two or more methods within the same class that share the same name, as long as their parameter declarations are different. When this is the case, the methods are said to be overloaded, and the process is referred to as method overloading. When an overloaded method is invoked, Java uses the type and/or number of arguments as its guide to determine which version of the overloaded method to call.

```java
class Box {
    int height, breath, length;

    public int volume(int height, int breath, int length) {
        return height * breath * length;
    }

    public int volume(int height, int breath) {
        return height * breath * this.length;
    }
}

```

Thus, overloaded methods must differ in the type and/or number of their parameters. While overloaded methods may have different return types, the return type alone is insufficient to distinguish two versions of a method. When Java encounters a call to an overloaded method, it simply executes the version of the method whose parameters match the arguments used in the call.

So below will not work.

```java
class Box {
    int height, breath, length;
    
    //Will not compile
    public int volume(int height, int breath, int length) {
        return height * breath * length;
    }

    public String volume(int height, int breath, int length) {
        return (height * breath * length) +"Area";
    }
}

```

When an overloaded method is called, Java looks for a match between the arguments used to call the method and the method’s parameters. However, this match need not always be exact. In some cases, Java’s automatic type conversions can play a role in overload resolution.

```java
public class Test {

    void test (){}

    void test (double d){}
    public static void main(String[] args) {
        int i = 5;
        Test tst = new Test();
        tst.test(i);
    }
}

```

Method overloading supports polymorphism because it is one way that Java implements the “one interface, multiple methods” paradigm. The value of overloading is that it allows related methods to be accessed by use of a common name. Thus, the function name represents the general action that is being performed. It is left to the compiler to choose the right specific version for a particular circumstance. You, the programmer, need only remember the general operation being performed.
