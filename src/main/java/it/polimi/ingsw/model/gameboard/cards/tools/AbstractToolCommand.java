package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_NOT_ACTIVE;

abstract class AbstractToolCommand extends AbstractCommand {

    AbstractToolCommand(Player player, GameData gameData, String cmd, int id) {
        super(player, gameData, cmd);
        addCondition(new Condition(gameData, getArgs(),
                (gd, args) -> gameData.getActiveToolID() == id || gameData.getPassiveToolID() == id, ERR_TOOL_NOT_ACTIVE));
    }
}
