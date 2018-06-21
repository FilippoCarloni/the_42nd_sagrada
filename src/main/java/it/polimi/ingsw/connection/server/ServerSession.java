package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;


public class ServerSession implements Serializable {
    private static final long serialVersionUID = Settings.SERIAL_VERSION_UID_SESSION;
    private String sessionID;

    ServerSession(String id) {
        Date date=new Date();
        sessionID = id+date.getTime()+(int)(new Random().nextDouble() * 10000000);
    }
    public String getID() {
        return sessionID;
    }

}
