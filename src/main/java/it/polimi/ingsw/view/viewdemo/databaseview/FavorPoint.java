package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * They will be a bit different: they will stay on window frames, so they will need a rectangle on windowframe rectangle
 */

public class FavorPoint extends Parent implements GuiItem {

    private StackPane container;

    public FavorPoint(int row, int column, GridPane gridPane){
        ConstructorHelper helper = new ConstructorHelper();
        container = helper.setElement(row, column, gridPane, "FavorPoints", 8);
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
