package it.polimi.ingsw.model.commands.conditions.argconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIFFERENT_INDEX;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_BIG;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_SMALL;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;

public class ArgComparison implements Condition {

    private static final String DEFAULT_ERROR_MESSAGE = "Bad JSON format.";
    private String typeOfComparison;
    private int index;
    private String bound;

    public ArgComparison(String typeOfComparison, int index, String bound) {
        this.typeOfComparison = typeOfComparison;
        this.index = index;
        this.bound = bound;
    }

    private int getBound(GameData gameData) {
        switch (bound) {
            case DICE_POOL_SIZE:
                return gameData.getDicePool().size();
            case ROUND_TRACK_SIZE:
                return gameData.getRoundTrack().getDice().size();
            case TOOL_DECK_SIZE:
                return new ToolDeck().size();
            case ZERO:
                return 0;
            default:
        }
        throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public ConditionPredicate getPredicate() {
        switch (typeOfComparison) {
            case GREATER_THAN:
                return (gd, args) -> args[index] >= getBound(gd);
            case SMALLER_THAN:
                return (gd, args) -> args[index] < getBound(gd);
            case EQUAL_TO:
                return (gd, args) -> args[index] == getBound(gd);
            default:
        }
        throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public String getErrorMessage() {
        switch (typeOfComparison) {
            case GREATER_THAN:
                return ERR_INDEX_TOO_SMALL;
            case SMALLER_THAN:
                return ERR_INDEX_TOO_BIG;
            case EQUAL_TO:
                return ERR_DIFFERENT_INDEX;
            default:
        }
        throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE);
    }
}
