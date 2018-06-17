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

/**
 * Implements the Command interface with a generic structure.
 */
public class ConcreteCommand implements Command {

    private String regExp;
    private boolean undoable;
    private String cmd;
    private GameData gameData;
    private Player player;
    private List<Condition> conditions;
    private List<Instruction> instructions;
    private static final String ARG_SEPARATOR = " ";

    /**
     * Allocates a new Command instance. In order to generate a viable command, several
     * parameters are needed.
     * @param regExp The regular expression that uniquely identifies the command
     * @param undoable A boolean true only if the command can be undone once executed
     * @param player The player that is casting the command
     * @param gameData The GameData instance on which the command will be executed
     * @param cmd The command string sent by the player
     */
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
            c.check(gameData, getArgs());
    }

    private void performInstructions() {
        for (Instruction i : instructions)
            i.perform(gameData, getArgs());
    }

    public int[] getArgs() {
        try {
            String[] stringArgs = cmd.split(ARG_SEPARATOR);
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

    /**
     * Adds a precondition for the command execution.
     * @param condition A Condition instance
     */
    void addCondition(Condition condition) {
        conditions.add(condition);
    }

    /**
     * Adds an instruction performed during the command execution.
     * @param instruction A Instruction instance
     */
    void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    @Override
    public final boolean isValid() {
        return Pattern.compile("^" + regExp + "$").asPredicate().test(cmd);
    }

    @Override
    public final void execute() throws IllegalCommandException {
        if (isValid()) {
            checkConditions();
            gameData.setUndoAvailable(undoable);
            performInstructions();
        }
    }

    @Override
    public String toString() {
        return player.getUsername() + ": " + cmd;
    }
}
