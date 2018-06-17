package it.polimi.ingsw.model.commands;

/**
 * This is the kind of Exception thrown when a command that is not rule-compliant is
 * given to the Game model. It must be catch externally.
 * The Exception message provides a brief log of the reason of the command failure.
 */
public class IllegalCommandException extends Exception {

    public IllegalCommandException(String message) {
        super(message);
    }
}
