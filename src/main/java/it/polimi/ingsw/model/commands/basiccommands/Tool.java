package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.FavorPointsCheck;
import it.polimi.ingsw.model.commands.conditions.ToolNotActivated;
import it.polimi.ingsw.model.commands.conditions.ToolNotActive;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Parameters;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_BIG;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_SMALL;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_NOT_USABLE;

public class Tool extends AbstractCommand {

    public Tool(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(new ToolNotActivated(gameData));
        addCondition(new ToolNotActive(gameData));
        addCondition(new Condition(gameData, getArgs(),
                (gd, args) -> args[0] >= 0, ERR_INDEX_TOO_SMALL));
        addCondition(new Condition(gameData, getArgs(),
                (gd, args) -> args[0] < Parameters.TOOL_CARDS, ERR_INDEX_TOO_BIG));
        addCondition(new Condition(gameData, getArgs(),
                (gd, args) -> gd.getTools().get(args[0]).isLegal(gameData), ERR_TOOL_NOT_USABLE));
        addCondition(new FavorPointsCheck(gameData, getArgs()));
    }

    @Override
    public String getRegExp() {
        return "tool \\d";
    }

    @Override
    public void executionWhenLegal() {
        int id = getGameData().getTools().get(getArgs()[0]).getID();
        int cardFavorPoints = getGameData().getTools().get(getArgs()[0]).getFavorPoints();
        getGameData().setActiveToolID(id);
        getGameData().setPassiveToolID(id);
        getGameData().setToolActivated(true);
        getGameData().getTools().get(getArgs()[0]).addFavorPoints();
        if (cardFavorPoints == 0)
            getGameData().getTurnManager().getCurrentPlayer().setFavorPoints(getGameData().getTurnManager().getCurrentPlayer().getFavorPoints() - 1);
        else
            getGameData().getTurnManager().getCurrentPlayer().setFavorPoints(getGameData().getTurnManager().getCurrentPlayer().getFavorPoints() - 2);
        getGameData().getTools().get(getArgs()[0]).execute(getGameData());
    }

}
