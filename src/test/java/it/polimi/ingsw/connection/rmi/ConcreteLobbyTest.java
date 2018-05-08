package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.Session;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteLobbyTest {

    @Test
    void test() throws RemoteException {
        ConcreteLobby lobby = new ConcreteLobby();
        assertTrue(lobby.connect("pippo").isValid());
        assertFalse(lobby.connect("pippo").isValid());
        assertFalse(lobby.connect("pippo ").isValid());
        assertFalse(lobby.connect(" pippo ").isValid());
        assertFalse(lobby.connect(" pippo").isValid());
        assertTrue(lobby.connect("pluto").isValid());
    }
}