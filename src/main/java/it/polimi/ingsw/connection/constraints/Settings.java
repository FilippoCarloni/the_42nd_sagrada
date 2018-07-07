package it.polimi.ingsw.connection.constraints;

import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;


import static it.polimi.ingsw.connection.constraints.ServerMessages.NOT_FOUND_CONFIG_FILE;

/**
 * The Settings class contains all the general settings for the connections, game and others settings for the connection package.
 * The values of the settings are loaded by the config file, if it is not found the default values are loaded.
 *
 */
public  class Settings{
    public static final int MAX_PLAYER_GAME = 4;
    public static final int MIN_PLAYER_GAME = 2;

    public final int rmiPort;
    public static final long SERIAL_VERSION_UID_SESSION = 1190476516911661470L;

    public final int lobbyWaitingTime;
    public final int socketPort;
    public final String serverIP;
    public static final String ANONYMOUS = "ANONYMOUS";
    public final int lobbyRefreshTime;
    public final int turnTime;
    public final int gameRefresh;
    public final int windowWaitingTime;
    public static final String LOBBY_RMI_ID = "Lobby";
    public final String clientIP;

    private static final int DEFAULT_RMI_PORT=8002;
    private static final int DEFAULT_SOCKET_PORT=8001;
    private static final String DEFAULT_SERVER_IP="localhost";
    private static final String FILE_CONFIG="src/main/java/res/network_config/constraints.config";
    private static final String IP_SERVER_TAG="IP_SERVER";
    private static final String PORT_SOCKET_TAG="PORT_SOCKET";
    private static final String PORT_RMI_TAG="PORT_RMI";
    private static final String LOBBY_REFRESH_TIMER_TAG="LOBBY_REFRESH_TIME";
    private static final int DEFAULT_LOBBY_REFRESH_TIME=500;
    private static final int DEFAULT_TURN_TIME=60000;
    private static final String TURN_TIME_TAG="TURN_TIME";
    private static final String GAME_REFRESH_TAG = "GAME_REFRESH";
    private static final int DEFAULT_GAME_REFRESH = 101;
    private static final String LOBBY_WAITING_TIME_TAG = "LOBBY_WAITING_TIME";
    private static final int DEFAULT_LOBBY_WAITING_TIME = 9000;
    private static final int DEFAULT_WINDOW_TIMER = 10000;
    private static final String WINDOW_WAITING_TIME_TAG = "WINDOW_WAITING_TIME";
    private static final String IP_CLIENT_DEFAULT = "localhost";
    private static final String IP_CLIENT_TAG = "IP_CLIENT";
    private static final Logger logger=Logger.getLogger(Settings.class.getName());

    public Settings() {
        int rmiPortConnection = DEFAULT_RMI_PORT;
        String ipserver = DEFAULT_SERVER_IP;
        int lobbyRefresh = DEFAULT_LOBBY_REFRESH_TIME;
        int turnTimeRefresh = DEFAULT_TURN_TIME;
        int gameRefreshTime = DEFAULT_GAME_REFRESH;
        int lobbyTime = DEFAULT_LOBBY_WAITING_TIME;
        int windowTime = DEFAULT_WINDOW_TIMER;
        String clientIPdefault = IP_CLIENT_DEFAULT;
        Properties configFile;
        configFile = new java.util.Properties();
        FileReader reader;
        String param;
        int socketPortConnection = DEFAULT_SOCKET_PORT;
        try {
            reader = new FileReader(FILE_CONFIG);
            configFile.load(reader);
            param = configFile.getProperty(IP_SERVER_TAG);
            if (param != null)
                ipserver = param;
            param = configFile.getProperty(PORT_SOCKET_TAG);
            if (param != null)
                socketPortConnection = Integer.parseInt(param);
            param = configFile.getProperty(PORT_RMI_TAG);
            if (param != null)
                rmiPortConnection = Integer.parseInt(param);
            param = configFile.getProperty(LOBBY_REFRESH_TIMER_TAG);
            if (param != null)
                lobbyRefresh = Integer.parseInt(param);
            param = configFile.getProperty(TURN_TIME_TAG);
            if (param != null)
                turnTimeRefresh = Integer.parseInt(param);
            param = configFile.getProperty(GAME_REFRESH_TAG);
            if (param != null)
                gameRefreshTime = Integer.parseInt(param);
            param = configFile.getProperty(LOBBY_WAITING_TIME_TAG);
            if (param != null)
                lobbyTime = Integer.parseInt(param);
            reader.close();
            param = configFile.getProperty(WINDOW_WAITING_TIME_TAG);
            if (param != null)
                windowTime = Integer.parseInt(param);
            param = configFile.getProperty(IP_CLIENT_TAG);
            if (param != null)
                clientIPdefault = param;
            reader.close();
        } catch (Exception eta) {
            logger.warning(NOT_FOUND_CONFIG_FILE);
        }
        clientIP = clientIPdefault;
        rmiPort= rmiPortConnection;
        windowWaitingTime = windowTime;
        serverIP = ipserver;
        socketPort = socketPortConnection;
        lobbyRefreshTime=lobbyRefresh;
        turnTime = turnTimeRefresh;
        gameRefresh = gameRefreshTime;
        lobbyWaitingTime = lobbyTime;
    }
    

}
