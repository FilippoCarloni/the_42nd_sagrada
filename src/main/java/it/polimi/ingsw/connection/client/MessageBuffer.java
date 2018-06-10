package it.polimi.ingsw.connection.client;

import java.util.ArrayList;
import java.util.List;

public class MessageBuffer {
    private List<String> messages;
    public MessageBuffer(){
        messages=new ArrayList<>();
    }

    public synchronized String getNext(){
        if(!messages.isEmpty())
            return messages.remove(0);
        return "";
    }

    public synchronized void add(String message){
        messages.add(message);
    }
}