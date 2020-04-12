package Shape.Shapes;

public class Triangle implements Shape {
    int sideA;
    int sideB;
    int sideC;
    double perimeter;

    public Triangle(int sideA, int sideB, int sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        perimeter = sideA+sideB+sideC;
    }

    @Override
    public double getPerimeter() {
        return perimeter;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "sideA=" + sideA +
                ", sideB=" + sideB +
                ", sideC=" + sideC +
                ", perimeter=" + perimeter +
                '}';
    }
}
