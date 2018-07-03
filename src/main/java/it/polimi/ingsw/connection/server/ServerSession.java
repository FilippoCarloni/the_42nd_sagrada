package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * The ServerSession class generate new ServerSessions. In particular this class contains the sessionID that
 * identifies uniquely an user in the server execution.
 */
public class ServerSession implements Serializable {
    private static final int RANDOM_RANGE = 10000000;
    private static final long serialVersionUID = Settings.SERIAL_VERSION_UID_SESSION;
    public static final String INVALID_SESSION_ID = "";
    private String sessionID;

    /**
     * genrate a sessionID formed as: username + actual time in ns + a random number.
     * @param username - The username used to generate the sessionID.
     */
    ServerSession(String username) {
        Date date=new Date();
        sessionID = username+date.getTime()+(new Random().nextInt(RANDOM_RANGE));
    }

    /**
     * Returns the unique ServeSession sessionID.
     * @return - The ServerSession sessionID.
     */
    public String getID() {
        return sessionID;
    }

}
