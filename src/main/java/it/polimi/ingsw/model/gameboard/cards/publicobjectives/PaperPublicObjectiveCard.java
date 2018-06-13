package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.FramePattern;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternFactory;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import org.json.simple.JSONObject;

public class PaperPublicObjectiveCard extends AbstractCard implements PublicObjectiveCard {

    private FramePattern pattern;
    private int pointsPerPattern;

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
