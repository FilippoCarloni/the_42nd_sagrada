package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class FavorPoint extends Parent implements GuiItem {

    public FavorPoint(int row, int column, GridPane gridPane){
        new ConstructorHelper().setElement(row, column, gridPane, "FavorPoints", 8);
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
