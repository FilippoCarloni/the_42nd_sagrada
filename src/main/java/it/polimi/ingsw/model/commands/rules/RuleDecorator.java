package it.polimi.ingsw.model.commands.rules;

abstract class RuleDecorator implements Rule {

    Rule decoratedRule;

    RuleDecorator(Rule decoratedRule) {
        this.decoratedRule = decoratedRule;
    }
}
