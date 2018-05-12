package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class AbstractCommand implements Command {

    ConcreteGameStatus status;
    String cmd;
    String regExp;
    Predicate<String[]> legalPredicate;

    AbstractCommand(ConcreteGameStatus status, String string) {
        if (status == null || string == null)
            throw new NullPointerException("Command must be initialized with not null parameters.");
        this.status = status;
        this.cmd = string;
    }

    @Override
    public boolean isValid() {
        if (regExp == null)
            throw new NullPointerException("Missing regular expression for command validation.");
        return Pattern.compile(regExp).asPredicate().test(cmd);
    }

    @Override
    public boolean isLegal() {
        if (legalPredicate == null)
            throw new NullPointerException("Missing legal predicate for command.");
        return isValid() && legalPredicate.test(cmd.split(" "));
    }
}
