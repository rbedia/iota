package org.doxu.iota;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {

    private static final int FULL_DECK = 64;

    @Test
    public void testInit() {
        Deck deck = new Deck();
        deck.draw();
        assertEquals(FULL_DECK - 1, deck.getCards().size());
        deck.init();
        assertEquals(FULL_DECK, deck.getCards().size());
    }

    @Test
    public void testHasCards() {
        Deck deck = new Deck();
        for (int i = 0; i < FULL_DECK; i++) {
            assertTrue(deck.hasCards());
            deck.draw();
        }
        assertFalse(deck.hasCards());
    }

    @Test
    public void testCount() {
        Deck deck = new Deck();
        assertEquals(FULL_DECK, deck.count());
    }

    @Test
    public void testDraw() {
        Deck deck = new Deck();
        deck.draw();
        assertEquals(FULL_DECK - 1, deck.getCards().size());
        deck.draw();
        assertEquals(FULL_DECK - 2, deck.getCards().size());
    }

    @Test
    public void testAddToBottom() {
        Deck deck = new Deck();
        // Clear the deck to make the test easier to understand
        while (deck.hasCards()) {
            deck.draw();
        }

        deck.addToBottom(Arrays.asList(Card.create("RS1"), Card.create("RS2")));
        deck.addToBottom(Arrays.asList(Card.create("RS3")));
        assertEquals(Card.create("RS1"), deck.getCards().get(0));
        assertEquals(Card.create("RS2"), deck.getCards().get(1));
        assertEquals(Card.create("RS3"), deck.getCards().get(2));

        deck.draw();
        assertEquals(Card.create("RS2"), deck.getCards().get(0));
        assertEquals(Card.create("RS3"), deck.getCards().get(1));
    }
}
