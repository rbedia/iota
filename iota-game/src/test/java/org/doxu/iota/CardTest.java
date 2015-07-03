package org.doxu.iota;

import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        Card.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid0() {
        Card.create("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid1() {
        Card.create("R");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid2() {
        Card.create("RS");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid3() {
        Card.create("RS3B");
    }

    @Test
    public void testCreateGood1() {
        Card card = Card.create("RS3");
        assertEquals(new Card(Color.RED, Shape.SQUARE, Count.THREE), card);
    }

    @Test
    public void testCreateGood2() {
        Card card = Card.create("BT4");
        assertEquals(new Card(Color.BLUE, Shape.TRIANGLE, Count.FOUR), card);
    }

    @Test
    public void testCreateBlank() {
        assertEquals(Card.BLANK, Card.create("..."));
    }

    @Test
    public void testGetPoints1() {
        assertEquals(1, Card.create("RS1").getPoints());
    }

    @Test
    public void testGetPoints2() {
        assertEquals(2, Card.create("RS2").getPoints());
    }

    @Test
    public void testGetPoints3() {
        assertEquals(3, Card.create("RS3").getPoints());
    }

    @Test
    public void testGetPoints4() {
        assertEquals(4, Card.create("RS4").getPoints());
    }

    @Test
    public void testIsBlank1() {
        assertTrue(Card.create("...").isBlank());
    }

    @Test
    public void testIsBlank2() {
        assertFalse(Card.create("RS1").isBlank());
    }
}
