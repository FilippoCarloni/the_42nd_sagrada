package it.polimi.ingsw.view.gamemenu;

import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class OverlayWrite extends StackPane {

    private Text text;
    private Rectangle rectangle;

    public OverlayWrite(String name, double width, double height, Pos position){

        text = new Text(name);
        rectangle = new Rectangle(width, height);

        setAlignment(position);
        getChildren().addAll(rectangle, text);
    }

    public void manageText(int font, Color color){
        text.setFont(text.getFont().font(font));
        text.setFill(color);
    }
    public void manageRectangle(double opacity, Color color){
        rectangle.setOpacity(opacity);
        rectangle.setFill(color);
        rectangle.setEffect(new GaussianBlur(3.5));
    }
}
