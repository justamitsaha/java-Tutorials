# Inheritance Overview

In Java a class can inherit from another class, like a parent child relation, where a child will have access to all the parent variables and methods except private ones.

```java
public class Test {
    public static void main(String[] args) {
        Toyota toyota = new Toyota();
        toyota.start();
    }

}

class Car {
    private String name;        // Child Toyota can't access this

    public void start(){        // Child can access this 
        System.out.println("Vroom vroom");
    }
}

class Toyota extends Car {
}

```

All classes in Java directly or indirectly extend Object class. It is the reason below methods like toString(), hashCode() are available to Car class even though it hasn’t defined it, because these are present in parent Object class.

One class can inherit only from one class however multi-level inheritance is allowed which means Toyota extends Car and Car extends object, So Toyota class will have access to all Object methods.

When child classes are called parent classes are also loaded. If a constructor does not explicitly invoke a superclass constructor, the Java compiler automatically inserts a call to the no-argument constructor of the superclass. In below example when new Toyota () is done Car constructor is also invoked and if we print the memory location then we see that both are at same memory location

```java
public class Test {
    public static void main(String[] args) {
        Toyota toyota = new Toyota();
    }

}

class Car {
    public Car() {
        System.out.println("Car constructor " + this);
    }
}

class Toyota extends Car {
    Toyota() {
        System.out.println("Toyota Constructor " + this);
    }
}

```

Car constructor Toyota@43d7741f

Toyota Constructor Toyota@43d7741f

> 💡 **Note:** **If super class doesn’t have default constructor. Sub class will look to call the default constructor,** and it will fail

```java
class Car {
    int wheel;

    public Car(int wheel) {
        this.wheel = wheel;
        System.out.println("Car constructor " + this);
    }
}

class Toyota extends Car {
    Toyota(int wheel) {                 //compilation problem
        System.out.println("Toyota Constructor " + this);
    }
}

```

So, the solution is either have no argument constructor

```java
class Car {
    int wheel;
    Car(){}     // will solve the compilation problem
    public Car(int wheel) {
        this.wheel = wheel;
        System.out.println("Car constructor " + this);
    }
}

class Toyota extends Car {
    Toyota(int wheel) {                 // will Compile
        System.out.println("Toyota Constructor " + this);
    }
}

```

Or explicitly call the super constructor

```java
class Car {
    int wheel;
    public Car(int wheel) {
        this.wheel = wheel;
        System.out.println("Car constructor " + this);
    }
}

class Toyota extends Car {
    Toyota(int wheel) {
        super(wheel);           // will Compile since we are explicitly calling
        System.out.println("Toyota Constructor " + this);
    }
}

```

> 💡 **Note:** Say 2 classes are put in same folder without any package structure. It may seem like since they are in same folder, we can keep default access level, but it won’t work since they are not in same package and will give below exception as Toyota class can’t see the constructor.

Exception in thread "main" java.lang.IllegalAccessError: class Toyota tried to access method 'void Car.<init>()' (Toyota is in unnamed module of

loader com.sun.tools.javac.launcher.Main$MemoryClassLoader @543e710e; Car is in unnamed module of loader 'app')

at Toyota.<init>(Toyota.java:2)

at Toyota.main(Toyota.java:7)
