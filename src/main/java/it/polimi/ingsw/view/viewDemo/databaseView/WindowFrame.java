package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
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
    private Rectangle container;
    private StackPane stackPane;
    private Image windowImage;
    private ImageView windowFrame;
    private ImagePattern windowImagePattern;


    public WindowFrame(double x, double y, String imgPath) {
        ConstructorHelper helper = new ConstructorHelper();
        container = helper.setElement(x, y, GUIParameters.WINDOW_FRAME_WIDTH, GUIParameters.WINDOW_FRAME_HEIGHT);
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
        name.setTranslateX(x);
        name.setTranslateY(y);

        container.setOpacity(0.6);
        container.setFill(Color.BLACK);
        container.setEffect(new GaussianBlur(3.5));

        stackPane.getChildren().addAll(container, name);
        getChildren().addAll(stackPane);
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
        //Will be write soon
    }
    @Override
    public void removeGlow() {
        //Will be write soon
    }
    @Override
    public void zoom() {
        //Will be write soon
    }
}
