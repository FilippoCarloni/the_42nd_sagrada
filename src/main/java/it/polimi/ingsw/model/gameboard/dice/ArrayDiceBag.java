package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.utility.ExceptionMessage.*;

/**
 * Implements the DiceBag interface holding data with an array-like representation.
 */
public class ArrayDiceBag implements DiceBag {

    private List<Die> dice;

    /**
     * Generates a new 90-dice dice bag equally divided between the 5 Sagrada colors (18 dice per color).
     */
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

    public ArrayDiceBag(List<Die> dice) {
        if (dice == null)
            throw new NullPointerException(NULL_PARAMETER);
        this.dice = new ArrayList<>();
        this.dice.addAll(dice.stream().map(d -> JSONFactory.getDie(d.encode())).collect(Collectors.toList()));
    }

    @Override
    public Die pick() {
        if (dice.isEmpty())
            throw new NoSuchElementException(EMPTY_COLLECTION);
        Collections.shuffle(dice);
        return dice.remove(dice.size() - 1);
    }

    @Override
    public List<Die> pick(int numOfDice) {
        if (numOfDice <= 0)
            throw new IllegalArgumentException(NEGATIVE_INTEGER);
        if (numOfDice > dice.size())
            throw new NoSuchElementException(INDEX_OUT_OF_BOUND);
        List<Die> pickedDice = new ArrayList<>();
        for (int i = 0; i < numOfDice; i++)
            pickedDice.add(pick());
        return pickedDice;
    }

    @Override
    public void insert(Die die) {
        if (die == null)
            throw new NullPointerException(NULL_PARAMETER);
        if (dice.stream().map(Die::hashCode).anyMatch(x -> x.hashCode() == die.hashCode()))
            throw new IllegalArgumentException(OBJECT_ALREADY_CONTAINED);
        Die d = JSONFactory.getDie(die.encode());
        d.roll();
        dice.add(d);
        Collections.shuffle(dice);
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        list.addAll(dice.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put(JSONTag.DICE, list);
        return obj;
    }
}
