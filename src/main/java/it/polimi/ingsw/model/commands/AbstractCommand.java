package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_NOT_YOUR_TURN;
import static java.lang.Integer.parseInt;

public abstract class AbstractCommand implements Command {

    private String cmd;
    private GameData gameData;
    private Player player;
    private List<Condition> conditions;

    public AbstractCommand(Player player, GameData gameData, String cmd) {
        assert player != null;
        assert gameData != null;
        assert cmd != null;
        this.cmd = cmd;
        this.gameData = gameData;
        this.player = player;
        conditions = new ArrayList<>();
    }

    private void checkConditions() throws IllegalCommandException {
        if (!player.getUsername().equals(gameData.getTurnManager().getCurrentPlayer().getUsername()))
            throw new IllegalCommandException(ERR_NOT_YOUR_TURN);
        for (Condition c : conditions)
            c.check();
    }

    public int[] getArgs() {
        String[] stringArgs = cmd.split(" ");
        if (stringArgs.length == 0)
            return new int[0];
        int[] args = new int[stringArgs.length - 1];
        for (int i = 1; i < stringArgs.length; i++)
            args[i - 1] = parseInt(stringArgs[i]) - 1;
        return args;
    }

    protected void addCondition(Condition condition) {
        assert condition != null;
        conditions.add(condition);
    }

    public abstract String getRegExp();

    public abstract void executionWhenLegal();

    public GameData getGameData() {
        return gameData;
    }

    @Override
    public final boolean isValid() {
        return Pattern.compile("^" + getRegExp() + "$").asPredicate().test(cmd);
    }

    @Override
    public final void execute() throws IllegalCommandException {
        if (isValid()) {
            checkConditions();
            executionWhenLegal();
            gameData.setUndoAvailable(true);
        }
    }

    @Override
    public String toString() {
        return "[ ] " + player.getUsername() + ": " + cmd;
    }
}
