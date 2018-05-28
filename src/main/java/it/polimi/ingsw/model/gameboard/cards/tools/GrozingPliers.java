package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Shade;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.commands.ErrorMessage.*;

public class GrozingPliers extends AbstractToolCard {

    GrozingPliers() {
        super(1);
        name = "Grozing Pliers";
        description = "After drafting, increase or decrease the value of the drafted die by 1. " +
                "1 may not change to 6, or 6 to 1.";
    }

    @Override
    public boolean isLegal(GameData gameData) {
        return gameData.getPickedDie() != null;
    }

    @Override
    public void execute(GameData gameData) {
        // This tool card doesn't need any activation action
    }

    @Override
    public List<Command> getCommands(Player player, GameData gameData, String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new Increase(player, gameData, cmd));
        commands.add(new Decrease(player, gameData, cmd));
        return commands;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        sb.append("(FP:").append(getFavorPoints()).append(")");
        for (int i = 0; i < pixelWidth - 6; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚂] ➡ [⚃]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚂] ➡ [⚁]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚀] x [⚅]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class Increase extends AbstractToolCommand {

        Increase(Player player, GameData gameData, String cmd) {
            super(player, gameData, cmd, id);
            addCondition(new Condition(gameData, getArgs(),
                    (gd, args) -> !gd.getPickedDie().getShade().equals(Shade.DARKEST), ERR_CANNOT_INCREASE));
        }

        @Override
        public String getRegExp() {
            return "increase";
        }

        @Override
        public void executionWhenLegal() {
            getGameData().getPickedDie().setShade(Shade.findByValue(getGameData().getPickedDie().getShade().getValue() + 1));
            ToolCard.tearDown(getGameData());
        }

        @Override
        public boolean undoable() {
            return true;
        }
    }

    private class Decrease extends AbstractToolCommand {

        Decrease(Player player, GameData gameData, String cmd) {
            super(player, gameData, cmd, id);
            addCondition(new Condition(gameData, getArgs(),
                    (gd, args) -> !gd.getPickedDie().getShade().equals(Shade.LIGHTEST), ERR_CANNOT_DECREASE));
        }

        @Override
        public String getRegExp() {
            return "decrease";
        }

        @Override
        public void executionWhenLegal() {
            getGameData().getPickedDie().setShade(Shade.findByValue(getGameData().getPickedDie().getShade().getValue() - 1));
            ToolCard.tearDown(getGameData());
        }

        @Override
        public boolean undoable() {
            return true;
        }
    }
}
