package it.polimi.ingsw.view.gamemenu;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {

    private Text text;
    private Rectangle rectangle;
    private DropShadow drop;
    private OverlayWrite overlayWrite;

    public MenuButton(String name, String writeDescription){
        text = new Text(name);
        rectangle = new Rectangle(450, 60);
        drop = new DropShadow(50, Color.WHITE);
        overlayWrite = new OverlayWrite(writeDescription, 450, 60, Pos.CENTER_LEFT);

        //Button write
        text.setFont(text.getFont().font(20));
        text.setFill(Color.WHITE);

        //Rectangle who contains the button
        rectangle.setOpacity(0.6);
        rectangle.setFill(Color.BLACK);
        rectangle.setEffect(new GaussianBlur(3.5));

        overlayWrite.manageText(20, Color.WHITE);
        overlayWrite.manageRectangle(0.6, Color.BLACK);
        overlayWrite.setTranslateY(300);
        overlayWrite.setVisible(false);

        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(rectangle, text, overlayWrite);

        //Mouse events when passing on the button
        this.setOnMouseEntered(e -> {
            glowAndTranslateOnMousePass();
            //overlayWrite.setVisible(true);
        });
        this.setOnMouseExited(e -> {
            returnNormalOnMouseExit();
            //overlayWrite.setVisible(false);
        });

        //Mouse events when pressing the button
        drop.setInput(new Glow());
        setOnMousePressed(e -> setEffect(drop));
        setOnMouseReleased(e -> setEffect(null));
    }

    private void glowAndTranslateOnMousePass(){
        rectangle.setTranslateX(10);
        rectangle.setFill(Color.WHITE);
        text.setTranslateX(10);
        text.setFill(Color.BLACK);
    }
    private void returnNormalOnMouseExit(){
        rectangle.setTranslateX(0);
        rectangle.setFill(Color.BLACK);
        text.setTranslateX(0);
        text.setFill(Color.WHITE);
    }

}
