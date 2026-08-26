# This keyword

Sometimes a method will need to refer to the object that invoked it. To allow this, Java defines the this keyword. this can be used inside any method to refer to the current object

```java
Box(int height, int breath, int length) {
    this.height = height;
    this.breath = breath;
    this.length = length;
}
```

This key word can only be used in non static context like non static block or method not in static block or method

```java
public class Test {

    int i;

    {
        this.i =3; //will compile
    }

    static {
        this.i =3; //won't compile
    }

    public static void main(String[] args) {
        this.i =3; //won't compile
    }
}

```
