package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

import java.io.Serializable;
import java.util.Date;


public class Session implements Serializable {
    private static final long serialVersionUID = Settings.SERIAL_VERSION_UID_SESSION;
    private String sessionID;
    private String error;
    Session(String id, String error) {
        Date date=new Date();
        sessionID = id+date.getTime()+(int)(Math.random()*10000000);
        this.error = error;
    }
    public Session(String id) {
        sessionID = id;
        this.error = "";
    }
    public String getID() {
        return sessionID;
    }

    public synchronized boolean isValid(){

        return error.equals("");
    }
}
