package it.polimi.ingsw.connection.client;

import java.io.*;

/**
 * The ClientStatus is the class that contains al the useful information about a client to interact with the server structure.
 */
class ClientStatus implements Serializable {
    private static final String SERIAL_FILE_EXTENSION = ".ser";
    private static final long serialVersionUID = 1190476517382928173L;
    private static final String NAME_OF_FILE = "ClientStatus_";
    private static final String PATH_OF_CLIENT_STATUS = "./";

    private String username;
    private String sessionID;

    /**
     * Creates a new ClientStatus from a username and sessionID.
     * @param sessionID - String used how idendtificator by the server.
     * @param username - String that represents the username of the user.
     */
    ClientStatus(String sessionID, String username){
        this.sessionID=sessionID;
        this.username=username;
    }

    /**
     *
     * @return - The username associated at the ClientStatus.
     */
    String getUsername() {
        return username;
    }

    /**
     *
     * @return - The sessionID associated at the ClientStatus.
     */
    String getSession() {
        return sessionID;
    }

    /**
     * Restores an ClientStatus by the username of the user associated to a saved ClientStatus.
     * @param username - Username of the user to restore from file.
     * @return - A ClientStatus if one is find in the previously saved, null otherwise.
     */
    static ClientStatus restoreClientStatus(String username){
        ClientStatus status;
        try (FileInputStream fileIn = new FileInputStream(PATH_OF_CLIENT_STATUS+NAME_OF_FILE + username + SERIAL_FILE_EXTENSION); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            status = (ClientStatus) in.readObject();
        } catch (IOException | ClassNotFoundException c) {
            return null;
        }
        return status;
    }

    /**
     * Saves the ClientStatus in a file.
     * The file name is: ClienStatus_<username_of_the_user>.ser.
     * @param clientStatus - The existing ClientStatus to save.
     * @return - If the saving operation of the clientStatus is done correctly.
     */
    static boolean saveStatus(ClientStatus clientStatus){
        ObjectOutputStream out;
        try(FileOutputStream fileOut  = new FileOutputStream(PATH_OF_CLIENT_STATUS+NAME_OF_FILE + clientStatus.getUsername() + SERIAL_FILE_EXTENSION)) {
            out = new ObjectOutputStream(fileOut);
            out.writeObject(clientStatus);
        } catch (IOException i) {
            return false;
        }
        return true;
    }
}
