package it.polimi.ingsw.connection.costraints;
public class Settings{
    private Settings() {
    }
    /**
     * Default Server port number for Registry connection
     *
     */
    public static final int RMI_PORT=8002;
    /**
     * Serial Version Universal Identificator for the Session object
     */
    public static final long SERIAL_VERSION_UID_SESSION = 1190476516911661470L;

    /**
     * The default waiting time in nanosecond to start a match with less than 4 players
     */
    public static final int WAITINGTIMETOMATCH=3000;
    public static final long SERIAL_VERSION_CLIENTSTATUS = 1190476517382928173L;
    public static final int SOCKET_PORT=8001;
}
