package it.polimi.ingsw.view.gui.gameboard.cards.privateobjective;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

import java.net.ConnectException;

import static java.lang.Integer.parseInt;

public class PrivateObjectiveFavorPointsController {

    @FXML
    private Label favorPointsLabel;
    @FXML
    private HBox root;

    public void close(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize() throws ConnectException {
        favorPointsLabel.setText(("Favor Points: " + parseInt(GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.FAVOR_POINTS).toString())));
        CardsSetter.setPrivateCard((JSONObject)GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.PRIVATE_OBJECTIVE));
        root.getChildren().add(CardsSetter.setPrivateCard((JSONObject)GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.PRIVATE_OBJECTIVE)));
    }
}
