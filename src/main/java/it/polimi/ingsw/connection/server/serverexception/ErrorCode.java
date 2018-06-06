package it.polimi.ingsw.connection.server.serverexception;

public class ErrorCode {
    private ErrorCode() {
        throw new UnsupportedOperationException();
    }
    public static final int SERVER_ERROR=500;
    public static final int GAME_ERROR=300;
    public static final int RMI_ERROR=400;
}
