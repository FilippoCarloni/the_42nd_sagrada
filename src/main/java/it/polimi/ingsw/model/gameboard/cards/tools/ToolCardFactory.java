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

import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public final class ToolCardFactory {

    private ToolCardFactory() {}

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
            throw new IllegalArgumentException("Bad file name.");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON file.");
        }
    }
}
