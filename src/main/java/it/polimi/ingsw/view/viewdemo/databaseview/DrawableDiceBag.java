package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class DrawableDiceBag extends Parent implements GuiItem {

    public DrawableDiceBag(int i, int j, GridPane root){
        new ConstructorHelper().setElement(i, j, root, "DrawableDiceBag", 20);
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
