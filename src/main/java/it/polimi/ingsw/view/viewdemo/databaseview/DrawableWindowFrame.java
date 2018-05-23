package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;
import it.polimi.ingsw.model.gameboard.windowframes.FileWindowPatternGenerator;
import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DrawableWindowFrame extends Parent implements GuiItem {

    private GridPane container;                 //TODO:it will be dimensioned to have square formed canvas into it
    private WindowFrame windowFrame;

    public DrawableWindowFrame(int row, int column, GridPane gridPane, String path) {
        windowFrame = new PaperWindowFrame(new FileWindowPatternGenerator(path));
        container = new ConstructorHelper().setWindowFrameElement(row, column, gridPane);
        windowFrameDrawer();
        getChildren().addAll(container);
    }

    private void windowFrameDrawer(){
        Map<Coordinate, Color> colorConstraints = windowFrame.getColorConstraints();
        Map<Coordinate, Shade> shadeConstraints = windowFrame.getShadeConstraints();
        for(int i = 0; i < Parameters.MAX_ROWS; i++){
            for(int j = 0; j < Parameters.MAX_COLUMNS; j++){
                if(colorConstraints.get(new Coordinate(i, j)) != null) {
                    //TODO: add button on every single square, and fill it with the image
                    ImageView img = loadImg(GUIParameters.COLOR_SHADE_IMAGE_PATH + colorConstraints.get(new Coordinate(i, j)).getLabel() + ".png");
                    container.add(img, i, j);
                    img.autosize();
                }
                else if(shadeConstraints.get(new Coordinate(i, j)) != null) {
                    /*ImageView img = loadImg(GUIParameters.COLOR_SHADE_IMAGE_PATH + shadeConstraints.get(new Coordinate(i, j)).getValue() + ".png");
                    container.add(img, i, j);
                    img.autosize();*/
                 }
                else {
                    ImageView img = loadImg(GUIParameters.DEFAULT_IMAGE_PATH);
                    container.add(img, i, j);
                    img.autosize();
                }
            }
        }
    }
    private ImageView loadImg(String path){
        ImageView img = null;
        try(InputStream is = Files.newInputStream(Paths.get(path))) {
            img = new ImageView(new Image(is));
        }
        catch (IOException e){
        }

        if (img == null)
            throw new NullPointerException("Image from " + path + " null");
        return img;

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
