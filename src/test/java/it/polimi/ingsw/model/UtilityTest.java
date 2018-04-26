package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UtilityTest {

    @Test
    void colorTest() {
        assertNotEquals(Color.findByID("R"), Color.BLUE);
        assertNotEquals(Color.findByID("r"), Color.RED);
        assertEquals(Color.findByID("null"), null);

        assertEquals(Color.RED.getLabel(), "red");
        assertEquals(Color.YELLOW.getLabel(), "yellow");
        assertEquals(Color.BLUE.getLabel(), "blue");
        assertEquals(Color.GREEN.getLabel(), "green");
        assertEquals(Color.PURPLE.getLabel(), "purple");

        assertEquals(Color.findByID("R"), Color.RED);
        assertEquals(Color.findByID("B"), Color.BLUE);
        assertEquals(Color.findByID("Y"), Color.YELLOW);
        assertEquals(Color.findByID("G"), Color.GREEN);
        assertEquals(Color.findByID("P"), Color.PURPLE);

        System.out.println(Color.PURPLE);
    }

    @Test
    void shadeTest() {
        assertEquals(Shade.LIGHTEST, Shade.findByValue(1));
        assertEquals(Shade.LIGHTER, Shade.findByValue(2));
        assertEquals(Shade.LIGHT, Shade.findByValue(3));
        assertEquals(Shade.DARK, Shade.findByValue(4));
        assertEquals(Shade.DARKER, Shade.findByValue(5));
        assertEquals(Shade.DARKEST, Shade.findByValue(6));

        assertEquals(null, Shade.findByValue(-1));

        assertNotEquals(Shade.DARKER, Shade.findByValue(1));

        assertEquals(Shade.LIGHTEST.getLabel(), "⚀");
        assertEquals(Shade.LIGHTER.getLabel(), "⚁");
        assertEquals(Shade.LIGHT.getLabel(), "⚂");
        assertEquals(Shade.DARK.getLabel(), "⚃");
        assertEquals(Shade.DARKER.getLabel(), "⚄");
        assertEquals(Shade.DARKEST.getLabel(), "⚅");
    }
}
