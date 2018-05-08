package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.Session;

import java.io.Serializable;

public class ClientStatus implements Serializable {
    private static final long serialVersionUID = Settings.SERIAL_VERSION_UID_SESSION;
    private String username;
    private Session sesssion;
    public ClientStatus(Session session, String username){
        this.sesssion=session;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public Session getSesssion() {
        return sesssion;
    }
}
