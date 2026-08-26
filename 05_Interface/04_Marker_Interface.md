# Marker Interface

A **marker interface** is an interface in Java that does not contain any methods, fields, or constants. It is also known as a **tagging interface**. The primary purpose of a marker interface is to provide run-time type information about objects, so the compiler and JVM have additional information about the object. The **Serializable** and **Cloneable** interfaces are the example of marker interface. In short, it indicates a signal or command to the JVM. The declaration of marker interface is the same as interface in Java but the interface must be empty.

Serializable Interface

The **Serializable** interface is a marker interface present in the java.io package. It is used to make an object eligible for saving its state into a file, a process known as **serialization**. Classes that do not implement this interface will not have any of their state serialized or deserialized So in below example if we remove Serializable even if it is not doing anything it will fail

```java
class A implements Serializable {
    int i;
    String s;

    public A(int i, String s) {
        this.i = i;
        this.s = s;
    }
}

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        A a = new A(20, "Amit");

// Serializing 'a'
        FileOutputStream fos = new FileOutputStream("xyz.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(a);

// De-serializing 'a'
        FileInputStream fis = new FileInputStream("xyz.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        A b = (A) ois.readObject();

        System.out.println(b.i + " " + b.s);

// closing streams
        oos.close();
        ois.close();
    }
}

```

Cloneable Interface

The Cloneable interface is present in the java.lang package. It indicates that it is legal for the clone() method to make a field-for-field copy of instances of the class that implements this interface. Invoking the Object.clone() method on an instance of a class that does not implement the Cloneable interface results in a CloneNotSupportedException being thrown

```java
class A implements Cloneable {
    int i;
    String s;

    public A(int i, String s) {
        this.i = i;
        this.s = s;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        A a = new A(20, "GeeksForGeeks");
        A b = (A) a.clone();
        System.out.println(b.i);
        System.out.println(b.s);
    }
}

```

Remote Interface

The **Remote** interface is present in the java.rmi package. It marks an object as remote, meaning it can be accessed from another machine. Any object that is a remote object must directly or indirectly implement this interface

Java also allows us to create our own marker interfaces. For example, we can create a marker interface that indicates whether an object can be removed from the database

```java
interface Deletable {
}

class Entity implements Deletable {
// implementation details
}

public class Test {
    public boolean delete(Object object) {
        if (!(object instanceof Deletable)) {
            return false;
        }
        // delete implementation details
        return true;
    }
}

```

Java provides annotations as an alternative to achieve the same results as marker interfaces. Annotations offer flexible metadata capabilities and can be applied to any class to perform specific actions. However, interfaces allow us to take advantage of polymorphism, which annotations do no
