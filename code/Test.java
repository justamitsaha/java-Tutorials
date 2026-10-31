import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Test {

    // Generic constructor
    public <T> Test(T value) {
        System.out.println("Constructor called with: " + value);
    }

    // Generic method: compares two objects of type T
    public <T> boolean isEqual(T a, T b) {
        return a.equals(b);
    }

    public static void main(String[] args) {
        Test t = new Test("Utility");
        new Test(100);

        System.out.println(t.isEqual("Hello", "Hello")); // true
        System.out.println(t.isEqual(10, 20));           // false

        // Common supertype Object is inferred when passing mixed types
        System.out.println(t.isEqual("Hello", 10));      // false
    }
}


