package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

import java.util.List;

/**
 * Generates a deck of public objective cards.
 * Details of the content:
 * <ul>
 *     <li>13: Color Diagonals</li>
 *     <li>14: Color Variety</li>
 *     <li>15: Column Color Variety</li>
 *     <li>16: Column Shade Variety</li>
 *     <li>17: Dark Shades</li>
 *     <li>18: Light Shades</li>
 *     <li>19: Medium Shades</li>
 *     <li>20: Row Color Variety</li>
 *     <li>21: Row Shade Variety</li>
 *     <li>22: Shade Variety</li>
 * </ul>
 */
public class PublicObjectiveDeck extends AbstractDeck {

    /**
     * Generates a new public objective deck instance.
     */
    public PublicObjectiveDeck() {
        List<PublicObjectiveCard> cards = PublicObjectiveFactory.getPublicObjectives();
        for (PublicObjectiveCard card : cards)
            add(card);
    }
}
