package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.ErrorMessage;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.GameData;

import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_NO_SHARING_DIE_1;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_NO_SHARING_DIE_2;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;

/**
 * Checks if the die present in a particular pool shares a particular feature.
 */
public class Share implements Condition {

    private String object;
    private String pool;
    private boolean valueIfEmpty;

    /**
     * Generates a new share-checker from the given parameters.
     * @param object Feature to be shared (color or shade)
     * @param pool Pool from which take the die (dice pool, round track, dice moved pool)
     * @param valueIfEmpty Value to be returned when the pool doesn't contain any die
     */
    public Share(String object, String pool, boolean valueIfEmpty) {
        this.object = object;
        this.pool = pool;
        this.valueIfEmpty = valueIfEmpty;
    }

    private List<Die> getPool(GameData gameData) {
        switch (pool) {
            case ROUND_TRACK:
                return gameData.getRoundTrack().getDice();
            case DICE_MOVED:
                return gameData.getDiceMoved();
            case DICE_POOL:
                return gameData.getDicePool();
            default:
        }
        throw new IllegalArgumentException(BAD_JSON);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> {
            Die die = gameData.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(args[0], args[1]);
            List<Die> dice = getPool(gameData);
            if (dice.isEmpty()) return valueIfEmpty;
            if (object.equals(SHADE))
                return dice.stream().map(Die::getShade).collect(Collectors.toList()).contains(die.getShade());
            else if (object.equals(COLOR))
                return dice.stream().map(Die::getColor).collect(Collectors.toList()).contains(die.getColor());
            throw new IllegalArgumentException(BAD_JSON);
        };
    }

    @Override
    public String getErrorMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(ERR_NO_SHARING_DIE_1);
        if (object.equals(COLOR)) sb.append(ErrorMessage.COLOR);
        else if (object.equals(SHADE)) sb.append(ErrorMessage.SHADE);
        sb.append(ERR_NO_SHARING_DIE_2);
        switch (pool) {
            case ROUND_TRACK:
                sb.append(ErrorMessage.ROUND_TRACK);
                break;
            case DICE_POOL:
                sb.append(ErrorMessage.DICE_POOL);
                break;
            case DICE_MOVED:
                sb.append(ErrorMessage.DICE_MOVED_POOL);
                break;
            default:
        }
        sb.append(".");
        return sb.toString();
    }
}
