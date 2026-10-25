import java.util.ArrayList;
import java.util.List;

public class Test {

    // Generic method
    public <T> boolean isEqual(T a, T b) {
        return a.equals(b);
    }

    // Generic constructor
    public <T> Test(T value) {
        System.out.println("Constructor called with: " + value);
    }

    public static void main(String[] args) {
        // Generic constructor: accepts different types
        Test obj1 = new Test("Hello");
        Test obj2 = new Test(100);

        // Generic method: works with different types
        Test util = new Test("Utility");

        System.out.println(util.isEqual("Hello", "Hello")); // true
        System.out.println(util.isEqual(10, 20));           // false

        // Java can infer a common supertype for both arguments, such as Object hence this will compile and return false
        System.out.println(util.isEqual("Hello", 10));
    }
}


