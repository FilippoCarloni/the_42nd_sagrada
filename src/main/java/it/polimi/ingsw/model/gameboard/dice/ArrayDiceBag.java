package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDiceBag implements DiceBag {

    private List<Die> dice;

    public ArrayDiceBag() {
        dice = new ArrayList<>();
        Color[] colors = Color.values();
        int totalDiceNumber = Parameters.MAX_COLUMNS * Parameters.MAX_ROWS * Parameters.MAX_PLAYERS + Parameters.TOTAL_NUMBER_OF_ROUNDS;
        for (int i = 0; i < totalDiceNumber; i++) {
            Die die = new PlasticDie(i);
            die.setColor(colors[i % colors.length]);
            die.roll();
            dice.add(die);
        }
        Collections.shuffle(dice);
    }

    @Override
    public Die pick() {
        if (dice.isEmpty())
            throw new NoSuchElementException("There are no more dice in the bag.");
        return dice.remove(dice.size() - 1);
    }

    @Override
    public List<Die> pick(int numOfDice) {
        if (numOfDice <= 0)
            throw new IllegalArgumentException("You should always pick at least one die.");
        List<Die> pickedDice = new ArrayList<>();
        for (int i = 0; i < numOfDice; i++)
            pickedDice.add(pick());
        return pickedDice;
    }

    @Override
    public void insert(Die die) {
        if (die == null)
            throw new NullPointerException("Cannot insert a null die in the bag.");
        Die d = new PlasticDie((PlasticDie) die);
        assert d.hashCode() == die.hashCode();
        d.roll();
        dice.add(d);
        Collections.shuffle(dice);
    }
}
