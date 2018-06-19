package it.polimi.ingsw.model.commands;

/**
 * This is the kind of Exception thrown when a command that is not rule-compliant is
 * given to the Game model. It must be catch externally.
 * The Exception message provides a brief log of the reason of the command failure.
 */
public class IllegalCommandException extends Exception {

    /**
     * Generates a new illegal command exception.
     * @param message Tells what's the reason of the failure according to Sagrada rules
     */
    public IllegalCommandException(String message) {
        super(message);
    }
}
