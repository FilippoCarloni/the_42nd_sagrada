package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_NOT_YOUR_TURN;
import static java.lang.Integer.parseInt;

public class ConcreteCommand implements Command {

    private String regExp;
    private boolean undoable;
    private String cmd;
    private GameData gameData;
    private Player player;
    private List<Condition> conditions;
    private List<Instruction> instructions;

    ConcreteCommand(String regExp, boolean undoable, Player player, GameData gameData, String cmd) {
        this.regExp = regExp;
        this.undoable = undoable;
        this.cmd = cmd;
        this.gameData = gameData;
        this.player = player;
        conditions = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    private void checkConditions() throws IllegalCommandException {
        if (!player.getUsername().equals(gameData.getTurnManager().getCurrentPlayer().getUsername()))
            throw new IllegalCommandException(ERR_NOT_YOUR_TURN);
        for (Condition c : conditions)
            c.check();
    }

    private void performInstructions() {
        for (Instruction i : instructions)
            i.perform(gameData, getArgs());
    }

    public int[] getArgs() {
        try {
            String[] stringArgs = cmd.split(" ");
            if (stringArgs.length == 0)
                return new int[0];
            int[] args = new int[stringArgs.length - 1];
            for (int i = 1; i < stringArgs.length; i++)
                args[i - 1] = parseInt(stringArgs[i]) - 1;
            return args;
        } catch (NumberFormatException e) {
            return new int[0];
        }
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public GameData getGameData() {
        return gameData;
    }

    @Override
    public final boolean isValid() {
        return Pattern.compile("^" + regExp + "$").asPredicate().test(cmd);
    }

    @Override
    public final void execute() throws IllegalCommandException {
        if (isValid()) {
            checkConditions();
            getGameData().setUndoAvailable(undoable);
            performInstructions();
        }
    }

    @Override
    public String toString() {
        return "[ ] " + player.getUsername() + ": " + cmd;
    }
}
