package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.commands.instructions.InstructionFactory;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static java.lang.Boolean.parseBoolean;

public final class CommandFactory {

    private CommandFactory() {}

    public static Command getCommand(JSONObject obj, Player player, GameData gameData, String cmd) {
        String regExp = obj.get(JSONTag.REGEXP).toString();
        boolean undoable = parseBoolean(obj.get(JSONTag.UNDOABLE).toString());
        ConcreteCommand command = new ConcreteCommand(regExp, undoable, player, gameData, cmd);
        JSONArray conditionsSER = (JSONArray) obj.get(JSONTag.CONDITIONS);
        for (Object o : conditionsSER)
            command.addCondition(ConditionFactory.getCondition((JSONObject) o, gameData, command.getArgs()));
        JSONArray instructionSER = (JSONArray) obj.get(JSONTag.INSTRUCTIONS);
        for (Object o : instructionSER)
            command.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
        return command;
    }
}
