package Shape;

import Shape.Shapes.Circle;
import Shape.Shapes.Rectangle;
import Shape.Shapes.Shape;
import Shape.Shapes.Triangle;

public class ShapeFactory {
    public Shape createShape(int radius){
        return new Circle(radius);
    }

    public Shape createShape(int sideA, int sideB){
        return new Rectangle(sideA, sideB);
    }

    public Shape createShape(int sideA, int sideB, int sideC){
        return new Triangle(sideA, sideB, sideC);
    }
}
