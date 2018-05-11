package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
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

public class ToolCard extends CardItem {

    private Text name;
    private StackPane stackPane;
    private Rectangle container;
    private Image cardImage;
    private ImageView cardImageView;
    private ImagePattern cardImagePattern;

    public ToolCard(double x, double y, String imgPath){
        ConstructorHelper helper = new ConstructorHelper();
        container = helper.setElement(x, y, GUIParameters.CARD_WIDTH, GUIParameters.CARD_HEIGHT);
        name = new Text("ToolCard");
        stackPane = new StackPane();
        /*try {
            loadImg(imgPath);
        } catch (IOException e) {
            System.err.println("Invalid Path");
        }
        cardImagePattern = new ImagePattern(cardImage);
        container.setFill(cardImagePattern);*/
        name.setFont(name.getFont().font(8));
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
            cardImage = new Image(is);
            cardImageView = new ImageView(cardImage);
            cardImageView.autosize();
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
