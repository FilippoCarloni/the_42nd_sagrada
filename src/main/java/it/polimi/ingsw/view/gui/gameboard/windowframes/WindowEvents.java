package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.ConnectException;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Class that manage click events on the main player's window frame.
 */

class WindowEvents {

    private String temporaryCommand;

    /**
     * Method that will manage click events on window frame, discriminating between "place" and "move" actions.
     * If there is a die into the selected position, temporary command will be initialized to "move row column", and
     * will be called manageMoveEvents() method, to handle move actions.
     * If there is not a die into the selected position, the command will be set to "place row column", to allow the player
     * to put a die into the selected position.
     * @param pane: StackPane which will handle mouse click events
     * @param row: row position of pane into the window frame
     * @param column: column position of pane into the window frame
     * @param dieOnIt: true if there is a die on the window frame in row-column position
     */
    void clickEventsOnWindowFrame(StackPane pane, int row, int column, boolean dieOnIt){
        if(dieOnIt){
            pane.setOnMouseClicked(e -> {
                temporaryCommand = GUIParameters.MOVE + row + " " + column;
                try {
                    manageMoveEvents();
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        } else {
            pane.setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(GUIParameters.PLACE + row + " " + column);
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }

    private void manageMoveEvents() throws ConnectException {
        List<StackPane> panesOnMainWindowFrame = GuiManager.getInstance().getGameBoard().getPanesOnWindowFrame();

        for (int i = 0; i < panesOnMainWindowFrame.size(); i++) {
            int tempRow = WindowFrameDrawer.rowAndColumnFromListIndex(i)[0];
            int tempColumn = WindowFrameDrawer.rowAndColumnFromListIndex(i)[1];
            panesOnMainWindowFrame.get(i).setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(temporaryCommand + " " + tempRow + " " + tempColumn);
                    resetPlaceAfterClick(panesOnMainWindowFrame);
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }
    private void resetPlaceAfterClick(List<StackPane> panesOnMainWindowFrame) throws ConnectException {
        temporaryCommand = null;
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS * GUIParameters.MAX_WINDOW_FRAMES_ROWS; i++){
            int tempRow = WindowFrameDrawer.rowAndColumnFromListIndex(i)[0];
            int tempColumn = WindowFrameDrawer.rowAndColumnFromListIndex(i)[1];
            JSONArray coordinates = (JSONArray) ((JSONObject)GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.WINDOW_FRAME)).get(JSONTag.COORDINATES);
            boolean dieOnIt = ((JSONObject) coordinates.get(i)).get(JSONTag.DIE) != null;
            clickEventsOnWindowFrame(panesOnMainWindowFrame.get(i), tempRow, tempColumn, dieOnIt);
        }
    }
}
