package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rafael
 */
public class Laydown {
    private final List<Move> moves;

    public Laydown() {
        moves = new ArrayList<>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        for (Move move : moves) {
            cards.add(move.getCard());
        }
        return cards;
    }
}
