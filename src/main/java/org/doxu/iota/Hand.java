package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    public static final int MAX_HAND = 4;
    private final List<Card> cards = new ArrayList<>(4);

    public boolean isFullHand() {
        return cardCount() == MAX_HAND;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int cardCount() {
        return cards.size();
    }

    public void add(Card card) {
        if (cards.size() >= MAX_HAND) {
            throw new IllegalStateException("Hand can't have more than 4 cards.");
        }
        cards.add(card);
    }

    public void remove(List<Card> cardsToRemove) {
        for (Card card : cardsToRemove) {
            cards.remove(card);
        }
    }

    public void remove(Card card) {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Hand doesn't have any cards.");
        }
        cards.remove(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
