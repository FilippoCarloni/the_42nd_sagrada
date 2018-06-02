package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.CommandFactory;
import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaperToolCard extends AbstractCard implements ToolCard {

    private int favorPoints;
    private boolean activeEffect;
    private JSONObject activator;
    private JSONArray commands;

    PaperToolCard(String name, String description, int id, boolean activeEffect, JSONObject activator, JSONArray commands) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.activeEffect = activeEffect;
        this.activator = activator;
        this.commands = commands;
    }

    @Override
    public boolean isEffectActive() {
        return activeEffect;
    }

    @Override
    public int getFavorPoints() {
        return favorPoints;
    }

    @Override
    public void addFavorPoints() {
        favorPoints = favorPoints == 0 ? favorPoints + 1 : favorPoints + 2;
    }

    @Override
    public Command getActivator(Player player, GameData gameData, String cmd) {
        return CommandFactory.getToolActivator(id, activator, player, gameData, cmd);
    }

    @Override
    public List<Command> getCommands(Player player, GameData gameData, String cmd) {
        List<Command> parsedCommands = new ArrayList<>();
        if (commands != null) {
            for (Object o : commands)
                parsedCommands.add(CommandFactory.getCommand((JSONObject) o, player, gameData, cmd));
        }
        return parsedCommands;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj =  super.encode();
        obj.put(JSONTag.TOOL_FAVOR_POINTS, favorPoints);
        return obj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|(FP:").append(favorPoints).append(")");
        for (int i = 0; i < pixelWidth - 6; i++) sb.append(" ");
        sb.append("|\n");
        sb.append("|(ID:").append(id).append(")");
        for (int i = 0; i < pixelWidth - 6; i++) sb.append(" ");
        sb.append("|\n\n").append(getLowerCard());
        return sb.toString();
    }
}
