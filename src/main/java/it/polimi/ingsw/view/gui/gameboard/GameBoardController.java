package it.polimi.ingsw.view.gui.gameboard;

import com.jfoenix.controls.JFXButton;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackVisualizer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIColor;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Main Board controller class
 */

public class GameBoardController {

    /**
     * Round Track StackPane and Canvas container
     */
    private ArrayList<StackPane> panesOnRoundTrack;
    private ArrayList<Canvas> canvasOnRoundTrack;

    /**
     * Player's names and window frames containers
     */
    private ArrayList<Label> playersNameLabels;
    private ArrayList<GridPane> playersGrids;

    /**
     * Element to draw window frames containers
     */
    private Map<Integer, ArrayList<Canvas>> canvasOnGrids;
    private Map<Integer, ArrayList<StackPane>> panesOnGrids;

    /**
     * Element to draw dice pool containers
     */
    private ArrayList<StackPane> panesOnDicePool;
    private ArrayList<Canvas> canvasOnDicePool;

    /**
     * Containers for tool and public objective cards
     */
    private ArrayList<VBox> toolCardsContainers;
    private ArrayList<VBox> publicObjectiveContainers;

    /**
     * JSON containers
     */
    private JSONArray players;
    private JSONObject mainPlayer;

    /**
     * Round Track references
     */
    private RoundTrackDrawer rDrawer;
    private RoundTrackVisualizer rVisualizer;

    @FXML
    private HBox roundTrackAnchorPane;
    @FXML
    private GridPane roundTrackGrid;
    @FXML
    private VBox backgroundMainPlayer;
    @FXML
    private GridPane windowFramePlayer1;
    @FXML
    private GridPane windowFramePlayer2;
    @FXML
    private GridPane windowFramePlayer3;
    @FXML
    private GridPane windowFramePlayer4;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label favorPointsLabel;
    @FXML
    private Label labelPlayer2;
    @FXML
    private Label labelPlayer3;
    @FXML
    private Label labelPlayer4;
    @FXML
    private GridPane diceGrid;
    @FXML
    private VBox toolCard1;
    @FXML
    private VBox toolCard2;
    @FXML
    private VBox toolCard3;
    @FXML
    private VBox publicObjectiveCard1;
    @FXML
    private VBox publicObjectiveCard2;
    @FXML
    private VBox publicObjectiveCard3;
    @FXML
    private Rectangle privateObjectiveRectangle;
    @FXML
    private StackPane draftedDieStackPane;
    @FXML
    private Canvas draftedDieCanvas;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private JFXButton continueButton;


    //Setters and getters
    private void setGrid(){
        playersGrids = new ArrayList<>();

        playersGrids.add(windowFramePlayer2);
        playersGrids.add(windowFramePlayer3);
        playersGrids.add(windowFramePlayer4);
    }
    private void setPlayersNameLabels(){
        playersNameLabels = new ArrayList<>();

        playersNameLabels.add(labelPlayer2);
        playersNameLabels.add(labelPlayer3);
        playersNameLabels.add(labelPlayer4);
    }
    private void setMaps(){
        canvasOnGrids = new HashMap<>();
        panesOnGrids = new HashMap<>();
        canvasOnGrids.put(getMainPlayerByUsername(players), new ArrayList<>());
        panesOnGrids.put(getMainPlayerByUsername(players), new ArrayList<>());
        for(int i = 0; i < players.size(); i++){
            if(i != getMainPlayerByUsername(players)){
                canvasOnGrids.put(i, new ArrayList<>());
                panesOnGrids.put(i, new ArrayList<>());
            }
        }
    }
    private void setCardsContainers(){
        toolCardsContainers = new ArrayList<>();
        publicObjectiveContainers = new ArrayList<>();

        toolCardsContainers.add(toolCard1);
        toolCardsContainers.add(toolCard2);
        toolCardsContainers.add(toolCard3);

        publicObjectiveContainers.add(publicObjectiveCard1);
        publicObjectiveContainers.add(publicObjectiveCard2);
        publicObjectiveContainers.add(publicObjectiveCard3);
    }
    public List<StackPane> getPanesOnWindowFrame(){
        return panesOnGrids.get(getMainPlayerByUsername(players));
    }
    public JFXButton getContinueButton(){
        return continueButton;
    }
    public void setrVisualizer(RoundTrackVisualizer rVisualizer) {
        this.rVisualizer = rVisualizer;
    }

