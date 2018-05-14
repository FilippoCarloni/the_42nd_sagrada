package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public class ConcretePlayer implements Player{

    private String username;
    private WindowFrame window;
    private PrivateObjectiveCard po;
    private int favorPoints;

    public ConcretePlayer(String username){
        this.username=username;
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
        return window;
    }

    @Override
    public PrivateObjectiveCard getPrivateObjective() {
        return po;
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
        return sb.toString();
    }
}
