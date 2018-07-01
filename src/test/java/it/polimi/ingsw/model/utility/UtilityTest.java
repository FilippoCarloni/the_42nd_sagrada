package it.polimi.ingsw.model.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UtilityTest {

    /**
     * Check Color ID correctness.
     */
    @Test
    void colorIDTest() {
        assertNotEquals(Color.RED, Color.findByID("r"));
        assertNotEquals(Color.RED, Color.findByID("B"));
        assertNull(Color.findByID("nullID"));
        assertEquals(Color.RED, Color.findByID("R"));
        assertEquals(Color.BLUE, Color.findByID("B"));
        assertEquals(Color.YELLOW, Color.findByID("Y"));
        assertEquals(Color.GREEN, Color.findByID("G"));
        assertEquals(Color.PURPLE, Color.findByID("P"));
    }

    /**
     * Check Color Label correctness.
     */
    @Test
    void colorLabelTest() {
        assertEquals("red", Color.RED.getLabel());
        assertEquals("yellow", Color.YELLOW.getLabel());
        assertEquals("blue", Color.BLUE.getLabel());
        assertEquals("green", Color.GREEN.getLabel());
        assertEquals("purple", Color.PURPLE.getLabel());
        assertNull(Color.findByLabel("invalidLabel"));
    }

    /**
     * Check Shade ID.
     */
    @Test
    void shadeIDTest() {
        assertEquals(Shade.LIGHTEST, Shade.findByValue(1));
        assertEquals(Shade.LIGHTER, Shade.findByValue(2));
        assertEquals(Shade.LIGHT, Shade.findByValue(3));
        assertEquals(Shade.DARK, Shade.findByValue(4));
        assertEquals(Shade.DARKER, Shade.findByValue(5));
        assertEquals(Shade.DARKEST, Shade.findByValue(6));
        assertEquals(Shade.LIGHTEST, Shade.findByID("" + 1));
        assertEquals(Shade.LIGHTER, Shade.findByID("" + 2));
        assertEquals(Shade.LIGHT, Shade.findByID("" + 3));
        assertEquals(Shade.DARK, Shade.findByID("" + 4));
        assertEquals(Shade.DARKER, Shade.findByID("" + 5));
        assertEquals(Shade.DARKEST, Shade.findByID("" + 6));
        assertNull(Shade.findByID(null));
        assertNull(Shade.findByID("null"));
        assertNull(Shade.findByValue(-1));
    }

    /**
     * Check Shade value.
     */
    @Test
    void shadeValueTest() {
        assertNotEquals(Shade.DARKER, Shade.findByValue(1));
        assertEquals(1, Shade.getMinimumValue());
        assertEquals(6, Shade.getMaximumValue());
        assertEquals("1", Shade.LIGHTEST.getLabel());
        assertEquals("2", Shade.LIGHTER.getLabel());
        assertEquals("3", Shade.LIGHT.getLabel());
        assertEquals("4", Shade.DARK.getLabel());
        assertEquals("5", Shade.DARKER.getLabel());
        assertEquals("6", Shade.DARKEST.getLabel());
    }
}
