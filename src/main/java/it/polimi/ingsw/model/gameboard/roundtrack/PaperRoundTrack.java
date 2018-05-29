package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class PaperRoundTrack implements RoundTrack {

    private int currentRoundNumber;
    private final int totalNumberOfRounds;
    private ArrayList<Die> dice;
    private int[] diceOnSlot;

    /**
     * Generates a dice-empty round track.
     */
    public PaperRoundTrack() {
        currentRoundNumber = 1;
        totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        dice = new ArrayList<>();
        diceOnSlot = new int[this.totalNumberOfRounds];
    }

    /**
     * Generates a clone of the round track represented with JSON syntax.
     * @param obj A JSON Object that holds RoundTrack-like information
     */
    public PaperRoundTrack(JSONObject obj) {
        totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        currentRoundNumber = parseInt(obj.get(JSONTag.CURRENT_ROUND_NUMBER).toString());
        dice = new ArrayList<>();
        for (Object die : (JSONArray) obj.get(JSONTag.ALL_DICE))
            dice.add(new PlasticDie((JSONObject) die));
        diceOnSlot = new int[this.totalNumberOfRounds];
        JSONArray numOfDiceOnSlot = (JSONArray) obj.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());
    }

    @Override
    public List<Die> getVisibleDice() {
        ArrayList<Die> clonedDice = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < currentRoundNumber - 1; i++) {
            index += diceOnSlot[i];
            clonedDice.add(new PlasticDie(dice.get(index).encode()));
        }
        return clonedDice;
    }

    @Override
    public List<Die> getDice() {
        return dice.stream().map(die -> new PlasticDie(die.encode())).collect(Collectors.toList());
    }

    @Override
    public void put(List<Die> dice) {
        if (dice == null) throw new NullPointerException("Cannot place null dice on the round track.");
        if (currentRoundNumber <= totalNumberOfRounds) {
            this.dice.addAll(dice.stream().map(die -> new PlasticDie(die.encode())).collect(Collectors.toList()));
            diceOnSlot[currentRoundNumber - 1] = dice.size();
            currentRoundNumber++;
            return;
        }
        throw new IllegalArgumentException("The round track is empty.");
    }

    @Override
    public void swap(Die playerDie, Die roundTrackDie) {
        if (playerDie == null || roundTrackDie == null) throw new NullPointerException("Cannot swap null dice.");
        if (!dice.contains(roundTrackDie))throw new IllegalArgumentException("The die is not present on the round track.");
        if (dice.contains(playerDie)) throw new IllegalArgumentException("Cannot swap two dice already on the round track.");
        dice.set(dice.indexOf(roundTrackDie), new PlasticDie(playerDie.encode()));
    }

    @Override
    public int getCurrentRoundNumber() {
        return currentRoundNumber > totalNumberOfRounds ? totalNumberOfRounds : currentRoundNumber;
    }

    @Override
    public int getTotalNumberOfRounds() {
        return totalNumberOfRounds;
    }

    @Override
    public boolean isGameFinished() {
        return currentRoundNumber > totalNumberOfRounds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("___");
        sb.append("_ \n| ROUND TRACK");
        for (int i = 0; i < totalNumberOfRounds * 3 - 12; i++) sb.append(" ");
        sb.append(" |\n|");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("---");
        sb.append("-|\n|");
        for (Die d : getVisibleDice()) sb.append(d);
        for (int i = 0; i < totalNumberOfRounds - getVisibleDice().size(); i++)
            sb.append("[").append(getVisibleDice().size() + i + 1).append("]");
        sb.append("|\n|");
        for (int i = 0; i < totalNumberOfRounds; i++) sb.append("___");
        sb.append("_|");
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put(JSONTag.CURRENT_ROUND_NUMBER, currentRoundNumber);
        JSONArray allDice = new JSONArray();
        allDice.addAll(dice.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put(JSONTag.ALL_DICE, allDice);
        JSONArray numberOfDiceOnSlot = new JSONArray();
        for (int i : diceOnSlot)
            numberOfDiceOnSlot.add(i);
        obj.put(JSONTag.NUMBER_OF_DICE_ON_SLOT, numberOfDiceOnSlot);
        return obj;
    }
}
