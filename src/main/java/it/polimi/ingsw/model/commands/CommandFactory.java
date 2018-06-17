package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.commands.instructions.InstructionFactory;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BROKEN_PATH;
import static java.lang.Boolean.parseBoolean;

/**
 * Generates Command instances from JSON encoding.
 * Commands are divided in generic commands and activator commands.
 * Every command is generic except from the once that are called on a Tool Card activation.
 * They all correspond to a specific regular expression and perform similar tasks,
 * so they are loaded differently from the others.
 */
public final class CommandFactory {

    private CommandFactory() {}

    /**
     * Returns a new generic command instance.
     * @param obj The JSON object from which the command will be loaded
     * @param player The player that executes the command
     * @param gameData The game data on which the command is executed
     * @param cmd The command string provided by the player
     * @return A new Command instance from the provided information
     */
    public static Command getCommand(JSONObject obj, Player player, GameData gameData, String cmd) {
        String regExp = obj.get(JSONTag.REGEXP).toString();
        boolean undoable = parseBoolean(obj.get(JSONTag.UNDOABLE).toString());
        ConcreteCommand command = new ConcreteCommand(regExp, undoable, player, gameData, cmd);
        JSONArray conditionsSER = (JSONArray) obj.get(JSONTag.CONDITIONS);
        for (Object o : conditionsSER)
            command.addCondition(ConditionFactory.getCondition((JSONObject) o));
        JSONArray instructionSER = (JSONArray) obj.get(JSONTag.INSTRUCTIONS);
        for (Object o : instructionSER)
            command.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
        return command;
    }

    /**
     * Returns a new generic command instance that is called on Tool Card activation.
     * @param id The ID of the Tool Card
     * @param obj The JSON object from which the command will be loaded
     * @param player The player that executes the command
     * @param gameData The game data on which the command is executed
     * @param cmd The command string provided by the player
     * @return A new Command instance from the provided information
     */
    public static Command getToolActivator(int id, JSONObject obj, Player player, GameData gameData, String cmd) {
        try {
            String path = Parameters.TOOL_ACTIVATOR_PATH;
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject basicObj = (JSONObject) new JSONParser().parse(content);
            String regExp = basicObj.get(JSONTag.REGEXP).toString() + id;
            boolean undoable = parseBoolean(obj.get(JSONTag.UNDOABLE).toString());
            ConcreteCommand activator = new ConcreteCommand(regExp, undoable, player, gameData, cmd);
            JSONArray conditionsSER = (JSONArray) basicObj.get(JSONTag.CONDITIONS);
            for (Object o : conditionsSER)
                activator.addCondition(ConditionFactory.getCondition((JSONObject) o));
            conditionsSER = (JSONArray) obj.get(JSONTag.CONDITIONS);
            for (Object o : conditionsSER)
                activator.addCondition(ConditionFactory.getCondition((JSONObject) o));
            JSONArray instructionSER = (JSONArray) basicObj.get(JSONTag.INSTRUCTIONS);
            for (Object o : instructionSER)
                activator.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
            instructionSER = (JSONArray) obj.get(JSONTag.INSTRUCTIONS);
            for (Object o : instructionSER)
                activator.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
            return activator;
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }
}
