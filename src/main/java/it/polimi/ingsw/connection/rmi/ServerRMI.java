package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.constraints.Settings;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.rmi.RMIMessage.ERROR_CREATING_RMI_REGISTRY;

/**
 * The ServerRMI class permits to manage an rmi server.
 */
public class ServerRMI {
    private int port;
    private Registry registry;
    private Logger logger= Logger.getLogger(ServerRMI.class.getName());
    private String url = "rmi://"+new Settings().serverIP+":";
    public ServerRMI()
    {
        this.port=new Settings().rmiPort;
        setupRegistry();
    }

    /**
     * Sets all the necessary configurations to setup an rmi registry.
     */
    private void setupRegistry(){
        try {
            registry = LocateRegistry.createRegistry(port);
            System.setProperty("java.rmi.server.hostname",new Settings().serverIP);
            // System.setProperty("java.security.policy","./src/main/java/res/network_config/server.policy")
          //  System.setSecurityManager(new SecurityManager())
            this.url += this.port+"/";
        }catch(Exception e){
            logger.log(Level.SEVERE, ERROR_CREATING_RMI_REGISTRY, e);
        }

    }

    /**
     *
     * @param name - Public name of the skeleton.
     * @param obj - Remote object to bind.
     * @throws RemoteException - Throws if there are problems in the bind action.
     * @throws AlreadyBoundException - Throws if the are already bind object that corresponds to the name and obj couple.
     */
    public void addSkeleton(String name,Remote obj) throws RemoteException, AlreadyBoundException {
        registry.bind(name,obj);
    }

    /**
     *
     * @return - The URL where are bounded the objects.
     */
    public String getURL() {
        return this.url;
    }
}
