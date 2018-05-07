package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public class ConcretePlayer implements Player{

    private String username;
    private WindowFrame window;
    private PrivateObjectiveCard po;

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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String frame = window.toString();
        int len = frame.indexOf('\n');
        for (int i = 0; i < len; i++)
            sb.append("-");
        sb.append("\n");
        for (int i = 0; i < (len - username.length()) / 2; i++)
            sb.append(" ");
        sb.append(username);
        for (int i = 0; i < len - username.length() - (len - username.length()) / 2; i++)
            sb.append(" ");
        sb.append("\n");
        for (int i = 0; i < len; i++)
            sb.append("-");
        sb.append("\n");
        sb.append(frame);
        return sb.toString();
    }
}
