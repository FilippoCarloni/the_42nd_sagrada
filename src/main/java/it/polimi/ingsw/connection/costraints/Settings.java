package it.polimi.ingsw.connection.costraints;

import java.io.FileReader;
import java.util.Properties;

public  class Settings{
    private static final int DEFAULT_RMI_PORT=8002;
    private static final int DEFAULT_SOCKET_PORT=8001;
    private static final String DEFAULT_SERVER_IP="127.0.0.1";
    private static final String FILE_CONFIG="./src/main/java/res/network_config/constraints.config";
    private static final String IP_SERVER_TAG="IP_SERVER";
    private static final String PORT_SOCKET_TAG="PORT_SOCKET";
    private static final String PORT_RMI_TAG="PORT_RMI";
    
    public Settings() {
        int rmiPort=DEFAULT_RMI_PORT;
        String ipserver=DEFAULT_SERVER_IP;
        int socketPort=DEFAULT_SOCKET_PORT;
        Properties configFile;
        configFile = new java.util.Properties();
        FileReader reader;
        String param;
        try {
            reader = new FileReader(FILE_CONFIG);
            configFile.load(reader);
            param=configFile.getProperty(IP_SERVER_TAG);
            if(param != null)
                ipserver=param;
            param=configFile.getProperty(PORT_SOCKET_TAG);
            if(param != null)
                socketPort=Integer.parseInt(param);
            param=configFile.getProperty(PORT_RMI_TAG);
            if(param != null)
                rmiPort=Integer.parseInt(param);
            reader.close();
        }catch(Exception eta){
        }
        RMI_PORT=rmiPort;
        IP_SERVER=ipserver;
        SOCKET_PORT=socketPort;
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
    public static final int WAITINGTIMETOMATCH=9000;
    public static final long SERIAL_VERSION_CLIENTSTATUS = 1190476517382928173L;
    public final int SOCKET_PORT;
    public final String IP_SERVER;
    public static final String ANONYMOUS = "ANONYMOUS";
}
