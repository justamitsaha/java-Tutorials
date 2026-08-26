# Interface

Interface provides a way to define a contract that a class must follow to implement a certain set of behaviors. So, inside an Interface we can define all abstract methods without worrying about the actual implementation logic. All the classes who are trying to implement these Interface, need to adhere to the contract or the specifications that we have defined with the help of abstract methods.

In simple words, an Interface is always going to provide

- Method signature

- return types

- parameters

that the implementation classes must follow.

With the interface we can provide a standard for our development activity, without this so that each individual developer would follow its own coding standard leading to problems in maintenance of code, difficulty in future enhancements. A great example of interface implementation is JPA, spring security which follows a proper standard , hence when any version is released it is easy to use and understand for developers.

An interface can contain

- Static final  fields   All field variables are public static and final by default even if we don’t mention it;

- Abstract methods  No Implementation abstract keyword is optional, can only be public

- Default methods   We can provide some default logic which implementing classes can override

- Private methods  Some specific logic not available to implementing or other classes

- Static methods   utility method

```java
interface Animal {
    int heart =1;   //its static public final even if not mentioned

    void walk();  //abstract key word optional

    default void hunt(){  //Implementing class can choose to override
        jump();
        System.out.println("hunting");
    }

    private void jump(){
        eat();
        System.out.println("jumping");
    }

    static void eat(){
        System.out.println();
    }
}

```

The abstract, default and static methods in interface are by default are public. Normally if no modifier is mentioned it is considered as default but for interface its public. We can make separate private methods but abstract, default and static methods are mandated and defaulted to the public.

Since abstract methods by default are public any Class overriding the method must be also be declared as public since it can’t have more restrictive access modifier

One interface can inherit from another interface, in this case we use extends keyword instead of implement keyword as interface won’t have any implementation.

```java
interface Vehicle{
    void go();

}

interface Car extends Vehicle{

}

```

We can’t create a instance of interface since it doesn’t have any implementation.

- Only classes can implement an interface, and it has to provide the implementation of all the abstract methods.

- If the implementing class does not provide any implementation, then that class also should be abstract.

- Variables declared in interface are always public, final, static. However, if the interface itself is not public then say it is default, then other classes outside the package won’t be able to see the member variable.

```java
public class Test {
    public static void main(String[] args) {
        System.out.println(Vehicle.TYRES_COUNT);
    }
}

interface Vehicle{
    int TYRES_COUNT = 4;
    void go();

}

```

After Java 8 Lambda and lots of other features were added then a lot of existing interfaces were modified, and new method signatures were added. This would disrupt all the existing implementations as the new methods had to be implemented by existing implementations. To resolve this default methods were introduced. So, implementing classes can ignore the default, use the default or can override it.

```java
interface Car {
    default void startEngine(){
        System.out.println("VROOM VROOM");
    }
}

class Toyota implements  Car{
    @Override
    public void startEngine(){
        System.out.println("VROOM VROOM VROOM");
    }
}

```

Since we have implementation logic in default methods hence private methods were introduced to add the common logic at one place in private methods

Similarly static methods were introduced to add utility functions
