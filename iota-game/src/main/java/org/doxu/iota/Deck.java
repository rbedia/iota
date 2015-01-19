package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

public class Deck {

    private final Queue<Card> cards;

    public Deck() {
        cards = new LinkedList<>();
        init();
    }

    public final void init() {
        cards.clear();
        List<Card> set = generateDeck();
        Collections.shuffle(set);
        cards.addAll(set);
    }

    private List<Card> generateDeck() {
        List<Card> set = new ArrayList<>();
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
