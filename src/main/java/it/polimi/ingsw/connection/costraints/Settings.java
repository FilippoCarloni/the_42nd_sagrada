package it.polimi.ingsw.connection.costraints;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public  class Settings{
    public Settings() {
        int port_rmi=8002;
        String ip_server="127.0.0.1";
        int socket_port=8001;
        FileReader fileReader=null;
        Scanner scanner=null;
        String param;
        try {
            fileReader= new FileReader("./src/main/java/res/network_config/constraints.config");
            scanner= new Scanner(fileReader);
            ip_server=scanner.nextLine().split(":")[1];
            param=scanner.nextLine().split(":")[1];
            socket_port=Integer.parseInt(param);
            param=scanner.nextLine().split(":")[1];
            port_rmi=Integer.parseInt(param);


        } catch (FileNotFoundException e) {
        }finally {
            if(fileReader!=null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                }
            }
            if(scanner!=null) {
                scanner.close();
            }
        }
        RMI_PORT=port_rmi;
        IP_SERVER=ip_server;
        SOCKET_PORT=socket_port;
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
    public static final int WAITINGTIMETOMATCH=3000;
    public static final long SERIAL_VERSION_CLIENTSTATUS = 1190476517382928173L;
    public final int SOCKET_PORT;
    public final String IP_SERVER;
    public static final String ANONYMOUS = "ANONYMOUS";
}
