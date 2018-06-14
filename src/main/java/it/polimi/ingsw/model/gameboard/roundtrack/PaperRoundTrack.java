package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.utility.ExceptionMessage.*;

/**
 * Implements the RoundTrack interface with an array-like structure.
 */
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

    public PaperRoundTrack(int currentRoundNumber, List<Die> dice, int[] diceOnSlot) {
        this.currentRoundNumber = currentRoundNumber;
        this.totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        this.dice = new ArrayList<>();
        this.dice.addAll(dice.stream().map(d -> JSONFactory.getDie(d.encode())).collect(Collectors.toList()));
        this.diceOnSlot = new int[diceOnSlot.length];
        System.arraycopy(diceOnSlot, 0, this.diceOnSlot, 0, diceOnSlot.length);
    }

    @Override
    public List<Die> getVisibleDice() {
        ArrayList<Die> clonedDice = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < currentRoundNumber - 1; i++) {
            index += diceOnSlot[i];
            clonedDice.add(JSONFactory.getDie(dice.get(index).encode()));
        }
        return clonedDice;
    }

    @Override
    public List<Die> getDice() {
        return dice.stream().map(die -> JSONFactory.getDie(die.encode())).collect(Collectors.toList());
    }

    @Override
    public void put(List<Die> dice) {
        if (dice == null) throw new NullPointerException(NULL_PARAMETER);
        if (currentRoundNumber <= totalNumberOfRounds) {
            this.dice.addAll(dice.stream().map(die -> JSONFactory.getDie(die.encode())).collect(Collectors.toList()));
            diceOnSlot[currentRoundNumber - 1] = dice.size();
            currentRoundNumber++;
            return;
        }
        throw new IllegalArgumentException(FULL_ROUND_TRACK);
    }

    @Override
    public void swap(Die playerDie, Die roundTrackDie) {
        if (playerDie == null || roundTrackDie == null) throw new NullPointerException(NULL_PARAMETER);
        if (!dice.contains(roundTrackDie))throw new IllegalArgumentException(OBJECT_NOT_EXISTS);
        if (dice.contains(playerDie)) throw new IllegalArgumentException(OBJECT_ALREADY_CONTAINED);
        dice.set(dice.indexOf(roundTrackDie), JSONFactory.getDie(playerDie.encode()));
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
        sb.append("{");
        sb.append("\n  visible dice : ");
        for (Die d : getVisibleDice()) sb.append(d);
        sb.append("\n  all dice     : ");
        for (Die d : getDice()) sb.append(d);
        sb.append("\n current round : ").append(currentRoundNumber);
        sb.append("\n}");
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
