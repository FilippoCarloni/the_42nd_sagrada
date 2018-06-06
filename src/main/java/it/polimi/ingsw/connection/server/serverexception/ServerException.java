package it.polimi.ingsw.connection.server.serverexception;

public class ServerException extends Exception {
    private final int errorCode;
    public ServerException(String message, int errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    @Override
    public String getMessage() {
        return "["+errorCode+"]: "+super.getMessage();
    }
}
