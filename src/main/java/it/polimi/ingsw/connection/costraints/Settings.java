package it.polimi.ingsw.connection.costraints;

import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;

public  class Settings{
    public static final int MAX_PLAYER_GAME = 4;
    public static final int MIN_PLAYER_GAME = 2;

    private static final int DEFAULT_RMI_PORT=8002;
    private static final int DEFAULT_SOCKET_PORT=8001;
    private static final String DEFAULT_SERVER_IP="localhost";
    private static final String FILE_CONFIG="src/main/java/res/network_config/constraints.config";
    private static final String IP_SERVER_TAG="IP_SERVER";
    private static final String PORT_SOCKET_TAG="PORT_SOCKET";
    private static final String PORT_RMI_TAG="PORT_RMI";
    private static final String LOBBY_REFRESH_TIMR_TAG="LOBBY_REFRESH_TIME";
    private static final int DEFAULT_LOBBY_REFRESH_TIME=500;
    private static final int DEFAULT_TURN_TIME=60000;
    private static final String TURN_TIME_TAG="TURN_TIME";
    private static final String GAME_REFRESH_TAG = "GAME_REFRESH";
    private static final int DEFAULT_GAME_REFRESH = 101;
    private static final String LOBBY_WAITING_TIME = "LOBBY_WAITING_TIME";
    private static final int DEFAULT_LOBBY_WAITING_TIME = 9000;
    private static final Logger logger=Logger.getLogger(Settings.class.getName());
    public Settings() {
        int rmiPort = DEFAULT_RMI_PORT;
        String ipserver = DEFAULT_SERVER_IP;
        int socketPort = DEFAULT_SOCKET_PORT;
        int lobbyRefresh = DEFAULT_LOBBY_REFRESH_TIME;
        int turnTimeRefresh = DEFAULT_TURN_TIME;
        int gameRefreshTime = DEFAULT_GAME_REFRESH;
        int lobbyTime = DEFAULT_LOBBY_WAITING_TIME;
        Properties configFile;
        configFile = new java.util.Properties();
        FileReader reader;
        String param;
        try {
            reader = new FileReader(FILE_CONFIG);
            configFile.load(reader);
            param = configFile.getProperty(IP_SERVER_TAG);
            if (param != null)
                ipserver = param;
            param = configFile.getProperty(PORT_SOCKET_TAG);
            if (param != null)
                socketPort = Integer.parseInt(param);
            param = configFile.getProperty(PORT_RMI_TAG);
            if (param != null)
                rmiPort = Integer.parseInt(param);
            param = configFile.getProperty(LOBBY_REFRESH_TIMR_TAG);
            if (param != null)
                lobbyRefresh = Integer.parseInt(param);
            param = configFile.getProperty(TURN_TIME_TAG);
            if (param != null)
                turnTimeRefresh = Integer.parseInt(param);
            param = configFile.getProperty(GAME_REFRESH_TAG);
            if (param != null)
                gameRefreshTime = Integer.parseInt(param);
            param = configFile.getProperty(LOBBY_WAITING_TIME);
            if (param != null)
                lobbyTime = Integer.parseInt(param);
            reader.close();
        } catch (Exception eta) {
            logger.warning("Error found in the configuration file");
        }
        RMI_PORT = rmiPort;
        IP_SERVER = ipserver;
        SOCKET_PORT = socketPort;
        lobbyRefreshTime=lobbyRefresh;
        turnTime = turnTimeRefresh;
        gameRefresh = gameRefreshTime;
        lobbyWaitingTime = lobbyTime;
    }
    /**
     * Default Server port number for Registry connection
     *
     */
    public final int RMI_PORT;
    /**
     * Serial Version Universal Identificator for the Session object
     */
    public static final long SERIAL_VERSION_UID_SESSION = 1190476516911661470L;

    /**
     * The default waiting time in nanosecond to start a match with less than 4 players
     */
    public final int lobbyWaitingTime;
    public static final long SERIAL_VERSION_CLIENTSTATUS = 1190476517382928173L;
    public final int SOCKET_PORT;
    public final String IP_SERVER;
    public static final String ANONYMOUS = "ANONYMOUS";
    public final int lobbyRefreshTime;
    public final int turnTime;
    public final int gameRefresh;
    public static final String LOBBY_RMI_ID = "Lobby";
}
