package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    public static final int MAX_HAND = 4;

    private final List<Card> cards = new ArrayList<>(MAX_HAND);

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

    public boolean remove(List<Card> cardsToRemove) {
        return cards.removeAll(cardsToRemove);
    }

    public boolean remove(Card card) {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Hand doesn't have any cards.");
        }
        return cards.remove(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
