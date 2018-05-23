package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;

public interface RoundTrack extends Iterable<Die>, JSONSerializable {

    List<Die> getVisibleDice();
    int getTotalScore();
    void put(Die die);
    void put(List<Die> dice);
    void swap(Die playerDie, Die roundTrackDie);
    int getCurrentRoundNumber();
    int getTotalNumberOfRounds();
    boolean isGameFinished();
}
