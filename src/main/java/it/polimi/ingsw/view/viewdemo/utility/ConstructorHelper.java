package it.polimi.ingsw.view.viewdemo.utility;

import javafx.scene.shape.Rectangle;

public class ConstructorHelper {

    public Rectangle setElement(double x, double y, double width, double height) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        return rectangle;
    }

}
