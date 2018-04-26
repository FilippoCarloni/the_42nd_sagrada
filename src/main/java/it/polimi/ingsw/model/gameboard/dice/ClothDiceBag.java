package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClothDiceBag implements DiceBag {

    private int progressiveCounter;
    private Color[] colors;
    private int[] coloredDiceQuantity;
    private int maxNumberOfSameColorDice;

    public ClothDiceBag() {
        colors = Color.values();
        coloredDiceQuantity = new int[colors.length];
        maxNumberOfSameColorDice = (Parameters.MAX_PLAYERS * Parameters.MAX_COLUMNS *
                Parameters.MAX_ROWS + Parameters.TOTAL_NUMBER_OF_ROUNDS) / colors.length;
        this.progressiveCounter = 0;
    }

    @Override
    public Die pick() {
        PlasticDie d = new PlasticDie(this.progressiveCounter);
        this.progressiveCounter++;
        for (int i = 0; i < colors.length; i++) {
            if (d.getColor().equals(colors[i]) && coloredDiceQuantity[i] < maxNumberOfSameColorDice) {
                coloredDiceQuantity[i]++;
                return d;
            }
        }
        // this is not actually random, but behaves similarly in most cases
        for (int i = 0; i < colors.length; i++) {
            if (coloredDiceQuantity[i] < maxNumberOfSameColorDice) {
                coloredDiceQuantity[i]++;
                d.setColor(colors[i]);
                return d;
            }
        }
        throw new NoSuchElementException("The dice bag is empty.");
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
