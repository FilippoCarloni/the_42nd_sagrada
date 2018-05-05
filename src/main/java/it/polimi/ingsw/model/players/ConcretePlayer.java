package it.polimi.ingsw.model.players;

import java.util.ArrayList;

public class ConcretePlayer implements Player{
    String username;

    public ConcretePlayer(String username){
        this.username=username;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
