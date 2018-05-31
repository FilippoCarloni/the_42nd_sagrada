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

    public static Command getToolActivator(JSONObject obj, Player player, GameData gameData, String cmd) {
        try {
            String path = Parameters.TOOL_ACTIVATOR_PATH;
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject basicObj = (JSONObject) new JSONParser().parse(content);
            String regExp = basicObj.get(JSONTag.REGEXP).toString();
            boolean undoable = parseBoolean(obj.get(JSONTag.UNDOABLE).toString());
            ConcreteCommand activator = new ConcreteCommand(regExp, undoable, player, gameData, cmd);
            JSONArray conditionsSER = (JSONArray) basicObj.get(JSONTag.CONDITIONS);
            for (Object o : conditionsSER)
                activator.addCondition(ConditionFactory.getCondition((JSONObject) o, gameData, activator.getArgs()));
            conditionsSER = (JSONArray) obj.get(JSONTag.CONDITIONS);
            for (Object o : conditionsSER)
                activator.addCondition(ConditionFactory.getCondition((JSONObject) o, gameData, activator.getArgs()));
            JSONArray instructionSER = (JSONArray) basicObj.get(JSONTag.INSTRUCTIONS);
            for (Object o : instructionSER)
                activator.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
            instructionSER = (JSONArray) obj.get(JSONTag.INSTRUCTIONS);
            for (Object o : instructionSER)
                activator.addInstruction(InstructionFactory.getInstruction((JSONObject) o));
            return activator;
        } catch (IOException e) {
            throw new IllegalArgumentException("Bad path for tool card activator.");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON tool card activator.");
        }
    }
}
