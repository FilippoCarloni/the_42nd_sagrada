package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.utility.Parameters;

import static it.polimi.ingsw.model.utility.ExceptionMessage.INDEX_OUT_OF_BOUND;

/**
 * Used to make the rule composition easier.
 * Placing rules follow a decorator pattern.
 */
abstract class RuleDecorator implements Rule {

    Rule decoratedRule;

    /**
     * Throws exception when the passed coordinates exceed the window frame bounds.
     * @param row Row index
     * @param column Column index
     */
    static void checkLegalCoordinates(int row, int column) {
        if (row < 0 || column < 0 || row >= Parameters.MAX_ROWS || column >= Parameters.MAX_COLUMNS)
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUND);
    }

    RuleDecorator(Rule decoratedRule) {
        this.decoratedRule = decoratedRule;
    }
}
