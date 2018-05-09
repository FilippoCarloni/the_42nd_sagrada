package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.costraints.Settings;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI {
    private int port;
    private Registry registry;
    private Logger logger= Logger.getLogger(ServerRMI.class.getName());
    private String url = "rmi://localhost:";
    public ServerRMI()
    {
        this.port=Settings.RMI_PORT;
        setupRegistry();
    }

    private void setupRegistry(){
        try {
            registry = LocateRegistry.createRegistry(port);
            //System.setProperty("java.security.policy","./project_demo/src/connection/rmi/server.policy");
            //System.setSecurityManager(new SecurityManager());
            this.url += this.port+"/";
        }catch(Exception e){
            logger.log(Level.SEVERE, "Error in creating the rmi registry", e);
        }

    }

    public void addSkeleton(String name,Remote obj) throws RemoteException, MalformedURLException, AlreadyBoundException {

        Naming.bind(url + name, obj);

    }
    public String getURL() {
        return this.url;
    }
}
