package it.polimi.ingsw.view.gui.preliminarystages.windowframeschoice;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

//TODO: add button to launch Game Board after map selection
//TODO: private objective not working properly, fix it

public class WindowFramesChoice {

    private ArrayList<GridPane> maps = new ArrayList<>();

    @FXML
    private GridPane map1;
    @FXML
    private GridPane map2;
    @FXML
    private GridPane map3;
    @FXML
    private GridPane map4;
    @FXML
    private ImageView privObjCard;

    //Map Choice Management
    public void choose1(){
        clicked(1);
    }
    public void choose2(){
        clicked(2);
    }
    public void choose3(){
        clicked(3);
    }
    public void choose4(){
        clicked(4);
    }
    private void clicked(int idMapChosen) {
        GameBoardController controller = new GameBoardController();
        controller.setIdMapChosen(idMapChosen);
        GuiManager.getInstance().getConnectionController().send("window " + idMapChosen);
    }

    //Methods for preliminary stage
    private void drawPreGame(JSONObject json){
        JSONArray jsonMaps = (JSONArray) json.get(JSONTag.WINDOW_FRAMES);
        JSONObject card = (JSONObject) json.get(JSONTag.PRIVATE_OBJECTIVE);

        privObjCard = new CardsSetter().setPrivateCard(privObjCard, card);
        setMaps();
        for(int i = 0; i < GUIParameters.NUM_MAPS_TO_CHOOSE; i++){
            new WindowFrameDrawer().frameFiller(maps.get(i), (JSONObject) jsonMaps.get(i));
        }
    }
    private void setMaps(){
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
    }

    @FXML
    protected void initialize() {
        //I need this to initialize @FXML objects
        drawPreGame(GuiManager.getInstance().getPreGameMessage());
    }
}
