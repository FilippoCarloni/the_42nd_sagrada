package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
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
import static java.lang.Integer.parseInt;

/**
 * Generates the Private Objective Cards of Sagrada Game.
 * Every private objective corresponds to a different Sagrada Color.
 */
final class PrivateObjectiveFactory {

    private PrivateObjectiveFactory() {}

    /**
     * Returns the list of public objectives, one for every game color.
     * @return A List of public objectives
     */
    static List<PrivateObjectiveCard> getPrivateObjectives() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(Parameters.PRIVATE_OBJECTIVES_PATH)));
            JSONObject parsedContent = (JSONObject) new JSONParser().parse(content);
            String name = parsedContent.get(JSONTag.NAME).toString();
            String description = parsedContent.get(JSONTag.DESCRIPTION).toString();
            int index = parseInt(parsedContent.get(JSONTag.INDEX).toString());
            int baseID = parseInt(parsedContent.get(JSONTag.BASE_ID).toString());
            List<PrivateObjectiveCard> privateObjectives = new ArrayList<>();
            for (Color color : Color.values())
                privateObjectives.add(new PaperPrivateObjectiveCard(name, description, index, color, baseID++));
            return privateObjectives;
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }
}
