package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class AbstractCommand implements Command {

    private ConcreteGameStatus status;
    private String cmd;
    private String regExp;
    private Predicate<String[]> legalPredicate;

    public AbstractCommand(ConcreteGameStatus status, String string) {
        if (status == null || string == null)
            throw new NullPointerException("Command must be initialized with not null parameters.");
        this.status = status;
        this.cmd = string;
    }

    protected ConcreteGameStatus getStatus() {
        return status;
    }

    protected String getCmd() {
        return cmd;
    }

    protected void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    protected void setLegalPredicate(Predicate<String[]> legalPredicate) {
        this.legalPredicate = legalPredicate;
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
