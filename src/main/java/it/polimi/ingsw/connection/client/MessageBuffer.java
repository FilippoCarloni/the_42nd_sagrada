package it.polimi.ingsw.connection.client;

import java.util.ArrayList;
import java.util.List;

/**
 * The MessageBuffer class implements an synchronized List of Strings.
 */
public class MessageBuffer {
    private List<String> messages;

    /**
     * Creates a new empty MessageBuffer.
     */
    MessageBuffer(){
        messages=new ArrayList<>();
    }

    /**
     * The messages from the MessageBuffer are returned in a synchronized way.
     * @return - The next MessageBuffer from the buffer. If the MessageBuffer is empty an empty String is returned.
     */
    synchronized String getNext(){
        if(!messages.isEmpty())
            return messages.remove(0);
        return "";
    }

    /**
     * Adds a String to the MessageBuffer in a synchronized way.
     * @param message - the String message to add.
     */
    public synchronized void add(String message){
        messages.add(message);
    }
}
