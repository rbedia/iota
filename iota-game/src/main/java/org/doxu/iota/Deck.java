package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

public class Deck {

    /**
     * Number of cards in a full deck.
     */
    public static final int FULL_DECK = 4 * 4 * 4;

    private final Queue<Card> cards;

    public Deck() {
        cards = new LinkedList<>();
        init();
    }

    public final void init() {
        cards.clear();
        Set<Card> set = generateDeck();
        List<Card> shuffleList = new ArrayList<>(set.size());
        shuffleList.addAll(set);
        Collections.shuffle(shuffleList);
        cards.addAll(shuffleList);
    }

    public static Set<Card> generateDeck() {
        Set<Card> set = new HashSet<>();
        for (Color color : Color.real()) {
            for (Shape shape : Shape.real()) {
                for (Count count : Count.real()) {
                    set.add(new Card(color, shape, count));
                }
            }
        }
        // TODO add wilcards
        return set;
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    public int count() {
        return cards.size();
    }

    public Card draw() {
        return cards.remove();
    }

    public void addToBottom(List<Card> newCards) {
        cards.addAll(newCards);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
