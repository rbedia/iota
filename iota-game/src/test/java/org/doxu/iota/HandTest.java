package org.doxu.iota;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testIsFullHand() {
        Hand hand = new Hand();
        for (int i = 0; i < Hand.MAX_HAND; i++) {
            assertFalse(hand.isFullHand());
            hand.add(Card.create("RS" + (i + 1)));
        }
        assertTrue(hand.isFullHand());
    }

    @Test
    public void testIsEmpty() {
        Hand hand = new Hand();
        Card card = Card.create("RS1");

        assertTrue(hand.isEmpty());

        hand.add(card);
        assertFalse(hand.isEmpty());

        hand.remove(card);
        assertTrue(hand.isEmpty());
    }

    @Test
    public void testCardCount() {
        Hand hand = new Hand();
        Card card = Card.create("RS1");

        assertEquals(0, hand.cardCount());

        hand.add(card);
        assertEquals(1, hand.cardCount());
    }

    @Test
    public void testAdd() {
        Hand hand = new Hand();
        for (int i = 0; i < Hand.MAX_HAND; i++) {
            hand.add(Card.create("RS" + (i + 1)));
        }
        List<Card> cards = hand.getCards();
        assertEquals(4, cards.size());
        assertTrue(cards.contains(Card.create("RS1")));
        assertTrue(cards.contains(Card.create("RS2")));
        assertTrue(cards.contains(Card.create("RS3")));
        assertTrue(cards.contains(Card.create("RS4")));
    }

    @Test(expected = IllegalStateException.class)
    public void testAddTooMany() {
        Hand hand = new Hand();
        for (int i = 0; i < Hand.MAX_HAND; i++) {
            hand.add(Card.create("RS" + (i + 1)));
        }
        hand.add(Card.create("BS1"));
    }

    @Test
    public void testRemove_List() {
        Hand hand = new Hand();
        hand.add(Card.create("RS1"));
        hand.add(Card.create("RS2"));
        hand.add(Card.create("RS3"));
        hand.add(Card.create("RS4"));

        assertTrue(hand.remove(Arrays.asList(Card.create("RS2"))));
        assertEquals(3, hand.cardCount());

        assertTrue(hand.remove(Arrays.asList(Card.create("RS3"), Card.create("RS4"), Card.create("RS1"))));
        assertEquals(0, hand.cardCount());
    }

    @Test
    public void testRemove_Card() {
        Hand hand = new Hand();
        hand.add(Card.create("RS1"));
        hand.add(Card.create("RS2"));
        hand.add(Card.create("RS3"));
        hand.add(Card.create("RS4"));

        assertTrue(hand.remove(Card.create("RS2")));
        assertEquals(3, hand.cardCount());

        assertTrue(hand.remove(Card.create("RS4")));
        assertTrue(hand.remove(Card.create("RS1")));
        assertTrue(hand.remove(Card.create("RS3")));
        assertEquals(0, hand.cardCount());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemove_CardEmpty() {
        Hand hand = new Hand();

        hand.remove(Card.create("RS2"));
    }

    @Test
    public void testRemove_CardFail() {
        Hand hand = new Hand();
        hand.add(Card.create("RS1"));
        hand.add(Card.create("RS2"));
        hand.add(Card.create("RS3"));
        hand.add(Card.create("RS4"));

        assertFalse(hand.remove(Card.create("BS1")));
        assertEquals(4, hand.cardCount());
    }
}
