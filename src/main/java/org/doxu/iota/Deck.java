package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

/**
 *
 * @author rafael
 */
public class Deck {

    private final Queue<Card> cards = new LinkedList<>();

    public Deck() {
        List<Card> set = new ArrayList<>();
        for (Color color : Color.VALID) {
            for (Shape shape : Shape.VALID) {
                for (Count count : Count.VALID) {
                    set.add(new Card(color, shape, count));
                }
            }
        }
        // TODO add wilcards
        Collections.shuffle(set);
        cards.addAll(set);
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    public Card draw() {
        return cards.remove();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
