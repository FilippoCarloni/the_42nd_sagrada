package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;

import java.util.List;

public interface RoundTrack extends Iterable<Die> {

    int getTotalScore();
    void put(Die die);
    void put(List<Die> dice);
    void swap(Die playerDie, Die roundTrackDie);
    int getCurrentRoundNumber();
    int getTotalNumberOfRounds();
    boolean isGameFinished();
}
