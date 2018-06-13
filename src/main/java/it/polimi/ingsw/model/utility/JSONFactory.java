package it.polimi.ingsw.model.utility;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;
import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gamedata.ConcreteGameData;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/**
 * Converts JSON objects to game data objects.
 */
public class JSONFactory {

    private JSONFactory() {}

    /**
     * Loads a Die object from JSON data.
     * @param obj A JSON-encoded die
     * @return A Die object
     */
    public static Die getDie(JSONObject obj) {
        int id = parseInt(obj.get(JSONTag.DIE_ID).toString());
        Color color = Color.findByLabel((String) obj.get(JSONTag.COLOR));
        Shade shade = Shade.findByID(obj.get(JSONTag.SHADE).toString());
        return new PlasticDie(id, color, shade);
    }

    /**
     * Loads a DiceBag object from JSON data.
     * @param obj A JSON-encoded dice bag
     * @return A DiceBag object
     */
    public static DiceBag getDiceBag(JSONObject obj) {
        JSONArray list = (JSONArray) obj.get(JSONTag.DICE);
        List<Die> dice = new ArrayList<>();
        for (Object die : list)
            dice.add(JSONFactory.getDie((JSONObject) die));
        return new ArrayDiceBag(dice);
    }

    /**
     * Loads a RoundTrack object from JSON data.
     * @param obj A JSON-encoded round track
     * @return A RoundTrack object
     */
    public static RoundTrack getRoundTrack(JSONObject obj) {
        int currentRoundNumber = parseInt(obj.get(JSONTag.CURRENT_ROUND_NUMBER).toString());
        List<Die> dice = new ArrayList<>();
        for (Object die : (JSONArray) obj.get(JSONTag.ALL_DICE))
            dice.add(JSONFactory.getDie((JSONObject) die));
        int[] diceOnSlot = new int[Parameters.TOTAL_NUMBER_OF_ROUNDS];
        JSONArray numOfDiceOnSlot = (JSONArray) obj.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());
        return new PaperRoundTrack(currentRoundNumber, dice, diceOnSlot);
    }

    /**
     * Loads a WindowFrame object from JSON data.
     * @param obj A JSON-encoded window frame
     * @return A WindowFrame object
     */
    public static WindowFrame getWindowFrame(JSONObject obj) {
        String name = obj.get(JSONTag.NAME).toString();
        int difficulty = parseInt(obj.get(JSONTag.DIFFICULTY).toString());
        Map<Coordinate, Die> dice = new HashMap<>();
        Map<Coordinate, Color> colorConstraints = new HashMap<>();
        Map<Coordinate, Shade> shadeConstraints = new HashMap<>();
        JSONArray list = (JSONArray) obj.get(JSONTag.COORDINATES);
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                JSONObject coordinate = (JSONObject) list.get(i * Parameters.MAX_COLUMNS + j);
                if (coordinate.get(JSONTag.DIE) != null)
                    dice.put(new Coordinate(i, j), JSONFactory.getDie((JSONObject) (coordinate.get(JSONTag.DIE))));
                if (coordinate.get(JSONTag.COLOR_CONSTRAINT) != null)
                    colorConstraints.put(new Coordinate(i, j), Color.findByLabel((String) (coordinate.get(JSONTag.COLOR_CONSTRAINT))));
                if (coordinate.get(JSONTag.SHADE_CONSTRAINT) != null)
                    shadeConstraints.put(new Coordinate(i, j), Shade.findByValue(parseInt((coordinate.get(JSONTag.SHADE_CONSTRAINT).toString()))));
            }
        }
        return new PaperWindowFrame(name, difficulty, dice, colorConstraints, shadeConstraints);
    }

    /**
     * Loads a Player object from JSON data.
     * @param obj A JSON-encoded player
     * @return A Player object
     */
    public static Player getPlayer(JSONObject obj) {
        String username = obj.get(JSONTag.USERNAME).toString();
        int favorPoints = parseInt(obj.get(JSONTag.FAVOR_POINTS).toString());
        JSONObject windowFrame = (JSONObject) obj.get(JSONTag.WINDOW_FRAME);
        WindowFrame window = null;
        if (windowFrame != null)
            window = JSONFactory.getWindowFrame(windowFrame);
        JSONObject privateObjective = (JSONObject) obj.get(JSONTag.PRIVATE_OBJECTIVE);
        PrivateObjectiveCard po = null;
        if (privateObjective != null)
            po = JSONFactory.getPrivateObjectiveCard(privateObjective);
        return new ConcretePlayer(username, window, po, favorPoints);
    }

    /**
     * Loads a TurnManager object from JSON data.
     * @param obj A JSON-encoded turn manager
     * @return A TurnManager object
     */
    public static TurnManager getTurnManager(JSONObject obj) {
        boolean roundStarting = parseBoolean(obj.get(JSONTag.ROUND_STARTING).toString());
        boolean roundEnding = parseBoolean(obj.get(JSONTag.ROUND_ENDING).toString());
        int turnIndex = parseInt(obj.get(JSONTag.TURN_INDEX).toString());
        int firstPlayerIndex = parseInt(obj.get(JSONTag.FIRST_PLAYER_INDEX).toString());
        List<Player> players = new ArrayList<>();
        List<String> playerTurns = new ArrayList<>();
        JSONArray playerList = (JSONArray) obj.get(JSONTag.PLAYERS);
        for (Object o : playerList)
            players.add(JSONFactory.getPlayer((JSONObject) o));
        playerList = (JSONArray) obj.get(JSONTag.PLAYER_TURNS);
        for (Object o : playerList)
            playerTurns.add(o.toString());
        return new ArrayTurnManager(roundStarting, roundEnding, players, playerTurns, turnIndex, firstPlayerIndex);
    }

    /**
     * Loads a PrivateObjectiveCard object from JSON data.
     * @param obj A JSON-encoded private objective card
     * @return A PrivateObjectiveCard object
     */
    public static PrivateObjectiveCard getPrivateObjectiveCard(JSONObject obj) {
        int id = parseInt(obj.get(JSONTag.CARD_ID).toString());
        Deck d = new PrivateObjectiveDeck();
        while (d.size() > 0) {
            PrivateObjectiveCard card = (PrivateObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException(BAD_JSON);
    }

    /**
     * Loads a PublicObjectiveCard object from JSON data.
     * @param obj A JSON-encoded public objective card
     * @return A PublicObjectiveCard object
     */
    public static PublicObjectiveCard getPublicObjectiveCard(JSONObject obj) {
        int id = parseInt(obj.get(JSONTag.CARD_ID).toString());
        Deck d = new PublicObjectiveDeck();
        while (d.size() > 0) {
            PublicObjectiveCard card = (PublicObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException(BAD_JSON);
    }


    /**
     * Loads a ToolCard object from JSON data.
     * @param obj A JSON-encoded tool card
     * @return A ToolCard object
     */
    private static ToolCard getToolCard(JSONObject obj) {
        int id = parseInt(obj.get(JSONTag.CARD_ID).toString());
        int favorPoints = parseInt(obj.get(JSONTag.FAVOR_POINTS).toString());
        Deck d = new ToolDeck();
        while (d.size() > 0) {
            ToolCard card = (ToolCard) d.draw();
            if (card.getID() == id) {
                while (card.getFavorPoints() < favorPoints)
                    card.addFavorPoints();
                assert card.getFavorPoints() == favorPoints;
                return card;
            }
        }
        throw new NoSuchElementException(BAD_JSON);
    }


    /**
     * Loads a GameData object from JSON data.
     * @param obj A JSON-encoded game data
     * @return A GameData object
     */
    public static GameData getGameData(JSONObject obj) {
        List<Die> dicePool = new ArrayList<>();
        JSONArray dicePoolList = (JSONArray) obj.get(JSONTag.DICE_POOL);
        for (Object o : dicePoolList)
            dicePool.add(JSONFactory.getDie((JSONObject) o));
        DiceBag diceBag = JSONFactory.getDiceBag((JSONObject) obj.get(JSONTag.DICE_BAG));
        RoundTrack roundTrack = JSONFactory.getRoundTrack((JSONObject) obj.get(JSONTag.ROUND_TRACK));
        List<PublicObjectiveCard> publicObjectives = new ArrayList<>();
        JSONArray poList = (JSONArray) obj.get(JSONTag.PUBLIC_OBJECTIVES);
        for (Object o : poList)
            publicObjectives.add(JSONFactory.getPublicObjectiveCard((JSONObject) o));
        List<ToolCard> tools = new ArrayList<>();
        JSONArray toolList = (JSONArray) obj.get(JSONTag.TOOLS);
        for (Object o : toolList)
            tools.add(JSONFactory.getToolCard((JSONObject) o));
        TurnManager turnManager = JSONFactory.getTurnManager((JSONObject) obj.get(JSONTag.TURN_MANAGER));
        Die pickedDie = null;
        if (obj.get(JSONTag.PICKED_DIE) != null)
            pickedDie = JSONFactory.getDie((JSONObject) obj.get(JSONTag.PICKED_DIE));
        boolean diePlaced = parseBoolean(obj.get(JSONTag.DIE_PLACED).toString());
        int activeToolID = parseInt(obj.get(JSONTag.ACTIVE_TOOL_ID).toString());
        int passiveToolID = parseInt(obj.get(JSONTag.PASSIVE_TOOL_ID).toString());
        boolean toolActivated = parseBoolean(obj.get(JSONTag.TOOL_ACTIVATED).toString());
        List<Die> diceMoved = new ArrayList<>();
        JSONArray diceMovedList = (JSONArray) obj.get(JSONTag.DICE_MOVED);
        for (Object o : diceMovedList)
            diceMoved.add(JSONFactory.getDie((JSONObject) o));
        boolean undoAvailable = parseBoolean(obj.get(JSONTag.UNDO_AVAILABLE).toString());
        return new ConcreteGameData(roundTrack, diceBag, dicePool, publicObjectives, tools, turnManager, pickedDie,
                diePlaced, activeToolID, passiveToolID, toolActivated, diceMoved, undoAvailable);
    }
}
