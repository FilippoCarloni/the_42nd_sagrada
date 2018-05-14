package it.polimi.ingsw.view.viewdemo.databaseview;

/**
 * Generic interface of every element present on our GUI. It gives 4 methods, one which allows to load images from a specific path, two for Glow effects
 * (setOnMouseEnter and removeOnMouseExited) and one which allows to zoom on a specific element of the GUI; each class which implements this interface.
 * Each class which implements this interface will have a reference to the corresponding object in model package
 */

public interface GuiItem {

    void setGlow();
    void removeGlow();
    void zoom();

}
