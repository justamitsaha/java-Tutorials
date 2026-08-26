# Sealed

Inside Java 17, the Sealed classes and interfaces are introduced. With the help of a Sealed class, we can permit which subclass can inherit a superclass.

Syntax

- Class should use the key word sealed. When we only mention sealed any class which is final, sealed or non sealed can extend

```java
sealed class Animal{
}

final class Dog extends Animal {
}

non-sealed class Insect extends Animal {
}

class Elephant extends Animal{      //can’t extend won’t compile
}

```

- If we want specific class to be allowed need to mention keyword permits along with sealed

```java
sealed class Animal permits Dog, Insect, Paws{
}

final class Dog extends Animal {
}

non-sealed class Insect extends Animal {
}

sealed class Paws extends Animal permits Lion{

}

non-sealed class Lion extends Paws {

}

class Elephant extends Animal{      //Can't extend Animal as not permitted
}

```

- Class extending sealed class must be final, sealed or non-sealed
