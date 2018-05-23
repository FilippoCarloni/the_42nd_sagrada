package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class LensCutter extends AbstractToolCard {

    private static final int ID = 5;

    LensCutter(ConcreteGameStatus status) {
        super(status, ID);
        name = "Lens Cutter";
        description = "After drafting, swap the drafted die with a die from the Round Track.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new SwapFromRoundTrack(status, cmd, ID));
        return commands;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        sb.append("(FP:").append(getFavorPoints()).append(")");
        for (int i = 0; i < pixelWidth - 6; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[5]").append(Color.YELLOW.paint("[⚂]")).append("[7]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("    ⬍    ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("   [⚃]   "));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class SwapFromRoundTrack extends AbstractToolCommand {

        SwapFromRoundTrack(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
            setRegExp("select \\d");
            setLegalPredicate(s -> getStatus().getStateHolder().getDieHolder() != null &&
                    getIndex() >= 0 && getIndex() < status.getRoundTrack().getDice().size());
        }

        private int getIndex() {
            return parseInt(getCmd().split(" ")[1]) - 1;
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                Die playerDie = status.getStateHolder().getDieHolder();
                Die roundTrackDie = status.getRoundTrack().getDice().get(getIndex());
                status.getRoundTrack().swap(playerDie, roundTrackDie);
                status.getStateHolder().setDieHolder(roundTrackDie);
                tearDown();
            }
        }
    }
}
