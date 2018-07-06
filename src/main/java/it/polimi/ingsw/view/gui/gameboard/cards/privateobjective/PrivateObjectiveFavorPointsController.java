package it.polimi.ingsw.view.gui.gameboard.cards.privateobjective;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;

import java.net.ConnectException;

import static java.lang.Integer.parseInt;

/**
 * Controller class for the private objective and favor points screen
 */

public class PrivateObjectiveFavorPointsController {

    @FXML
    private Label favorPointsLabel;
    @FXML
    private Label privateObjectiveTitle;
    @FXML
    private Rectangle privateObjectiveRectangle;
    @FXML
    private TextArea privateObjectiveTextArea;

    /**
     * Method used by the close button to close the screen.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void close(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize() throws ConnectException {
        favorPointsLabel.setText((GUIParameters.FAVOR_POINTS + parseInt(GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.FAVOR_POINTS).toString())));
        CardsSetter.setPrivateCard((JSONObject)GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.PRIVATE_OBJECTIVE), privateObjectiveTitle, privateObjectiveRectangle, privateObjectiveTextArea);
    }
}
