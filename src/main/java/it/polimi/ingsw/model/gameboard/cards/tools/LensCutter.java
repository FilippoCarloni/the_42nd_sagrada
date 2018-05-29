package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Color;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.commands.ErrorMessage.*;

public class LensCutter extends AbstractToolCard {

    LensCutter() {
        super(5);
        name = "Lens Cutter";
        description = "After drafting, swap the drafted die with a die from the Round Track.";
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
        commands.add(new Select(player, gameData, cmd));
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
        sb.append("[5]").append(Color.YELLOW.paint("[⚂]")).append("[7]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("    ⬍    ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("   [⚃]   "));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class Select extends AbstractToolCommand {

        Select(Player player, GameData gameData, String cmd) {
            super(player, gameData, cmd, id);
            addCondition(new Condition(getGameData(), getArgs(),
                    (gd, args) -> args[0] >= 0, ERR_INDEX_TOO_SMALL));
            addCondition(new Condition(getGameData(), getArgs(),
                    (gd, args) -> args[0] < gd.getRoundTrack().getDice().size(), ERR_INDEX_TOO_BIG));
        }

        @Override
        public String getRegExp() {
            return "select \\d+";
        }

        @Override
        public void executionWhenLegal() {
            Die playerDie = getGameData().getPickedDie();
            Die roundTrackDie = getGameData().getRoundTrack().getDice().get(getArgs()[0]);
            getGameData().getRoundTrack().swap(playerDie, roundTrackDie);
            getGameData().setPickedDie(roundTrackDie);
            ToolCard.tearDown(getGameData());
        }

        @Override
        public boolean undoable() {
            return true;
        }
    }
}
