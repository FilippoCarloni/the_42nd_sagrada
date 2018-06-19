package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.commands.rules.ColorRule;
import it.polimi.ingsw.model.commands.rules.PlacingRule;
import it.polimi.ingsw.model.commands.rules.ShadeRule;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import static it.polimi.ingsw.model.commands.ErrorMessage.*;

/**
 * Checks if the die can be placed in a particular position.
 */
public class Place implements Condition {

    private boolean placing;
    private boolean color;
    private boolean shade;
    private String errorMessage = ERR_RULE_ERROR;

    /**
     * Generates a new place-checker instance.
     * @see it.polimi.ingsw.model.commands.rules.Rule
     * @param placing True if the Placing rule should be followed
     * @param color True if the Color rule should be followed
     * @param shade True if the Shade rule should be followed
     */
    public Place(boolean placing, boolean color, boolean shade) {
        this.placing = placing;
        this.color = color;
        this.shade = shade;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> {
            boolean value = true;
            WindowFrame window = gameData.getTurnManager().getCurrentPlayer().getWindowFrame();
            Die die = gameData.getPickedDie();
            if (placing) {
                value = new PlacingRule().canBePlaced(die, window, args[0], args[1]);
                if (!value)
                    errorMessage = ERR_PLACING_ERROR;
            }
            if (value && color) {
                value = new ColorRule().canBePlaced(die, window, args[0], args[1]);
                if (!value)
                    errorMessage = ERR_COLOR_ERROR;
            }
            if (value && shade) {
                value = new ShadeRule().canBePlaced(die, window, args[0], args[1]);
                errorMessage = ERR_SHADE_ERROR;
            }
            return value;
        };
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
