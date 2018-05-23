package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PaperRoundTrack implements RoundTrack {

    private int currentRoundNumber;
    private final int totalNumberOfRounds;
    private ArrayList<Die> dice;
    private int[] diceOnSlot;

    public PaperRoundTrack() {
        currentRoundNumber = 1;
        totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        dice = new ArrayList<>();
        diceOnSlot = new int[this.totalNumberOfRounds];
    }

    public PaperRoundTrack(JSONObject obj) {
        totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        currentRoundNumber = (int) obj.get("current_round_number");
        dice = new ArrayList<>();
        for (Object die : (JSONArray) obj.get("all_dice"))
            dice.add(new PlasticDie((JSONObject) die));
        diceOnSlot = new int[this.totalNumberOfRounds];
        JSONArray numOfDiceOnSlot = (JSONArray) obj.get("number_of_dice_on_slot");
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = (int) numOfDiceOnSlot.get(i);
    }

    @Override
    public List<Die> getVisibleDice() {
        ArrayList<Die> clonedDice = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < currentRoundNumber - 1; i++) {
            index += diceOnSlot[i];
            clonedDice.add(new PlasticDie((PlasticDie) dice.get(index)));
        }
        return clonedDice;
    }

    @Override
    public int getTotalScore() {
        return dice.stream().map(d -> d.getShade().getValue()).mapToInt(Integer::intValue).sum();
    }

    @Override
    public void put(Die die) {
        if (die == null) throw new NullPointerException("Cannot place a null die on the round track.");
        if (currentRoundNumber <= totalNumberOfRounds) {
            dice.add(die);
            diceOnSlot[currentRoundNumber - 1] = 1;
            currentRoundNumber++;
        }
    }

    @Override
    public void put(List<Die> dice) {
        if (dice == null) throw new NullPointerException("Cannot place null dice on the round track.");
        if (currentRoundNumber <= totalNumberOfRounds) {
            this.dice.addAll(dice);
            diceOnSlot[currentRoundNumber - 1] = dice.size();
            currentRoundNumber++;
        }
    }

    @Override
    public void swap(Die playerDie, Die roundTrackDie) {
        if (playerDie == null || roundTrackDie == null) throw new NullPointerException("Cannot swap null dice.");
        if (!dice.contains(roundTrackDie))throw new IllegalArgumentException("The die is not present on the round track.");
        if (dice.contains(playerDie)) throw new IllegalArgumentException("Cannot swap two dice already on the round track.");
        dice.set(dice.indexOf(roundTrackDie), playerDie);
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
    public Iterator<Die> iterator() {
        ArrayList<Die> clonedDice = new ArrayList<>();
        for (Die d : dice) clonedDice.add(new PlasticDie((PlasticDie) d));
        return clonedDice.iterator();
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
        obj.put("current_round_number", currentRoundNumber);
        obj.put("total_number_of_rounds", totalNumberOfRounds);
        JSONArray visibleDice = new JSONArray();
        visibleDice.addAll(getVisibleDice().stream().map(Die::encode).collect(Collectors.toList()));
        obj.put("visible_dice", visibleDice);
        JSONArray allDice = new JSONArray();
        allDice.addAll(dice.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put("all_dice", allDice);
        JSONArray numberOfDiceOnSlot = new JSONArray();
        for (int i : diceOnSlot)
            numberOfDiceOnSlot.add(i);
        obj.put("number_of_dice_on_slot", numberOfDiceOnSlot);
        obj.put("total_number_of_dice", dice.size());
        return obj;
    }
}
