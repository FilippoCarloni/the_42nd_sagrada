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
 * Checks if the movement command is rule-compliant.
 */
public class Move implements Condition {

    private boolean placing;
    private boolean color;
    private boolean shade;
    private String errorMessage = ERR_RULE_ERROR;

    /**
     * Generates a new movement-checker.
     * @see it.polimi.ingsw.model.commands.rules.Rule
     * @param placing True if the Placing rule should be checked
     * @param color True if the Color rule should be checked
     * @param shade True if the Shade rule should be checked
     */
    public Move(boolean placing, boolean color, boolean shade) {
        this.placing = placing;
        this.color = color;
        this.shade = shade;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> {
            boolean value = true;
            WindowFrame window = gameData.getTurnManager().getCurrentPlayer().getWindowFrame();
            Die die = window.pick(args[0], args[1]);
            if (placing) {
                value = new PlacingRule().canBePlaced(die, window, args[2], args[3]);
                errorMessage = setError(value, ERR_PLACING_ERROR);
            }
            if (value && color) {
                value = new ColorRule().canBePlaced(die, window, args[2], args[3]);
                errorMessage = setError(value, ERR_COLOR_ERROR);
            }
            if (value && shade) {
                value = new ShadeRule().canBePlaced(die, window, args[2], args[3]);
                errorMessage = setError(value, ERR_SHADE_ERROR);
            }
            gameData.getTurnManager().getCurrentPlayer().getWindowFrame().put(die, args[0], args[1]);
            return value;
        };
    }

    private String setError(boolean value, String error) {
        return value ? errorMessage : error;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
