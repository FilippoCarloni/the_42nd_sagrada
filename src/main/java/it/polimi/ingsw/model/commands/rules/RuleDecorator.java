package it.polimi.ingsw.model.commands.rules;

/**
 * Used to make the rule composition easier.
 * Placing rules follow a decorator pattern.
 */
abstract class RuleDecorator implements Rule {

    Rule decoratedRule;

    RuleDecorator(Rule decoratedRule) {
        this.decoratedRule = decoratedRule;
    }
}
