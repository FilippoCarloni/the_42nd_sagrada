package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

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

import static it.polimi.ingsw.model.utility.JSONTag.*;
import static java.lang.Integer.parseInt;

public final class PublicObjectiveFactory {

    private PublicObjectiveFactory() {}

    public static List<PublicObjectiveCard> getPublicObjectives() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(Parameters.PUBLIC_OBJECTIVES_PATH)));
            JSONObject parsedContent = (JSONObject) new JSONParser().parse(content);
            JSONArray publicObjectives = (JSONArray) parsedContent.get(JSONTag.PUBLIC_OBJECTIVES);
            List<PublicObjectiveCard> loadedObjectives = new ArrayList<>();
            for (Object o : publicObjectives) {
                JSONObject objective = (JSONObject) o;
                loadedObjectives.add(new PaperPublicObjectiveCard(
                        objective.get(NAME).toString(),
                        objective.get(DESCRIPTION).toString(),
                        parseInt(objective.get(CARD_ID).toString()),
                        (JSONObject) objective.get(PATTERN),
                        parseInt(objective.get(POINTS_PER_PATTERN).toString())));
            }
            return loadedObjectives;
        } catch (IOException e) {
            throw new IllegalArgumentException("Bad file name.");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON file.");
        }
    }
}
