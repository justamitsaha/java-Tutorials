# Overriding

Overriding is a feature that allows a subclass or child class to provide a specific implementation of a method that is already provided by one of its super-classes or parent classes, so that when the method is invoked using child class reference the child implementation is getting called instead of parent implementation. We can’t override private, static or final methods.

```java
class Animal{
    public void eat(){
        System.out.println("Animal eating");
    }
}

class Dog extends Animal{
    @Override       // will enforce that the signature is same
    public void eat(){
        System.out.println("Dog eating");
    }
}

```

Below are the rule of overriding is that can be enforced by @Override annotation

- Only methods inherited from parent can be overwritten.

- The method signature must be same i.e. same name and parameters.

- The return type should be either same of the child method return type should be sub class of parent return type or same.

- Method should not be private, static or final.

- The access modifier can’t be more restrictive, for e.g. if the super class method is protected then subclass can be public,

It can’t be default or private.

Private (most restrictive) -> default ->protected -> public (least restrictive)

- Child method must not replace or add any new exception. It can add or replace subclass exception.

- Say parent method throws IOException,

- Child can remove the exception in its implementation completely like Elephant class below

```java
class Animal{
    void eat() throws IOException {
        System.out.println("Animal eating");
    }
}

class Elephant extends Animal{
    @Override
    protected void eat() {      // No Exception will compile
        System.out.println("Elephant eating");
    }
}

```

- Child can use same IOException like Dog class below

```java
class Animal{
    void eat() throws IOException {
        System.out.println("Animal eating");
    }
}

class Dog extends Animal{
    @Override
    protected void eat() throws IOException {   //Same exception will compile
        System.out.println("Dog eating");
    }
}

```

- Child can throw exception which is child of paret exception like FileNotFoundException which is child of IOException

```java
class Animal{
    void eat() throws IOException {
        System.out.println("Animal eating");
    }
}

class Sparrow extends Animal{
    @Override
    protected void eat() throws FileNotFoundException { //subclass will compile
        System.out.println("Sparrow eating");
    }
}

```

- Parent exception will not compile

```java
class Animal{
    void eat() throws IOException {
        System.out.println("Animal eating");
    }
}

class Cat extends Animal{
    @Override
    protected void eat() throws Exception {  //Adding parent exception will not compile
        System.out.println("Cat eating");
    }
}

```

- Child can’t replace or add new exception like SQLException which maintains no hierarchy with IOException

```java
class Animal{
    void eat() throws IOException {
        System.out.println("Animal eating");
    }
}

class Lion extends Animal{
    @Override
    protected void eat() throws SQLException { // Unrelated exception will not compile
        System.out.println("Lion eating");
    }
}

```
