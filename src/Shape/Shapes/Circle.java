package Shape.Shapes;



public class Circle implements Shape {
    int radius;
    double circumference;

    public Circle(int radius) {
        this.radius = radius;
        circumference = Math.PI*radius*radius;
    }

    @Override
    public double getPerimeter() {
        return circumference;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", circumference=" + circumference +
                '}';
    }
}
