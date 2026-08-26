public class Test {
    public static void main(String[] args) {
        Box b1 = new Box(1, 1, 1); // Points to location in memory}
    }
}
class Box {
    int height, width, length;

    int area() {
        return this.height * this.length * this.width;
    }

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
}