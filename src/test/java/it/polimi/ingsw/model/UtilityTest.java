package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UtilityTest {

    @Test
    void colorTest() {
        assertEquals(Color.findByID("R"), Color.RED);
        assertNotEquals(Color.findByID("R"), Color.BLUE);
        assertNotEquals(Color.findByID("r"), Color.RED);
    }

    @Test
    void shadeTest() {
        assertEquals(Shade.LIGHT, Shade.findByValue(3));
        assertEquals(null, Shade.findByValue(-1));
        assertNotEquals(Shade.DARKER, Shade.findByValue(1));
    }
}
