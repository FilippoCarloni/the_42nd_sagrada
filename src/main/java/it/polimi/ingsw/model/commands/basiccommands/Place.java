package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.conditions.*;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INVALID_COORDINATES;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_RULE_ERROR;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SLOT_ALREADY_OCCUPIED;

public class Place extends AbstractCommand {

    public Place(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(new DiePicked(gameData));
        addCondition(new DieNotPlaced(gameData));
        addCondition(new ToolNotActive(gameData));
        addCondition(new Condition(gameData, getArgs(),
                (gs, args) -> validateCoordinates(args[0], args[1]),
                ERR_INVALID_COORDINATES));
        addCondition(new Condition(gameData, getArgs(),
                (gs, args) -> gs.getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(args[0], args[1]),
                ERR_SLOT_ALREADY_OCCUPIED));
        addCondition(new Condition(gameData, getArgs(),
                (gs, args) -> Rule.checkAllRules(getGameData().getPickedDie(), getGameData().getTurnManager().getCurrentPlayer().getWindowFrame(), getArgs()[0], getArgs()[1]),
                ERR_RULE_ERROR));
    }

    private boolean validateCoordinates(int row, int column) {
        return Coordinate.validateRow(row) && Coordinate.validateColumn(column);
    }

    @Override
    public String getRegExp() {
        return "place \\d \\d";
    }

    @Override
    public void executionWhenLegal() {
        getGameData().getTurnManager().getCurrentPlayer().getWindowFrame()
                .put(getGameData().getPickedDie(), getArgs()[0], getArgs()[1]);
        getGameData().setPickedDie(null);
        getGameData().setDiePlaced(true);
    }

    @Override
    public boolean undoable() {
        return true;
    }
}