    /**
     * Main Player getter
     * @return a reference to the main player's JSONObject
     */
    public JSONObject getMainPlayer(){
        return mainPlayer;
    }

    //Maps and labels management
    private int getMainPlayerByUsername(JSONArray players){
        try {
            String usernameMainPlayer = GuiManager.getInstance().getUsernameMainPlayer();
            for (int i = 0; i < players.size(); i++) {
                if ((((JSONObject) players.get(i)).get(JSONTag.USERNAME)).toString().equals(usernameMainPlayer)) {
                    return i;
                }
            }
        } catch (RemoteException | ConnectException e){
            print(e.getMessage());
        }
        throw new IllegalArgumentException(GUIParameters.MAIN_PLAYER_NOT_FOUND);
    }
    private void drawMapAndSetUsername(ArrayList<Canvas> canvasArrayList, ArrayList<StackPane> stackPanes, JSONObject player, Label labelPlayer, double scale, boolean editable){
        labelPlayer.setText(player.get(JSONTag.USERNAME).toString());
        WindowFrameDrawer.frameReset((JSONObject) player.get(JSONTag.WINDOW_FRAME), canvasArrayList, stackPanes, scale, editable);
    }
    private void fillFirstTimeMap(){
        int j = 0;
        WindowFrameDrawer.frameFiller(canvasOnGrids.get(getMainPlayerByUsername(players)), panesOnGrids.get(getMainPlayerByUsername(players)), windowFramePlayer1, 1, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getMainPlayerByUsername(players)) {
                WindowFrameDrawer.frameFiller(canvasOnGrids.get(i), panesOnGrids.get(i), playersGrids.get(j), GUIParameters.REDUCTION_SCALE, false);
                j++;
            }
        }
    }

    //Cards management
    private void addCardsOnGameBoard(JSONObject json){
        List<ImageView> toolCards = CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY, true);
        List<ImageView> pubObjCards = CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY, false);

        for(int i = 0; i < toolCards.size(); i++){
            toolCardsContainers.get(i).getChildren().add(toolCards.get(i));
            publicObjectiveContainers.get(i).getChildren().add(pubObjCards.get(i));
        }
    }
    private void privateObjectiveRectangleFiller(JSONObject privateObjectiveCard){
        int id = parseInt(privateObjectiveCard.get(JSONTag.CARD_ID).toString());
        privateObjectiveRectangle.setFill(GUIColor.findById(id).getColor());
    }

    /**
     * Messages printing into TextArea, called from update() method into GuiManager.
     * @param message: the message to print.
     */
    public void setMessageText(String message){
        messageTextArea.appendText(message);
    }

    /**
     * Methods used by action buttons.
     */
    public void pass() throws RemoteException, ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.PASS);
    }
    public void undo() throws RemoteException, ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.UNDO);
    }
    public void redo() throws RemoteException, ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.REDO);
    }
    public void goToEndGame(ActionEvent event){
        try {
            GuiManager.getInstance().setGameBoard(null);
            GuiManager.getInstance().setGameBoardMessage(null);
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.END_GAME_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOnCloseRequest(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(GUIParameters.EXIT);
                } catch (ConnectException | RemoteException e1) {
                    print(e1.getMessage());
                }
                System.exit(0);
            });
            stage.setTitle(GUIParameters.END_GAME_TITLE + " - " + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method, called by the update() method from Gui Manager to handle gui refresh.
     * @param json: the JSONObject containing all the information to re-draw all gui elements.
     */
    public void gameBoardUpdate(JSONObject json){
        try {
            if(GuiManager.getInstance().getGameStatMessage() == null) {
                players = (JSONArray) ((JSONObject) json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
                mainPlayer = (JSONObject) players.get(getMainPlayerByUsername(players));
                updater(json);
            }
        } catch (ConnectException | RemoteException e) {
            print(e.getMessage());
        }
    }

    //Updater support method
    private void updater(JSONObject json){
        int j = 0;
        drawMapAndSetUsername(canvasOnGrids.get(getMainPlayerByUsername(players)), panesOnGrids.get(getMainPlayerByUsername(players)), mainPlayer, labelPlayer1, 1, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getMainPlayerByUsername(players)) {
                drawMapAndSetUsername(canvasOnGrids.get(i), panesOnGrids.get(i), (JSONObject) players.get(i), playersNameLabels.get(j), GUIParameters.REDUCTION_SCALE, false);
                j++;
            }
        }

        JSONObject roundTrack = (JSONObject) json.get(JSONTag.ROUND_TRACK);
        rDrawer.roundTrackUpdate(roundTrack, panesOnRoundTrack, canvasOnRoundTrack);
        if(rVisualizer != null){
            rVisualizer.allDiceDrawer(roundTrack);
        }

        favorPointsLabel.setText("    FP: " + mainPlayer.get(JSONTag.FAVOR_POINTS));

        manageDraftedDie(json);
        DiceDrawer.dicePoolReset(json, panesOnDicePool, canvasOnDicePool);
    }
    private void manageDraftedDie(JSONObject json){
        draftedDieCanvas.getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 1.5, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 1.5);
        draftedDieStackPane.setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.BACKGROUND_COLOR);
        if(json.get(JSONTag.PICKED_DIE) != null) {
            JSONObject draftedDie = (JSONObject) json.get(JSONTag.PICKED_DIE);
            int value = parseInt((draftedDie.get(JSONTag.SHADE)).toString());
            String color = draftedDie.get(JSONTag.COLOR).toString();

            DiceDrawer.dicePointsDrawer(value, color, draftedDieCanvas.getGraphicsContext2D(), draftedDieStackPane, 1.5);
        }
    }

    //First game board draw, called by initialize
    private void firstUpdate(JSONObject json){
        players = (JSONArray)((JSONObject)json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getMainPlayerByUsername(players));
        panesOnRoundTrack = new ArrayList<>();
        canvasOnRoundTrack = new ArrayList<>();
        panesOnDicePool = new ArrayList<>();
        canvasOnDicePool = new ArrayList<>();
        rVisualizer = null;

        Group group = new Group();
        StackPane pane = new StackPane();

        rDrawer = new RoundTrackDrawer();

        setGrid();
        setPlayersNameLabels();
        setCardsContainers();
        setMaps();

        rDrawer.roundTrackStartingFiller(roundTrackGrid, panesOnRoundTrack, canvasOnRoundTrack);
        roundTrackAnchorPane.setOnMouseClicked(e -> rDrawer.seeAllDice());
        fillFirstTimeMap();
        privateObjectiveRectangleFiller((JSONObject) mainPlayer.get(JSONTag.PRIVATE_OBJECTIVE));
        DiceDrawer.diceFiller(diceGrid, panesOnDicePool, canvasOnDicePool, ((JSONArray) json.get(JSONTag.DICE_POOL)).size(), true);
        updater(json);
        addCardsOnGameBoard(json);

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().add(pane);
        backgroundMainPlayer.getChildren().add(group);
    }

    @FXML
    public void initialize() throws RemoteException, ConnectException {
        GuiManager.getInstance().setGameBoard(this);
        JSONObject json = GuiManager.getInstance().getGameBoardMessage();
        messageTextArea.setEditable(false);
        firstUpdate(json);
    }
}
