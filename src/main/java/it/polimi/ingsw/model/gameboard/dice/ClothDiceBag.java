package it.polimi.ingsw.model.gameboard.dice;

import java.util.ArrayList;
import java.util.List;

public class ClothDiceBag implements DiceBag {

    private int progressiveCounter;

    public ClothDiceBag() {
        this.progressiveCounter = 0;
    }

    @Override
    public Die pick() {
        PlasticDie d = new PlasticDie(this.progressiveCounter);
        this.progressiveCounter++;
        return d;
    }

    @Override
    public List<Die> pick(int numOfDice) {
        if (numOfDice <= 0) throw new IllegalArgumentException("You must pick at least one die from the bag.");
        List<Die> dice = new ArrayList<>();
        for  (int i = 0; i < numOfDice; i++)
            dice.add(this.pick());
        return dice;
    }
}
