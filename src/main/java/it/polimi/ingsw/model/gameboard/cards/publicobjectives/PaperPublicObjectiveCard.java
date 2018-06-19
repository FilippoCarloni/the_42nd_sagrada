package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.FramePattern;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternFactory;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import org.json.simple.JSONObject;

/**
 * Implements the PublicObjectiveCard interface with a generic structure.
 */
public class PaperPublicObjectiveCard extends AbstractCard implements PublicObjectiveCard {

    private FramePattern pattern;
    private int pointsPerPattern;

    /**
     * Generates a public objective card, provided its building logic.
     * @param name Card's name
     * @param description Card's description
     * @param id Card's identifier
     * @param pattern JSON that encodes the pattern logic that the card reads
     * @param pointsPerPattern number of points per pattern hit on a window frame
     */
    PaperPublicObjectiveCard(String name, String description, int id, JSONObject pattern, int pointsPerPattern) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.pattern = PatternFactory.getPattern(pattern);
        this.pointsPerPattern = pointsPerPattern;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        return pattern.getNumOfPatterns(window) * pointsPerPattern;
    }
}
