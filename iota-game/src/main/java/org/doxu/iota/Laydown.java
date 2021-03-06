package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Laydown {

    private final List<Move> moves;

    public Laydown() {
        moves = new ArrayList<>();
    }

    private Laydown(List<Move> moves) {
        this.moves = moves;
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public void clear() {
        moves.clear();
    }

    public Laydown copy() {
        return new Laydown(new ArrayList<>(moves));
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

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        for (Move move : moves) {
            locations.add(move.getLocation());
        }
        return locations;
    }

    public boolean contains(Card card) {
        for (Move move : moves) {
            if (move.getCard().equals(card)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Location location) {
        for (Move move : moves) {
            if (move.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }
}
