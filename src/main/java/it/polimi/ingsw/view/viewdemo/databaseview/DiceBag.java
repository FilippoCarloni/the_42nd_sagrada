package it.polimi.ingsw.view.viewdemo.databaseview;

import it.polimi.ingsw.view.viewdemo.utility.ConstructorHelper;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class DiceBag extends Parent implements GuiItem {

    private StackPane container;

    public DiceBag(int i, int j, GridPane root){
        ConstructorHelper helper = new ConstructorHelper();
        container = helper.setElement(i, j, root, "DiceBag", 20);
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
