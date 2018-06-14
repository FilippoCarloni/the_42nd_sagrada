package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternID.DIAGONALS;
import static it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternID.DIFFERENCE;
import static it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternID.SET;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static it.polimi.ingsw.model.utility.JSONTag.OBJECT;
import static it.polimi.ingsw.model.utility.JSONTag.PLACE;

/**
 * Generates frame pattern for value points evaluation.
 */
public final class PatternFactory {

    private PatternFactory() {}

    /**
     * Returns a FramePattern instance from a JSON object.
     * @param pattern A JSON object that encodes the frame pattern logic
     * @return A FramePattern instance
     */
    public static FramePattern getPattern(JSONObject pattern) {
        switch (pattern.get(JSONTag.TYPE).toString()) {
            case SET:
                return new SetPattern(pattern.get(OBJECT).toString(), (JSONArray) pattern.get(JSONTag.VALUES));
            case DIAGONALS:
                return new DiagonalsPattern(pattern.get(OBJECT).toString());
            case DIFFERENCE:
                return new DifferencePattern(pattern.get(PLACE).toString(), pattern.get(OBJECT).toString());
            default:
        }
        throw new IllegalArgumentException(BAD_JSON);
    }
}
