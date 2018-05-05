package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

import java.io.Serializable;


public class Session implements Serializable {
    private static final long serialVersionUID = Settings.serialVersionUIDSession;
    private String sessionID;
    private String error;
    public Session(String id, String error) {
        sessionID = id;
        this.error = error;
    }

    public String getID() {
        return sessionID;
    }

    public synchronized boolean isValid(){

        return error.equals("");
    }
}
