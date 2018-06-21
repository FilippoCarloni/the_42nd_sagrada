package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.layout.StackPane;

import java.util.List;

class WindowEvents {

    private String temporaryCommand;

    WindowEvents(){
        temporaryCommand = "";
    }

    /**
     * Method that will manage click events on window frame, discriminating between "place" and "move" actions
     * @param pane: StackPane which will handle mouse click events
     * @param row: row position of pane into the window frame
     * @param column: column position of pane into the window frame
     * @param dieOnIt: true if there is a die on the window frame in row-column position
     */
    void clickEventsOnWindowFrame(StackPane pane, int row, int column, boolean dieOnIt){
        if(dieOnIt){
            pane.setOnMouseClicked(e -> {
                temporaryCommand = "move " + row + " " + column;
                manageMoveEvents(row, column);
            });
        } else {
            pane.setOnMouseClicked(e -> GuiManager.getInstance().getConnectionController().send("place " + row + " " + column));
        }
    }

    private void manageMoveEvents(int oldRow, int oldColumn){
        List<StackPane> panesOnMainWindowFrame = GuiManager.getInstance().getGameBoard().getPanesOnWindowFrame();

        for(int i = 0; i < panesOnMainWindowFrame.size(); i++){
            int tempRow = WindowFrameDrawer.rowAndColumnFromListIndex(i)[0];
            int tempColumn = WindowFrameDrawer.rowAndColumnFromListIndex(i)[1];
            if(tempRow != oldRow && tempColumn != oldColumn) {
                panesOnMainWindowFrame.get(i).setOnMouseClicked(e -> {
                    GuiManager.getInstance().getConnectionController().send(temporaryCommand + " " + tempRow + " " + tempColumn);
                    resetPlaceAfterClick(panesOnMainWindowFrame);
                });
            }
        }
    }
    private void resetPlaceAfterClick(List<StackPane> panesOnMainWindowFrame){
        temporaryCommand = "";
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS * GUIParameters.MAX_WINDOW_FRAMES_ROWS; i++){
            int tempRow = WindowFrameDrawer.rowAndColumnFromListIndex(i)[0];
            int tempColumn = WindowFrameDrawer.rowAndColumnFromListIndex(i)[1];
            panesOnMainWindowFrame.get(i).setOnMouseClicked(e -> GuiManager.getInstance().getConnectionController().send("place " + tempRow + " " + tempColumn));
        }
    }
}
