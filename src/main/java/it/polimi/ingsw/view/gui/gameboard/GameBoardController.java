package it.polimi.ingsw.view.gui.gameboard;

import com.jfoenix.controls.JFXButton;
import it.polimi.ingsw.connection.constraints.Commands;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackVisualizer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.utility.GUIColor;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
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
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Main Board controller class.
 * It contains all methods that handle the drawing and updating of the game board.
 */

public class GameBoardController {

    //Round Track StackPane and Canvas container
    private ArrayList<StackPane> panesOnRoundTrack;
    private ArrayList<Canvas> canvasOnRoundTrack;

    //Player's names and window frames containers
    private ArrayList<Label> playersNameLabels;
    private ArrayList<GridPane> playersGrids;

    //Element to draw window frames containers
    private Map<Integer, ArrayList<Canvas>> canvasOnGrids;
    private Map<Integer, ArrayList<StackPane>> panesOnGrids;

    //Element to draw dice pool containers
    private ArrayList<StackPane> panesOnDicePool;
    private ArrayList<Canvas> canvasOnDicePool;

    //Public cards elements containers
    private ArrayList<Label> toolsTitle;
    private ArrayList<TextArea> toolsDescription;
    private ArrayList<Label> publicObjectivesTitle;
    private ArrayList<TextArea> publicObjectivesDescription;

    //JSON containers
    private JSONArray players;
    private JSONObject mainPlayer;

    //Round Track references
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
    private Label labelPlayer2;
    @FXML
    private Label labelPlayer3;
    @FXML
    private Label labelPlayer4;
    @FXML
    private GridPane diceGrid;
    @FXML
    private Label toolTitle1;
    @FXML
    private Label toolTitle2;
    @FXML
    private Label toolTitle3;
    @FXML
    private TextArea toolDescription1;
    @FXML
    private TextArea toolDescription2;
    @FXML
    private TextArea toolDescription3;
    @FXML
    private Label publicObjectiveTitle1;
    @FXML
    private Label publicObjectiveTitle2;
    @FXML
    private Label publicObjectiveTitle3;
    @FXML
    private TextArea publicObjectiveDescription1;
    @FXML
    private TextArea publicObjectiveDescription2;
    @FXML
    private TextArea publicObjectiveDescription3;
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


    //Private Setters
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
    private void setPublicCardsElements(){
        toolsTitle = new ArrayList<>();
        toolsDescription = new ArrayList<>();

        publicObjectivesTitle = new ArrayList<>();
        publicObjectivesDescription = new ArrayList<>();

        toolsTitle.add(toolTitle1);
        toolsTitle.add(toolTitle2);
        toolsTitle.add(toolTitle3);

        toolsDescription.add(toolDescription1);
        toolsDescription.add(toolDescription2);
        toolsDescription.add(toolDescription3);

        publicObjectivesTitle.add(publicObjectiveTitle1);
        publicObjectivesTitle.add(publicObjectiveTitle2);
        publicObjectivesTitle.add(publicObjectiveTitle3);

        publicObjectivesDescription.add(publicObjectiveDescription1);
        publicObjectivesDescription.add(publicObjectiveDescription2);
        publicObjectivesDescription.add(publicObjectiveDescription3);
    }

    /**
     * Getter of the List containing all StackPane of the main player.
     * @return a List of StackPane.
     */
    public List<StackPane> getPanesOnWindowFrame(){
        return panesOnGrids.get(getMainPlayerByUsername(players));
    }

    /**
     * Getter for the continue button; used by the GuiManager to make te button visible and clickable at the end of
     * the match.
     * @return a reference to the continue button.
     */
    public JFXButton getContinueButton(){
        return continueButton;
    }

    /**
     * Public setter for the RoundTrackVisualizer, used when the round track is open by a player.
     * @param rVisualizer: a reference to the current RoundTrackVisualizer.
     */
    public void setrVisualizer(RoundTrackVisualizer rVisualizer) {
        this.rVisualizer = rVisualizer;
    }

    /**
     * Main Player getter.
     * @return a reference to the main player's JSONObject
     */
    public JSONObject getMainPlayer(){
        return mainPlayer;
    }

