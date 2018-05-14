package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublicObjectiveCardTest {

    // TODO: add single-card tests

    @Test
    void isDeckCorrect() {
        Deck d = new PublicObjectiveDeck();
        assertEquals(d.size(), 10);
        d.draw(d.size());
        assertThrows(NoSuchElementException.class, d::draw);
    }

    @Test
    void printCards() {
        DiceBag db = new ClothDiceBag();
        Deck frames = new WindowFrameDeck();
        Deck d = new PublicObjectiveDeck();
        WindowFrame w = (WindowFrame) frames.draw();
        for (int i = 0; i < Parameters.MAX_ROWS; i++)
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++)
                w.put(db.pick(), i, j);
        System.out.println(w);
        while (d.size() > 0) {
            PublicObjectiveCard c = (PublicObjectiveCard) d.draw();
            System.out.println("Value points for " + c.getName() + ": " + c.getValuePoints(w));
        }
    }
}
