package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.Session;

import java.io.Serializable;

class ClientStatus implements Serializable {
    private static final long serialVersionUID = Settings.SERIAL_VERSION_CLIENTSTATUS;
    private String username;
    private Session sesssion;
    ClientStatus(Session session, String username){
        this.sesssion=session;
        this.username=username;
    }

    String getUsername() {
        return username;
    }

    Session getSesssion() {
        return sesssion;
    }
}
