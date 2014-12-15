package org.doxu.iota;

public class Move {

    private final Location location;
    private final Card card;

    public Move(Location location, Card card) {
        this.location = location;
        this.card = card;
    }

    public Location getLocation() {
        return location;
    }

    public Card getCard() {
        return card;
    }
}
