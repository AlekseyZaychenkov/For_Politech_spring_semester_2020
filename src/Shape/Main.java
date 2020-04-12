package Shape;

import Shape.Shapes.Shape;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
	    ShapeFactory shapeFactory = new ShapeFactory();
        ArrayList<Shape> shapeArrayList = new ArrayList<Shape>();

        Shape shape = null;

        for (int i=0; i<10; i++) {
            int numberOfSides = (int)(3*Math.random())+1;
            switch (numberOfSides){
                case (1):  // Circle
                    shape = shapeFactory.createShape((int)(3*Math.random())+1);
                break;
                case (2):  // Rectangle
                    shape = shapeFactory.createShape((int)(10*Math.random()+1), (int)(10*Math.random())+1);
                break;
                case (3):  // Triangle
                    shape = shapeFactory.createShape((int)(20*Math.random())+1, (int)(10*Math.random())+1, (int)(10*Math.random())+1);
                break;
            }
            shapeArrayList.add(shape);
        }

        // searching of the biggest
        double maxPerimeter = 0;
        int numberOfShapeWithMaxPerimeter = 0;
        for (int i=0; i<shapeArrayList.size(); i++) {
            shape = shapeArrayList.get(i);
            if (shape.getPerimeter() > maxPerimeter) {
                numberOfShapeWithMaxPerimeter = i;
                maxPerimeter = shape.getPerimeter();
            }
        }

        System.out.println("A shape with the biggest perimeter/circumference:");
        System.out.println(shapeArrayList.get(numberOfShapeWithMaxPerimeter).toString());
    }
}
