package it.polimi.ingsw.model.gameboard.startMenu;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {

    private Text name;

    public MenuButton(String name){

        //Button write
        this.name = new Text(name);
        this.name.setFont(this.name.getFont().font(20));
        this.name.setFill(Color.WHITE);

        //Rectangle who contains the button
        Rectangle bg = new Rectangle(450, 60);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur(3.5));

        //Alignment of button
        setAlignment(Pos.CENTER_LEFT);
        setRotate(-0.5);
        getChildren().addAll(bg, this.name);

        //Mouse events when passing on the button
        this.setOnMouseEntered(e -> {
            bg.setTranslateX(10);
            bg.setFill(Color.WHITE);
            this.name.setTranslateX(10);
            this.name.setFill(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            bg.setTranslateX(0);
            bg.setFill(Color.BLACK);
            this.name.setTranslateX(0);
            this.name.setFill(Color.WHITE);
        });

        //Mouse events when pressed
        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());
        setOnMousePressed(e -> setEffect(drop));
        setOnMouseReleased(e -> setEffect(null));
    }

}