    //Maps and labels management
    private static int getMainPlayerByUsername(JSONArray players){
        try {
            String usernameMainPlayer = GuiManager.getInstance().getUsernameMainPlayer();
            for (int i = 0; i < players.size(); i++) {
                if ((((JSONObject) players.get(i)).get(JSONTag.USERNAME)).toString().equals(usernameMainPlayer)) {
                    return i;
                }
            }
        } catch (ConnectException e){
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
        WindowFrameDrawer.frameFiller(canvasOnGrids.get(getMainPlayerByUsername(players)), panesOnGrids.get(getMainPlayerByUsername(players)), windowFramePlayer1, GUIParameters.NO_SCALE, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getMainPlayerByUsername(players)) {
                WindowFrameDrawer.frameFiller(canvasOnGrids.get(i), panesOnGrids.get(i), playersGrids.get(j), GUIParameters.REDUCTION_FOR_OTHER_PLAYERS, false);
                j++;
            }
        }
    }

    //Cards management
    private void addCardsOnGameBoard(JSONObject json){
        CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.TOOLS), toolsTitle, toolsDescription, true);
        CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES), publicObjectivesTitle, publicObjectivesDescription, false);
    }
    private void setUsedToolCard(JSONArray json){
        for(int i = 0; i < toolsTitle.size(); i++){
            if(parseInt(((JSONObject)json.get(i)).get(JSONTag.FAVOR_POINTS).toString()) > 0 && !toolsTitle.get(i).getStyleClass().contains(GUIParameters.USED_TOOL))
                toolsTitle.get(i).getStyleClass().add(GUIParameters.USED_TOOL);
        }
    }
    private void privateObjectiveRectangleFiller(JSONObject privateObjectiveCard){
        int id = parseInt(privateObjectiveCard.get(JSONTag.CARD_ID).toString());
        privateObjectiveRectangle.setFill(GUIColor.findById(id).getColor());
        privateObjectiveRectangle.getStyleClass().add(GUIParameters.CLICKABLE);
    }

    /**
     * Method set on Action on the Private Objective Rectangle, it opens a screen containing the number of remaining
     * Favor Points, and the Private Objective Card.
     */
    public void seePrivateObjective()   {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.PRIVATE_OBJECTIVE_FXML_PATH));
            Stage stage = new Stage();
            stage.setTitle(GUIParameters.PRIVATE_OBJECTIVE_TITLE + GUIParameters.SEPARATOR + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e){
            print(e.getMessage());
        }
    }

    /**
     * Messages printing into TextArea, called from update() method into GuiManager.
     * @param message: the message to print.
     */
    public void setMessageText(String message){
        messageTextArea.appendText(" " + message);
    }

    /**
     * Methods used by action buttons; all this methods send a command to the Connection Controller.
     * The possible commands are:
     * <ol>
     *     <li>pass</li>
     *     <li>undo</li>
     *     <li>redo</li>
     * </ol>
     */
    public void pass() throws ConnectException {
        GuiManager.getInstance().getConnectionController().send(Commands.PASS);
    }
    public void undo() throws ConnectException {
        GuiManager.getInstance().getConnectionController().send(Commands.UNDO);
    }
    public void redo() throws ConnectException {
        GuiManager.getInstance().getConnectionController().send(Commands.REDO);
    }

    /**
     * Method set on Action on the Continue button. The button become visible only at the end of the match and
     * change the scene, going from the main board to the points visualization.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void goToEndGame(ActionEvent event){
        try {
            GuiManager.getInstance().setGameBoard(null);
            GuiManager.getInstance().setGameBoardMessage(null);
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.END_GAME_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            GuiManager.setOnCloseRequest(stage);
            stage.setTitle(GUIParameters.END_GAME_TITLE + GUIParameters.SEPARATOR + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(scene);
        } catch (IOException e) {
            print(e.getMessage());
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
        } catch (ConnectException e) {
            print(e.getMessage());
        }
    }

    //Updater support method
    private void updater(JSONObject json){
        JSONArray toolCards = (JSONArray) json.get(JSONTag.TOOLS);

        int j = 0;
        drawMapAndSetUsername(canvasOnGrids.get(getMainPlayerByUsername(players)), panesOnGrids.get(getMainPlayerByUsername(players)), mainPlayer, labelPlayer1, GUIParameters.NO_SCALE, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getMainPlayerByUsername(players)) {
                drawMapAndSetUsername(canvasOnGrids.get(i), panesOnGrids.get(i), (JSONObject) players.get(i), playersNameLabels.get(j), GUIParameters.REDUCTION_FOR_OTHER_PLAYERS, false);
                j++;
            }
        }

        JSONObject roundTrack = (JSONObject) json.get(JSONTag.ROUND_TRACK);
        rDrawer.roundTrackUpdate(roundTrack, panesOnRoundTrack, canvasOnRoundTrack);
        if(rVisualizer != null){
            rVisualizer.allDiceDrawer(roundTrack);
        }

        setUsedToolCard(toolCards);
        manageDraftedDie(json);
        DiceDrawer.dicePoolReset(json, panesOnDicePool, canvasOnDicePool);
    }
    private void manageDraftedDie(JSONObject json){
        draftedDieCanvas.getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.INCREMENT_FOR_DRAFTED_DIE,
                GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.INCREMENT_FOR_DRAFTED_DIE);
        draftedDieStackPane.setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.BACKGROUND_COLOR);
        draftedDieStackPane.setStyle(GUIParameters.BORDER_COLOR_STRING + GUIParameters.BORDER_COLOR);
        if(json.get(JSONTag.PICKED_DIE) != null) {
            JSONObject draftedDie = (JSONObject) json.get(JSONTag.PICKED_DIE);
            int value = parseInt((draftedDie.get(JSONTag.SHADE)).toString());
            String color = draftedDie.get(JSONTag.COLOR).toString();

            DiceDrawer.dicePointsDrawer(value, color, draftedDieCanvas.getGraphicsContext2D(), draftedDieStackPane, GUIParameters.INCREMENT_FOR_DRAFTED_DIE);
        }
    }

    //First game board draw, called by initialize(), and support methods
    private void firstUpdate(JSONObject json){
        initializeGlobalVariables(json);

        Group group = new Group();
        StackPane pane = new StackPane();

        rDrawer = new RoundTrackDrawer();

        setGrid();
        setPlayersNameLabels();
        setPublicCardsElements();
        setMaps();

        callFirsFillers(json);
        updater(json);

        try {
            if(GuiManager.getInstance().getNowPlaying() != null)
                setMessageText(GuiManager.getInstance().getNowPlaying());
        } catch (ConnectException e) {
            print(e.getMessage());
        }

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().add(pane);
        backgroundMainPlayer.getChildren().add(group);
    }
    private void initializeGlobalVariables(JSONObject json){
        players = (JSONArray)((JSONObject)json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getMainPlayerByUsername(players));
        panesOnRoundTrack = new ArrayList<>();
        canvasOnRoundTrack = new ArrayList<>();
        panesOnDicePool = new ArrayList<>();
        canvasOnDicePool = new ArrayList<>();
        rVisualizer = null;
    }
    private void callFirsFillers(JSONObject json){
        rDrawer.roundTrackStartingFiller(roundTrackGrid, panesOnRoundTrack, canvasOnRoundTrack);
        roundTrackAnchorPane.setOnMouseClicked(e -> rDrawer.seeAllDice());
        fillFirstTimeMap();
        privateObjectiveRectangleFiller((JSONObject) mainPlayer.get(JSONTag.PRIVATE_OBJECTIVE));
        DiceDrawer.diceFiller(diceGrid, panesOnDicePool, canvasOnDicePool, (players.size() * 2) + 1, true);
        addCardsOnGameBoard(json);
    }

    @FXML
    public void initialize() throws ConnectException {
        GuiManager.getInstance().setGameBoard(this);
        JSONObject json = GuiManager.getInstance().getGameBoardMessage();
        messageTextArea.setEditable(false);
        firstUpdate(json);
    }
}
