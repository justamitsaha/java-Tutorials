public class Test {
    public static void main(String[] args) {
        Box b1 = new Box(1, 1, 1);
        Box b2 = b1;
        b1.length = 2;
        System.out.println("b2.length: " + b2.length);

        Box b3 = new Box(3, 3, 3);
        b2 = b3;
        System.out.println("b1.length: " + b1.length);

        b3 = null;
        System.out.println("b2.length: " + b2.length);
    }
}

class Box {
    int height;
    int width;
    int length;

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }

    int area() {
        return this.height * this.length * this.width;
    }
}