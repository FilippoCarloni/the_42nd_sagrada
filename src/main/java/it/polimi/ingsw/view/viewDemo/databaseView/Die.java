package it.polimi.ingsw.view.viewdemo.databaseview;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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

public class Die extends Parent implements GuiItem {

    private Text name;
    private StackPane stackPane;
    private Rectangle container;
    private Image dieImage;
    private ImageView dieImageView;
    private ImagePattern dieImagePattern;

    public Die(double x, double y, String imgPath){
        ConstructorHelper helper = new ConstructorHelper();

        container = helper.setElement(x, y, GUIParameters.DIE_WIDTH,  GUIParameters.DIE_HEIGHT);
        name = new Text("Dice");
        stackPane = new StackPane();
        /*try {
            loadImg(imgPath);
        } catch (IOException e) {
            System.err.println("Invalid Path");
        }
        dieImagePattern = new ImagePattern(dieImage);
        container.setFill(dieImagePattern);*/

        name.setFont(name.getFont().font(20));
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
            dieImage = new Image(is);
            dieImageView = new ImageView(dieImage);
            dieImageView.autosize();
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
