package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;

public interface DiceBag extends JSONSerializable {

    Die pick();
    List<Die> pick(int numOfDice);
    void insert(Die die);
}
