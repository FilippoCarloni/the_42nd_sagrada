package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BROKEN_PATH;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/**
 * Generates tool card instances from JSON configuration files.
 */
public final class ToolCardFactory {

    private ToolCardFactory() {}

    /**
     * Returns the tool card instances from file.
     * <br>
     * NOTE: Parameters.USE_COMPLETE_RULES can be used to manage tool card loading:
     * <ul>
     *     <li>true: loads tool cards from 1 to 12</li>
     *     <li>false: loads tool cards from 1 to 6</li>
     * </ul>
     * @see Parameters
     * @return A List of tool card instances
     */
    public static List<ToolCard> getTools() {
        List<ToolCard> cards = new ArrayList<>(loadFromJSON(Parameters.SIMPLIFIED_RULES_TOOLS_PATH));
        if (USE_COMPLETE_RULES)
            cards.addAll(loadFromJSON(Parameters.COMPLETE_RULES_TOOLS_PATH));
        return cards;
    }

    private static List<ToolCard> loadFromJSON(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject parsedContent = (JSONObject) new JSONParser().parse(content);
            JSONArray tools = (JSONArray) parsedContent.get(JSONTag.TOOL_CARDS);
            List<ToolCard> loadedTools = new ArrayList<>();
            for (Object o : tools) {
                JSONObject toolCard = (JSONObject) o;
                loadedTools.add(new PaperToolCard(toolCard.get(JSONTag.NAME).toString(), toolCard.get(JSONTag.DESCRIPTION).toString(),
                        parseInt(toolCard.get(JSONTag.CARD_ID).toString()), parseBoolean(toolCard.get(JSONTag.ACTIVE).toString()),
                        (JSONObject) toolCard.get(JSONTag.ACTIVATOR), (JSONArray) toolCard.get(JSONTag.COMMANDS)));
            }
            return loadedTools;
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }
}
