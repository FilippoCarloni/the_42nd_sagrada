package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

class WindowEvents {

    private String temporaryCommand;

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
                try {
                    manageMoveEvents();
                } catch (RemoteException | ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        } else {
            pane.setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send("place " + row + " " + column);
                } catch (ConnectException | RemoteException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }

    private void manageMoveEvents() throws RemoteException, ConnectException {
        List<StackPane> panesOnMainWindowFrame = GuiManager.getInstance().getGameBoard().getPanesOnWindowFrame();

        for (int i = 0; i < panesOnMainWindowFrame.size(); i++) {
            int tempRow = WindowFrameDrawer.rowAndColumnFromListIndex(i)[0];
            int tempColumn = WindowFrameDrawer.rowAndColumnFromListIndex(i)[1];
            panesOnMainWindowFrame.get(i).setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(temporaryCommand + " " + tempRow + " " + tempColumn);
                    resetPlaceAfterClick(panesOnMainWindowFrame);
                } catch (ConnectException | RemoteException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }
    private void resetPlaceAfterClick(List<StackPane> panesOnMainWindowFrame) throws RemoteException, ConnectException {
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
