# Final

A field can be declared as final. Doing so prevents its contents from being modified, making it, essentially, a constant. This means that you must initialize a final field when it is declared. You can do this in one of two ways: First, you can give it a value when it is declared. Second, you can assign it a value within a constructor.

```java
class Box implements Cloneable {
    int height, breath, length;
    final String colour;        // Won't compile if not initialized

    Box(String colour) {
        this.colour = colour;
    }
}

```

Effects of Final

> 💡 **Note:** Class  other class can’t extend

```java
final class Car {
    public void start() {
        System.out.println("Car vroom vroom");
    }
}

class Toyota extends Car {      // Won't compile
    @Override
    public void start() {
        System.out.println("Toyota vroom vroom");
    }

    public void repair() {
        System.out.println("Repairing toyota");
    }
}

```

> 💡 **Note:** Variable  Can’t change value

> 💡 **Note:** Method  Can’t be overwritten
