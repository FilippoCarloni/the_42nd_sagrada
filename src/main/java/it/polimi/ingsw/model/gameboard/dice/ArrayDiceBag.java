package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Generates a clone of the dice bag represented with JSON syntax.
     * It has memory of all the dice still in the bag.
     * @param obj A JSON Object that holds DiceBag-like information
     */
    public ArrayDiceBag(JSONObject obj) {
        JSONArray list = (JSONArray) obj.get("dice");
        dice = new ArrayList<>();
        for (Object die : list)
            dice.add(new PlasticDie((JSONObject) die));
    }

    @Override
    public Die pick() {
        if (dice.isEmpty())
            throw new NoSuchElementException("There are no more dice in the bag.");
        Collections.shuffle(dice);
        return dice.remove(dice.size() - 1);
    }

    @Override
    public List<Die> pick(int numOfDice) {
        if (numOfDice <= 0)
            throw new IllegalArgumentException("You should always pick at least one die.");
        if (numOfDice > dice.size())
            throw new NoSuchElementException("There are not enough dice in the dice bag.");
        List<Die> pickedDice = new ArrayList<>();
        for (int i = 0; i < numOfDice; i++)
            pickedDice.add(pick());
        return pickedDice;
    }

    @Override
    public void insert(Die die) {
        if (die == null)
            throw new NullPointerException("Cannot insert a null die in the bag.");
        if (dice.stream().map(Die::hashCode).anyMatch(x -> x.hashCode() == die.hashCode()))
            throw new IllegalArgumentException("The bag already contains the die.");
        Die d = new PlasticDie(die.encode());
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
        obj.put("dice", list);
        obj.put("capacity", dice.size());
        return obj;
    }
}
