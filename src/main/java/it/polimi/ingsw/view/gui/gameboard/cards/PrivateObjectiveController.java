package it.polimi.ingsw.view.gui.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;

import java.net.ConnectException;
import java.rmi.RemoteException;

public class PrivateObjectiveController {

    @FXML
    private ImageView privateObjectiveImageView;
    @FXML
    private AnchorPane apPrivateObjective;

    public void quit(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize() throws RemoteException, ConnectException {
        privateObjectiveImageView = CardsSetter.setPrivateCard((JSONObject) GuiManager.getInstance().getGameBoard().getMainPlayer().get(JSONTag.PRIVATE_OBJECTIVE));
        apPrivateObjective.getChildren().add(privateObjectiveImageView);
    }

}
