package it.polimi.ingsw.connection.server.serverexception;

/**
 * The ServerException is the class that rapresents all the Exception in the Server.
 */
public class ServerException extends Exception {

    /**
     * Create an ServerException from the mesagge.
     * @param message - String that contains the error.
     */
    public ServerException(String message) {
        super(message);
    }

}
