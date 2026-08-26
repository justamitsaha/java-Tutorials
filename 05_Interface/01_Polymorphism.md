# Polymorphism

Polymorphism in Java, a core concept of Object-Oriented Programming (OOP), is achieved primarily through two mechanisms: method overloading and method overriding.

1. Method Overloading (Compile-Time Polymorphism):

Method overloading occurs when a class has multiple methods with the same name but different parameter lists (different number of parameters, different types of parameters, or different order of parameter types).

The compiler determines which overloaded method to call at compile time based on the arguments provided during the method invocation.

This is known as compile-time polymorphism or static polymorphism.

**Example:** A class with multiple add methods, one taking two integers, another taking three integers, and another taking two doubles.

2. Method Overriding (Runtime Polymorphism):

Method overriding occurs when a subclass provides a specific implementation for a method that is already defined in its superclass. The method in the subclass must have the same name, same return type, and same parameter list as the method in the superclass.

The actual method to be executed is determined at runtime based on the type of the object, not the type of the reference variable. This is known as runtime polymorphism or dynamic method dispatch.

**Example:** A Shape class with a draw() method, and Circle and Square subclasses that override the draw() method to provide specific drawing implementations for circles and squares, respectively.

Additionally, polymorphism can also be achieved through interfaces and abstract classes:

**Interfaces:**

When multiple classes implement a common interface, they are required to implement the methods declared in that interface. Objects of these different classes can then be treated uniformly through the interface reference, allowing for polymorphic behavior.

**Abstract Classes:**

Similar to interfaces, abstract classes can define abstract methods that must be implemented by concrete subclasses. This allows for a common interface while providing specific implementations in different subclasses, enabling polymorphism.
