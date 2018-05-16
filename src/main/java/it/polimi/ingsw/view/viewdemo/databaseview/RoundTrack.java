package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;


public class RoundTrack extends Parent implements GuiItem {

    public RoundTrack(int row, int column, GridPane gridPane){
        new ConstructorHelper().setElement(row, column, gridPane, "RoundTrack", 60);
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
