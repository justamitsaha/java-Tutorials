# Diamond Problem

**Diamond problem with interface**: Interface unlike inheritance allows multiple implements. So, it can face diamond problems

- Scenario1 both methods are abstract, compilers won’t get confused regarding which method to override. The best approach is to use override annotation

```java
interface Vehicle{
    void startEngine();

}

interface Car {
    void startEngine();
}

class Toyota implements  Car , Vehicle{
    @Override
    public void startEngine() {
    }
}

```

- Scenario 2 one abstract one default

```java
interface Vehicle{
    void startEngine();

}

interface Car {
    default void startEngine(){
        System.out.println("Vroom Vroom");
    }
}

```

In this scenario the compiler will get confused regarding which method to use. The solution is to override the method and then either call parent method or provide new solution

```java
class Toyota implements  Car , Vehicle{
    @Override
    public void startEngine() {
        Car.super.startEngine();
    }
}

```

- Scenario 3 both interface have default

```java
interface Vehicle {
    default void startEngine() {
        System.out.println("Pee Pee");
    }
}

interface Car {
    default void startEngine() {
        System.out.println("Vroom Vroom");
    }
}

```

In this case  if a class implements only one interface it’s ok it can live with default implementation.

```java
class Toyota implements Car {
}

```

But if it implements both Car and Vehicle. It must override the method because otherwise compiler will get confused, and it will not compile.

```java
class Toyota implements Car, Vehicle{
    @Override
    public void startEngine() {
        Car.super.startEngine();
    }
}

```

- If one of them is class and another interface In this case the compiler will give preference to Class as inheritance provides implementation and interface is for specification. So Toyota will have  the method from Class Car

```java
interface Vehicle {
    default void startEngine() {
        System.out.println("Pee Pee");
    }
}

class Car {
    // Below method has to be public otherwise below implementation won't compile
    public void startEngine() {
        System.out.println("Vroom Vroom");
    }
}

class Toyota extends Car implements Vehicle  {

}

```

> 💡 **Note:** ** Note any abstract, static or default method in interface is by default public so if we have  a class that also has the same abstract method, that should also be public. Say if the class has default modifier, then the implementation class will inherit the same. This is invalid relation with respect to interface which has the abstract method marked as public by default,
