package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaperRoundTrack implements RoundTrack {

    // TODO: add functionality for adding 0 dice on a particular slot.

    private int currentRoundNumber;
    private final int totalNumberOfRounds;
    private ArrayList<Die> dice;
    private int[] diceOnSlot;

    public PaperRoundTrack() {
        this.currentRoundNumber = 1;
        this.totalNumberOfRounds = Parameters.TOTAL_NUMBER_OF_ROUNDS;
        this.dice = new ArrayList<>();
        this.diceOnSlot = new int[this.totalNumberOfRounds];
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
        if (!dice.contains(roundTrackDie)) throw new IllegalArgumentException("The die is not present on the round track.");
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
}
