# Covariance

Covariance is a concept in object-oriented programming that allows a subclass to override a method in its superclass with a return type that is a subclass of the return type of the superclass method. This enables more flexibility in method overriding and allows for a more specific return type in the subclass.

In this example, the makeSound() method in the Dog class overrides the same method in the Animal class with a more specific return type (Dog).

```java
class Animal {
    Animal makeSound() {
        System.out.println("Some sound");
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    Dog makeSound() {       // This is overriding Parent and returning a subclass
        System.out.println("Woof");
        return new Dog();
    }
}

public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.makeSound();
    }
}

```

But same thing is not allowed in Collections

```java
public static void main(String[] args) {
    String s = "Generics";
    Object o = s;   // Will work

    String[] sArray = {"Generics", "Collections"};
    Object[] objArray = sArray;    // Will work

    List<String> sList = new ArrayList<>();
    List<Object> oList = new ArrayList<>();
    oList = sList;      // This will no compile
}

```

This has some disadvantages as we can see we can add String object in Numbers array. Initially in order to achieve polymorphism this covariance was added hence it is found in Arrays but then when the developers resailed it they did not allowed for Collections.

```java
public static void main(String[] args) {
    Number[] numArray = {1, 2, 3};
    Object[] objectArray = numArray; // Covariances accepted
    objectArray[0] = "String";
}

```
