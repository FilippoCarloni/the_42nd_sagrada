package it.polimi.ingsw.view.viewDemo.databaseView;

import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WindowFrame extends Parent implements GuiItem {

    private Text name;
    private StackPane stackPane;
    private Image windowImage;
    private ImageView windowFrame;
    private ImagePattern windowImagePattern;
    private Rectangle container;


    public WindowFrame(double X, double Y, String imgPath) {
        container = setWindowFrame(X, Y);
        name = new Text("WindowFrame");
        stackPane = new StackPane();

        /*try {
            loadImg(imgPath);
        } catch (IOException e) {
            System.err.println("Invalid Path");
        }
        windowImagePattern = new ImagePattern(windowImage);
        container.setFill(windowImagePattern);*/

        name.setFont(name.getFont().font(30));
        name.setFill(Color.WHITE);
        name.setTranslateX(X);
        name.setTranslateY(Y);

        container.setOpacity(0.6);
        container.setFill(Color.BLACK);
        container.setEffect(new GaussianBlur(3.5));

        stackPane.getChildren().addAll(container, name);
        getChildren().addAll(stackPane);
    }

    private Rectangle setWindowFrame(double X, double Y) {
        Rectangle container = new Rectangle(400, 200);
        container.setTranslateX(X);
        container.setTranslateY(Y);
        return container;
    }

    @Override
    public void loadImg(String path) throws IOException {
        try(InputStream is = Files.newInputStream(Paths.get(path))){
            windowImage = new Image(is);
            windowFrame = new ImageView(windowImage);
            windowFrame.autosize();
        }
    }
    @Override
    public void setGlow() {

    }
    @Override
    public void removeGlow() {

    }
    @Override
    public void zoom() {

    }
}