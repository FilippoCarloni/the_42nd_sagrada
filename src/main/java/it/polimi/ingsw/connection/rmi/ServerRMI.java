package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.costraints.Settings;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

public class ServerRMI {
    int port;
    Registry registry;
    private String url = "rmi://localhost:";
    public ServerRMI()
    {
        this.port=Settings.PORT;
        setupRegistry();
    }
    public ServerRMI(int port)
    {
        this.port=port;
        setupRegistry();

    }
    private void setupRegistry(){
        try {
            registry = LocateRegistry.createRegistry(port);
            //System.setProperty("java.security.policy","./project_demo/src/connection/rmi/server.policy");
            //System.setSecurityManager(new SecurityManager());
            this.url += this.port+"/";
        }catch(Exception e){
            System.out.println("Error in creating the rmi registry");
            e.printStackTrace();
        }

    }
    public void addSkeleton(String name,Remote obj) throws RemoteException, MalformedURLException, AlreadyBoundException {

        Naming.bind(url + name, obj);

    }
    public String getURL() {
        return this.url;
    }
}
