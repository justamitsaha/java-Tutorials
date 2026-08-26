# Static Dynamic binding

Inside a Java class will have fields and methods, and these methods and fields can be invoked with the help of class name or with the help of object.

When you try to bring inheritance into your Java project, there will be lot many scenarios of casting where Java needs to determine whether parent class methods/variable needs to be called or child class. There won't be any confusion if you have a single class or when you are invoking static, private and final methods and variables.

Some kind of decision must be taken regarding the variables and methods with inheritance. The effort that is going to be put by your Java to determine which method to invoke or to which variable to invoke is called binding. It is of 2 types

Static  During compilation time, can be overridden during run time

Dynamic   During run time

```java
public class Test {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        animal.eat();           //Static binding
        dog.eat();              //Static binding
        Animal animal1 = new Dog();
        animal1.eat();          //First static but during runtime Dynamic binding
    }
}

class Animal {
    public void eat() {
        System.out.println("Animal eating");
    }
}

class Dog extends Animal {
    public void eat() {
        System.out.println("Dog eating");
    }
}

```
