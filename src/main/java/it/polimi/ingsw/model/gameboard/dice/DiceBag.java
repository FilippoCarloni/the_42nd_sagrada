package it.polimi.ingsw.model.gameboard.dice;

import java.util.List;

public interface DiceBag {

    Die pick();
    List<Die> pick(int numOfDice);
}
