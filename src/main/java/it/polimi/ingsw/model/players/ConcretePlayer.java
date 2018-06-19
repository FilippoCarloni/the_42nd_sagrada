package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NEGATIVE_INTEGER;
import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Implements the Player interface with a generic structure.
 */
public class ConcretePlayer implements Player{

    private String username;
    private WindowFrame window;
    private PrivateObjectiveCard po;
    private int favorPoints;

    /**
     * Generates a player instance with null window frame and null private objective.
     * @param username A String representing the username
     */
    public ConcretePlayer(String username){
        this.username=username;
    }

    /**
     * Generates a player from the state encoded in the given parameters.
     * @param username Player's username string
     * @param window Player's current window frame
     * @param privateObjective Player's private objective card
     * @param favorPoints Player's number of favor points
     */
    public ConcretePlayer(String username, WindowFrame window, PrivateObjectiveCard privateObjective, int favorPoints) {
        this.username = username;
        this.window = window;
        this.po = privateObjective;
        this.favorPoints = favorPoints;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setWindowFrame(WindowFrame window) {
        if (window == null) throw new NullPointerException(NULL_PARAMETER);
        favorPoints = window.getDifficulty();
        this.window = window;
    }

    @Override
    public WindowFrame getWindowFrame() {
        return window;
    }

    @Override
    public PrivateObjectiveCard getPrivateObjective() {
        return po == null ? null : JSONFactory.getPrivateObjectiveCard(po.encode());
    }

    @Override
    public void setPrivateObjective(PrivateObjectiveCard po) {
        if (po == null) throw new NullPointerException(NULL_PARAMETER);
        this.po = po;
    }

    @Override
    public int getFavorPoints() {
        return favorPoints;
    }

    @Override
    public void setFavorPoints(int points) {
        if (points < 0) throw new IllegalArgumentException(NEGATIVE_INTEGER);
        favorPoints = points;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof ConcretePlayer && ((ConcretePlayer) o).getUsername().equals(username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "\n  name  : " + username +
                "\n  fp    : " + favorPoints +
                "\n  po    : " + po +
                "\n  frame :\n" + window +
                "\n}";

    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put(JSONTag.USERNAME, username);
        obj.put(JSONTag.FAVOR_POINTS, favorPoints);
        obj.put(JSONTag.WINDOW_FRAME, window == null ? null : window.encode());
        obj.put(JSONTag.PRIVATE_OBJECTIVE, po == null ? null : po.encode());
        return obj;
    }
}
