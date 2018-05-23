package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import org.json.simple.JSONObject;

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
     * Generates a clone of the player represented with JSON syntax.
     * @param obj A JSON Object that holds Player-like information
     */
    public ConcretePlayer(JSONObject obj) {
        username = (String) obj.get("username");
        favorPoints = (int) obj.get("favor_points");
        JSONObject windowFrame = (JSONObject) obj.get("window_frame");
        if (windowFrame != null)
            window = new PaperWindowFrame(windowFrame);
        JSONObject privateObjective = (JSONObject) obj.get("private_objective");
        if (privateObjective != null)
            po = PrivateObjectiveCard.getCardFromJSON(privateObjective);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setWindowFrame(WindowFrame window) {
        if (window == null) throw new NullPointerException("Cannot play with a null map.");
        favorPoints = window.getDifficulty();
        this.window = window;
    }

    @Override
    public WindowFrame getWindowFrame() {
        return window == null ? null : new PaperWindowFrame(window.encode());
    }

    @Override
    public PrivateObjectiveCard getPrivateObjective() {
        return po == null ? null : PrivateObjectiveCard.getCardFromJSON(po.encode());
    }

    @Override
    public void setPrivateObjective(PrivateObjectiveCard po) {
        if (po == null) throw new NullPointerException("Cannot play with no private objective.");
        this.po = po;
    }

    @Override
    public int getFavorPoints() {
        return favorPoints;
    }

    @Override
    public void setFavorPoints(int points) {
        if (points < 0)
            throw new IllegalArgumentException("Favor points cannot be negative.");
        favorPoints = points;
    }

    @Override
    public String toString() {
        String printableUsername = username + " (FP:" + favorPoints + ")";
        StringBuilder sb = new StringBuilder();
        if (window != null) {
            String frame = window.toString();
            int len = frame.indexOf('\n');
            for (int i = 0; i < len; i++)
                sb.append("-");
            sb.append("\n");
            for (int i = 0; i < (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
            sb.append(printableUsername);
            for (int i = 0; i < len - printableUsername.length() - (len - printableUsername.length()) / 2; i++)
                sb.append(" ");
            sb.append("\n");
            sb.append(frame);
        }
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("favor_points", favorPoints);
        obj.put("window_frame", window == null ? null : window.encode());
        obj.put("private_objective", po == null ? null : po.encode());
        return obj;
    }
}
