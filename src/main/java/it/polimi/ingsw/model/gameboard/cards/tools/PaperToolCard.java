package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.CommandFactory;
import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
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
        obj.put(JSONTag.FAVOR_POINTS, favorPoints);
        return obj;
    }
}
