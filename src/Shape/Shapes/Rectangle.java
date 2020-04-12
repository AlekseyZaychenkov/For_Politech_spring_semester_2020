package Shape.Shapes;


public class Rectangle implements Shape {
    int sideA;
    int sideB;
    double perimeter;

    public Rectangle(int sideA, int sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
        perimeter = 2*(sideA+sideB);
    }

    @Override
    public double getPerimeter() {
        return perimeter;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "sideA=" + sideA +
                ", sideB=" + sideB +
                ", perimeter=" + perimeter +
                '}';
    }
}
