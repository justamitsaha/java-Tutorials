# Upcasting Down casting

Upcasting when subclass references super class. Here point to notice is that the object is created using new Toyota but since it is referenced as a Car class object, so it can’t access the repair method present in Toyota class as JVM think it is or Car type and Car doesn’t have a repair method

```java
public class Test {
    public static void main(String[] args) {
        Car toyota = new Toyota();
        toyota.start();
	   //toyota.repair(); //JVM won't compile
    }

}

class Car {
    public void start(){
        System.out.println("Car vroom vroom");
    }
}

class Toyota extends Car {
    @Override
    public void start(){
        System.out.println("Toyota vroom vroom");
    }

    public void repair(){
        System.out.println("Repairing toyota");
    }
}

```

**Down casting**  When super class refers sub class then we use down casting here we need to explicitly do the casting

```java
public class Test {
    public static void main(String[] args) {
        Car toyota = new Toyota();
        toyota.start();
        //toyota.repair(); //JVM won't compile 
        Toyota toyota1 = (Toyota) toyota;
        toyota1.start();
        toyota1.repair();
    }
}

```

The underlying object toyota is of type Toyota but it refers to Car. So, when we try to re-assign to toyota1 we have to do explicit casting. The casting will work since the underlying object is of type Toyota

Directly assigning dog to Animal like below won’t work. The code will compile but will throw **ClassCastException** exception. Since the underlying object created with new is of type Animal

```java
Toyota an = (Toyota)new Car();

```

We can prevent such issues with instance of operators where we can do the actual casting after doing instance of check using instanceof method

```java
public class Test {
    public static void main(String[] args) {
        Car car = new Toyota();
        if(car instanceof Toyota){
            Toyota toyota = (Toyota) car;
        }

        Toyota toyota1 = null;
        if (toyota1 instanceof Toyota){
            toyota1 = (Toyota)new Car();
        }
    }
}

```

The instance of works only for inheritance relationship i.e. the 2 Class getting compared must have parent child relationship. We can’t use is for any random class. This is useful if we want to create a common solution. Say we want to create a common method for different animals. There we can pass Animal reference as method parameters. Then Dog Cat etc. can call these and we can check the type using instance of method and do the operation, otherwise we will have to write separate methods.

```java
public static void performAction(Car animal) {
    animal.eat();
    if(animal instanceof Dog) {
        Dog dog = (Dog) animal;
        dog.eat();
    } else if (animal instanceof Cat cat) { // New syntax since Java 16 where we can pass object also
        cat.eat();        // And casting is not needed
    }
}

```
