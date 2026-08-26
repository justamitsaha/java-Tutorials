# Super

We can call parent method from child using super key work. If it is 3 or more level inheritance for e.g. C extending B and B extending A. We can’t call A from C directly.

```java
class Animal {
    void eat() {
        System.out.println("Animal eating");
    }
}

class Dog extends Animal {
    @Override
    void eat() {
        super.eat();
        System.out.println("Dog eating");
    }
}

class Bulldog extends Dog {
    @Override
    void eat() {
        super.eat();
        System.out.println("Bulldog eating");
    }
}

public class Test {
    public static void main(String[] args) {
        new Dog().eat();
        new Bulldog().eat();
    }
}

```
